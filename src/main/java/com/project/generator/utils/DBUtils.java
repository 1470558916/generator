package com.project.generator.utils;


import com.project.generator.model.DataSourceVo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author YM
 * @Date 2022/2/9
 * @Version 1.0
 */
public class DBUtils {

    private static Connection connection;

    public static String DRIVER_CLASS_NAME_MYSQL8 = "com.mysql.cj.jdbc.Driver";
    public static String DRIVER_CLASS_NAME_POSTGRESQL = "org.postgresql.Driver";

    public static Connection getConnection() {
        return connection;
    }

    public static Connection initDataSource(DataSourceVo dataSourceVo) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            Class.forName(dataSourceVo.getDriverClassName());
            connection = DriverManager.getConnection(dataSourceVo.getUrl(), dataSourceVo.getUsername(), dataSourceVo.getPassword());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            connection = null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connection = null;
        }
        return connection;
    }

}
