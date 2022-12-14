package com.project.generator.controller;

import cn.hutool.core.date.DateUtil;
import com.google.common.base.CaseFormat;
import com.project.generator.constant.ResponseResult;
import com.project.generator.model.DataSourceVo;
import com.project.generator.model.JdbcParam;
import com.project.generator.model.TableClass;
import com.project.generator.model.TableVO;
import com.project.generator.service.GeneratorDocService;
import com.project.generator.service.GeneratorService;
import com.project.generator.utils.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author YM
 * @Date 2022/03/20
 * @Description
 */
@Slf4j
@RestController
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private GeneratorDocService generatorDocService;

    @PostMapping("/connect")
    public ResponseResult connect(@RequestBody DataSourceVo dataSourceVo){
        if ("mysql".equals(dataSourceVo.getDbType())){
           dataSourceVo.setDriverClassName(DBUtils.DRIVER_CLASS_NAME_MYSQL8);
        }else if("postgresql".equals(dataSourceVo.getDbType())){
            dataSourceVo.setDriverClassName(DBUtils.DRIVER_CLASS_NAME_POSTGRESQL);
        }
        Connection connection = DBUtils.initDataSource(dataSourceVo);
        if (connection != null) {
            return ResponseResult.success(dataSourceVo.getDbType() + "θΏζ₯ζε");
        }
        return ResponseResult.fail(dataSourceVo.getDbType() +"θΏζ₯ε€±θ΄₯");
    }

    // θ·εθ‘¨ε
    @PostMapping("/config")
    public ResponseResult configTable(@RequestBody JdbcParam param){
        if (!"mybatis".equals(param.getJdbcType())){
            ResponseResult.error("η?εδ»ζ―ζmybatisζ¨‘ζΏ");
        }
        String packageName = param.getPackageName();
        try {
            Connection connection = DBUtils.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(connection.getCatalog(), null, "%", new String[]{"TABLE","VIEW"});
            List<TableClass> tableClassList = new ArrayList<>();
            while (tables.next()) {
                TableClass tableClass = new TableClass();
                tableClass.setPackageName(packageName);
                String table_name = tables.getString("TABLE_NAME");
                String modelName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table_name);
                tableClass.setTableName(table_name);
                tableClass.setModelName(modelName);
                String remarks = tables.getString("remarks");
                if (StringUtils.isEmpty(remarks)) {
                    remarks = modelName;
                }
                tableClass.setTableComment(remarks);
                tableClass.setFunctionName(remarks);
                tableClass.setControllerName(modelName + "Controller");
                tableClass.setMapperName(modelName + "Mapper");
                tableClass.setServiceName(modelName+"Service");
                tableClass.setServiceImplName(modelName+"ServiceImpl");
                tableClass.setVoName(modelName+"VO");
                tableClass.setCreateDate(DateUtil.formatDate(new Date()));
                tableClassList.add(tableClass);
            }
            return ResponseResult.success("ζ°ζ?εΊδΏ‘ζ―θ―»εεΉΆιη½?ζε", tableClassList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.error("ζ°ζ?εΊδΏ‘ζ―θ―»εε€±θ΄₯!");
    }


    @PostMapping("/generateCode")
    public ResponseResult generateCode(@RequestBody TableVO tableVO, HttpServletRequest req, HttpServletResponse response) {
        if (tableVO.getTableClassList()==null || tableVO.getTableClassList().size()==0){
            log.error("δ»£η ηζε€±θ΄₯οΌθ‘¨ζ°ζ?δΈθ½δΈΊη©Ί");
            return ResponseResult.error("δ»£η ηζε€±θ΄₯οΌθ‘¨ζ°ζ?δΈθ½δΈΊη©Ί");
        }
        return generatorService.generateCode(tableVO.getTableClassList(),tableVO,
                req.getSession().getServletContext().getRealPath("/"),response);
    }


    @PostMapping("/generationDoc")
    public ResponseResult generationDocument(@RequestBody DataSourceVo dataSourceVo,HttpServletResponse response){
        return generatorDocService.generationDocument(dataSourceVo,response);
    }

}
