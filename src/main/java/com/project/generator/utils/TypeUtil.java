package com.project.generator.utils;

/**
 * @Author YM
 * @Date 2022/2/9
 * @Version 1.0
 */
public class TypeUtil {

    public static String castMysqlColumnTypeToPropertyType(String type) {
        if (type != null) {
            String result = "String";
            String capType = type.toUpperCase();
            switch (capType) {
                case "VARCHAR":
                case "CHAR":
                case "TEXT":
                    result = "String";
                    break;

                case "INT":
                case "INTEGER":
                case "SMALLINT":
                case "MEDIUMINT":
                    result = "Integer";
                    break;
                case "DATETIME":
                case "DATE":
                case "TIME":
                case "YEAR":
                    result = "Date";
                    break;
                case "TIMESTAMP":
                    result = "Timestamp";
                    break;
                case "BLOB":
                    result = "byte[]";
                    break;
                case "BIT":
                case "TINYINT":
                case "BOOLEAN":
                    result = "Boolean";
                    break;
                case "BIGINT":
                    result = "Long";
                    break;
                case "FLOAT":
                    result = "Float";
                    break;
                case "DOUBLE":
                    result = "Double";
                    break;
                case "DECIMAL":
                    result = "BigDecimal";
                    break;
                default:
                    result = "String";
            }
            return result;
        }
        return null;
    }

    // type 转换
    //https://mybatis.org/mybatis-3/apidocs/reference/org/apache/ibatis/type/JdbcType.html

    public static String castMysqlColumnTypeToJdbcType(String type) {
        if (type != null) {
            String result = "VARCHAR";
            String capType = type.toUpperCase();
            switch (capType) {
                case "VARCHAR":
                case "TEXT":
                    result = "VARCHAR";
                    break;
                case "INT":
                    result = "INTEGER";
                    break;
                case "DATETIME":
                    result = "TIMESTAMP";
                    break;
                case "CLOB":
                    result = "TEXT";
                    break;
                default:
                    result = type;
            }
            return result;
        }
        return null;
    }


    public static String castPostgresColumnTypeToPropertyType(String type) {
        if (type != null) {
            String result = "String";
            String capType = type.toUpperCase();
            switch (capType) {
                case "VARCHAR":
                case "CHAR":
                case "TEXT":
                case "BPCHAR":
                    result = "String";
                    break;
                case "INT":
                case "INT2":
                case "INT4":
                case "SERIAL2":
                case "SERIAL4":
                case "INTEGER":
                case "SMALLINT":
                case "MEDIUMINT":
                    result = "Integer";
                    break;
                case "DATE":
                case "TIMESTAMP":
                    result = "Date";
                    break;
                case "TIMESTAMPZ":
                case "TIMETZ":
                    result = "Timestamp";
                    break;
                case "TIME":
                    result = "Time";
                    break;
                case "BLOB":
                    result = "byte[]";
                    break;
                case "BIT":
                case "BOOL":
                    result = "Boolean";
                    break;
                case "BIGINT":
                case "INT8":
                case "OID":
                case "SERIAL8":
                    result = "Long";
                    break;
                case "FLOAT":
                case "FLOAT4":
                    result = "Float";
                    break;
                case "DOUBLE":
                case "FLOAT8":
                case "MONEY":
                    result = "Double";
                    break;
                case "DECIMAL":
                case "NUMERIC":
                    result = "BigDecimal";
                    break;
                default:
                    result = "String";
            }
            return result;
        }
        return null;
    }

    // type 转换
    //https://mybatis.org/mybatis-3/apidocs/reference/org/apache/ibatis/type/JdbcType.html

    public static String castPostgresColumnTypeToJdbcType(String type) {
        if (type != null) {
            String result = "VARCHAR";
            String capType = type.toUpperCase();
            switch (capType) {
                case "VARCHAR":
                case "TEXT":
                    result = "VARCHAR";
                    break;
                case "CHAR":
                case "BPCHAR":
                    result = "CHAR";
                    break;
                case "INT2":
                    result = "SMALLINT";
                    break;
                case "INT":
                case "INT4":
                case "SERIAL2":
                case "SERIAL4":
                    result = "INTEGER";
                    break;
                case "INT8":
                case "OID":
                    result = "BIGINT";
                    break;
                case "SERIAL8":
                    result = "BIGINT";
                    break;
                case "FLOAT":
                    result = "FLOAT";
                    break;
                case "FLOAT4":
                    result = "REAL";
                    break;
                case "DOUBLE":
                case "FLOAT8":
                case "MONEY":
                    result = "DOUBLE";
                    break;
                case "DECIMAL":
                case "NUMERIC":
                    result = "DECIMAL";
                    break;
                case "TIMESTAMP":
                case "TIMESTAMPZ":
                    result = "TIMESTAMP";
                    break;
                case "TIMETZ":
                    result = "TIME";
                    break;
                case "DATE":
                    result = "DATE";
                    break;
                case "BIT":
                case "BOOL":
                    result = "BIT";
                    break;
                case "CLOB":
                    result = "TEXT";
                    break;
                default:
                    result = capType;
            }
            return result;
        }
        return null;
    }

    /**
     * 首字母转为小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转为大写
     *
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

}
