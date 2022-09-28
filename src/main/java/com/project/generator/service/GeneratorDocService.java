package com.project.generator.service;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.project.generator.constant.ResponseResult;
import com.project.generator.model.DataSourceVo;
import com.project.generator.utils.DBUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.ArrayList;

/**
 * @program: generator
 * @description: 数据库文档生成处理
 * @author: YM
 * @create: 2022-07-09 16:58
 */

@Slf4j
@Service
public class GeneratorDocService {

    @Value("${generate.realPath}")
    private String genRealPath;

    public ResponseResult generationDocument(DataSourceVo dataSourceVo, HttpServletResponse response) {
        String fileOutputDir = "./doc";
        if (genRealPath != null) {
            fileOutputDir = genRealPath + "/doc";
        }
        String dbType = dataSourceVo.getDbType();
        if ("mysql".equals(dbType)) {
            dataSourceVo.setDriverClassName(DBUtils.DRIVER_CLASS_NAME_MYSQL8);
        } else if ("postgresql".equals(dbType)) {
            dataSourceVo.setDriverClassName(DBUtils.DRIVER_CLASS_NAME_POSTGRESQL);
        }

        // 数据源配置
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(dataSourceVo.getDriverClassName());
        hikariConfig.setJdbcUrl(dataSourceVo.getUrl());
        hikariConfig.setUsername(dataSourceVo.getUsername());
        hikariConfig.setPassword(dataSourceVo.getPassword());
        //mysql 设置可以获取tables remarks信息
//        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        DataSource dataSource = new HikariDataSource(hikariConfig);
        //生成配置
        EngineConfig engineConfig = EngineConfig.builder()
                //生成文件路径
                .fileOutputDir(fileOutputDir)
                //打开目录
                .openOutputDir(true)
                //文件类型
                .fileType(EngineFileType.WORD)
                //生成模板实现
                .produceType(EngineTemplateType.freemarker)
                //自定义文件名称
                .fileName("数据库文件").build();

        //忽略表
        ArrayList<String> ignoreTableName = new ArrayList<>();
//        ignoreTableName.add("test_user");
//        ignoreTableName.add("test_group");
        //忽略表前缀
        ArrayList<String> ignorePrefix = new ArrayList<>();
//        ignorePrefix.add("test_");
        //忽略表后缀
        ArrayList<String> ignoreSuffix = new ArrayList<>();
//        ignoreSuffix.add("_t");
        ProcessConfig processConfig = ProcessConfig.builder()
                //指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
                //根据名称指定表生成
                .designatedTableName(new ArrayList<>())
                //根据表前缀生成
                .designatedTablePrefix(new ArrayList<>())
                //根据表后缀生成
                .designatedTableSuffix(new ArrayList<>())
                //忽略表名
                .ignoreTableName(ignoreTableName)
                //忽略表前缀
                .ignoreTablePrefix(ignorePrefix)
                //忽略表后缀
                .ignoreTableSuffix(ignoreSuffix).build();
        //配置
        Configuration config = Configuration.builder()
                .title("数据库设计文档")
                //版本
                .version("1.0.0")
                //描述
                .description("数据库设计文档生成")
                //数据源
                .dataSource(dataSource)
                //生成配置
                .engineConfig(engineConfig)
                //生成配置
                .produceConfig(processConfig)
                .build();
        //执行生成
        new DocumentationExecute(config).execute();
        return ResponseResult.success("生成成功");
    }


}
