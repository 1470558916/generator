package com.project.generator.model;

import lombok.Data;

/**
 * @Author YM
 * @Date 2022/2/9
 * @Version 1.0
 */
@Data
public class DataSourceVo {

    private String username;
    private String password;
    private String url;
    private String dbType;
    private String driverClassName;

}
