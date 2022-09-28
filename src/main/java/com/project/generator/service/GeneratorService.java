package com.project.generator.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import com.google.common.base.CaseFormat;
import com.project.generator.constant.ResponseResult;
import com.project.generator.model.ColumnClass;
import com.project.generator.model.DataSourceVo;
import com.project.generator.model.TableClass;
import com.project.generator.model.TableVO;
import com.project.generator.utils.DBUtils;
import com.project.generator.utils.FileUtil;
import com.project.generator.utils.TypeUtil;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author YM
 * @Date 2022/03/20
 * @Description
 */
@Slf4j
@Service
public class GeneratorService {


    @Value("${generate.realPath}")
    private String genRealPath;

    Configuration cfg = null;

    {
        cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setTemplateLoader(new ClassTemplateLoader(GeneratorService.class, "/templates"));
        cfg.setDefaultEncoding("UTF-8");
    }

    /**
     * @param tableClassList
     * @param tableVO        生成基本要求信息
     * @param realPath       服务器路径
     * @return
     */
    public ResponseResult generateCode(List<TableClass> tableClassList, TableVO tableVO, String realPath,
                                       HttpServletResponse response) {
        if (StrUtil.isNotEmpty(tableVO.getPath())) {
            tableVO.setPath("." + tableVO.getPath());
        }

        String templatePath = tableVO.getTempPath() + "/";
        //是否获取到配置文件中的路径
        if (genRealPath != null && !genRealPath.isEmpty()) {
            realPath = genRealPath;
        }
        String dateStr = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT);
        String generatePath = realPath + "/" + dateStr;
        try {
            Template modelTemplate = cfg.getTemplate(templatePath + "Model.java.ftl");
            Template voTemplate = cfg.getTemplate(templatePath + "VO.java.ftl");
            Template mapperJavaTemplate = cfg.getTemplate(templatePath + "Mapper.java.ftl");
            Template mapperXmlTemplate = cfg.getTemplate(templatePath + "Mapper.xml.ftl");
            Template serviceTemplate = cfg.getTemplate(templatePath + "Service.java.ftl");
            Template serviceImplTemplate = cfg.getTemplate(templatePath + "ServiceImpl.java.ftl");
            Template controllerTemplate = cfg.getTemplate(templatePath + "Controller.java.ftl");
            Connection connection = DBUtils.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            for (TableClass tableClass : tableClassList) {
                System.out.println("catalog:" + connection.getCatalog() + "tablename:" + tableClass.getTableName());
                List<ColumnClass> columnClassList = getColumnClasses(connection, metaData, tableClass, tableVO.getDbType());
                tableClass.setSlf4jFlag(tableVO.getSlf4jFlag());
                tableClass.setLombokFlag(tableVO.getLombokFlag());
                tableClass.setBatchFlag(tableVO.getBatchFlag());
                tableClass.setSwaggerFlag(tableVO.getSwaggerFlag());
                tableClass.setPath(tableVO.getPath());
                tableClass.setColumns(columnClassList);
                if (tableClass.getPrimaryColumn() == null) {
                    continue;
                }
                System.out.println("getPrimaryColumn:" + tableClass.getPrimaryColumn());
                String path = realPath + "/" + dateStr + "/" + tableClass.getPackageName().replace(".", "/");
                String childPath = "";
                if (StrUtil.isNotEmpty(tableClass.getPath())) {
                    childPath = tableClass.getPath().replace(".", "/");
                }
                generate(modelTemplate, tableClass, path + "/entity" + childPath + "/");
                generate(voTemplate, tableClass, path + "/vo" + childPath + "/");
                generate(mapperJavaTemplate, tableClass, path + "/mapper" + childPath + "/");
                generate(mapperXmlTemplate, tableClass, path + "/mapperXml" + childPath + "/");
                generate(serviceTemplate, tableClass, path + "/service" + childPath + "/");
                generate(serviceImplTemplate, tableClass, path + "/service" + childPath + "/impl/");
                generate(controllerTemplate, tableClass, path + "/controller/");
            }

            String zipName = dateStr + ".zip";
            zipName = new String(zipName.getBytes(), "utf-8");

            //=================压缩======================
            File zipFile = new File(generatePath + ".zip");
            FileOutputStream fos1 = new FileOutputStream(zipFile);
            FileUtil.toZip(generatePath, fos1, true);
            fos1.close();
            //  下载
            response.reset();
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=" + zipName + "");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            File file = new File(generatePath + ".zip");
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = bis.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            bis.close();
            outputStream.flush();
            outputStream.close();

            log.info("代码已生成,目录为：" + generatePath);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("代码生成错误：" + e.getMessage());
        } finally {
            //下载完成之后，删掉这个zip包
            File fileTempZip = new File(generatePath + ".zip");
            if (fileTempZip.exists()) {
                fileTempZip.delete();
            }
        }
        return ResponseResult.error("代码生成失败");
    }

    public List<ColumnClass> getColumnClasses(Connection connection, DatabaseMetaData metaData,
                                              TableClass tableClass, String dbType) throws SQLException {
        ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableClass.getTableName(), null);
        // metaData.getPrimaryKeys 得到的  ResultSet 默认支持向前滚动 type = 1003 主键获取
        ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null, tableClass.getTableName());
        List<String> pkNames = new ArrayList<>();
        primaryKeys.beforeFirst();
        //(!primaryKeys.isBeforeFirst() && rs.getRow() == 0
        while (primaryKeys.next()) {
            String pkName = primaryKeys.getString("COLUMN_NAME");
            pkNames.add(pkName);
        }
        List<ColumnClass> columnClassList = new ArrayList<>();
        while (columns.next()) {
            String column_name = columns.getString("COLUMN_NAME");
            String type_name = columns.getString("TYPE_NAME");
            String remarks = columns.getString("REMARKS");
            if (StrUtil.isEmpty(remarks)) {
                remarks = column_name;
            }
            ColumnClass columnClass = new ColumnClass();
            columnClass.setRemark(remarks);
            columnClass.setColumnName(column_name);
            columnClass.setType(type_name);
            if (column_name.indexOf("_") > 0) {
                columnClass.setPropertyName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, column_name));
            } else {
                columnClass.setPropertyName(TypeUtil.toLowerCaseFirstOne(column_name));
            }
            // type 转换
            if (StringUtils.isNotEmpty(dbType) && "mysql".equals(dbType)) {
                columnClass.setPropertyType(TypeUtil.castMysqlColumnTypeToPropertyType(type_name));
                columnClass.setJdbcType(TypeUtil.castMysqlColumnTypeToJdbcType(type_name));
            } else {
                columnClass.setPropertyType(TypeUtil.castPostgresColumnTypeToPropertyType(type_name));
                columnClass.setJdbcType(TypeUtil.castPostgresColumnTypeToJdbcType(type_name));
            }
            // 主键匹配到对应的Column
            for (String pkName : pkNames) {
                if (column_name.equals(pkName)) {
                    columnClass.setPrimary(true);
                    tableClass.setPrimaryColumn(columnClass);
                }
            }
            columnClassList.add(columnClass);
        }
        return columnClassList;
    }

    private void generate(Template template, TableClass tableClass, String path) throws IOException, TemplateException {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //template-name:mybatisplus/Model.java.ftl
        String name = template.getName().split("/")[1];
        String fileName = path + "/" + tableClass.getModelName() + name.replace(".ftl", "").replace("Model", "");
        FileOutputStream fos = new FileOutputStream(fileName);
        OutputStreamWriter out = new OutputStreamWriter(fos);
        template.process(tableClass, out);
        fos.close();
        out.close();
    }


}
