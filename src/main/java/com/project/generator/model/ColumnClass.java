package com.project.generator.model;


public class ColumnClass {
    private String propertyName;
    private String columnName;
    private String type;
    private String propertyType;
    private String jdbcType;
    private String remark;
    private Boolean isPrimary;

    @Override
    public String toString() {
        return "ColumnClass{" +
                "propertyName='" + propertyName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", type='" + type + '\'' +
                ", propertyType='" + propertyType + '\'' +
                ", jdbcType='" + jdbcType + '\'' +
                ", remark='" + remark + '\'' +
                ", isPrimary=" + isPrimary +
                '}';
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }
}
