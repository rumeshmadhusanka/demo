package com.example.demo;

import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

public class MysqlDataSource {
    private static DataSource instance;

    private MysqlDataSource(){}

    public static synchronized DataSource getInstance(){
        if(instance == null){
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.url("jdbc:mysql://localhost:3306/airplane?useSSL=false");
            dataSourceBuilder.username("root");
            dataSourceBuilder.password("");
            instance = dataSourceBuilder.build();
        }
        return instance;
    }
}
