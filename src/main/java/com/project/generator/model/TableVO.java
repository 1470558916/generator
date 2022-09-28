package com.project.generator.model;

import lombok.Data;

import java.util.List;

@Data
public class TableVO {

    private Boolean slf4jFlag;

    private Boolean lombokFlag;

    private Boolean batchFlag;

    private Boolean swaggerFlag;

    private String tempPath;

    // 包-分层路径；可不用
    private String path;

    private String dbType;

    List<TableClass> tableClassList;

    @Override
    public String toString() {
        return "TableVO{" +
                "slf4jFlag=" + slf4jFlag +
                ", lombokFlag=" + lombokFlag +
                ", batchFlag=" + batchFlag +
                ", swaggerFlag=" + swaggerFlag +
                ", tempPath='" + tempPath + '\'' +
                ", path='" + path + '\'' +
                ", dbType='" + dbType + '\'' +
                ", tableClassList=" + tableClassList +
                '}';
    }
}
