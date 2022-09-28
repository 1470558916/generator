package com.project.generator.model;

import lombok.Data;

import java.util.List;

@Data
public class TableClass {

    private String tableName;
    private String voName;
    private String modelName;
    private String serviceName;
    private String serviceImplName;
    private String mapperName;
    private String controllerName;
    private String packageName;
    private String tableComment;
    private String functionName;
    private String createDate;

    private List<ColumnClass> columns;

    private ColumnClass primaryColumn;

    private Boolean batchFlag;

    private Boolean slf4jFlag;
    // 是否使用lombak
    private Boolean lombokFlag;
    // 是否使用swagger
    private Boolean swaggerFlag;

    private String path;

    @Override
    public String toString() {
        return "TableClass{" +
                "tableName='" + tableName + '\'' +
                ", voName='" + voName + '\'' +
                ", modelName='" + modelName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceImplName='" + serviceImplName + '\'' +
                ", mapperName='" + mapperName + '\'' +
                ", controllerName='" + controllerName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", tableComment='" + tableComment + '\'' +
                ", functionName='" + functionName + '\'' +
                ", createDate='" + createDate + '\'' +
                ", columns=" + columns +
                ", primaryColumn=" + primaryColumn +
                ", batchFlag=" + batchFlag +
                ", slf4jFlag=" + slf4jFlag +
                ", lombokFlag=" + lombokFlag +
                ", swaggerFlag=" + swaggerFlag +
                ", path='" + path + '\'' +
                '}';
    }
}
