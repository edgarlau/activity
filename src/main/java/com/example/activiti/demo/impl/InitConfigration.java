package com.example.activiti.demo.impl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

public class InitConfigration implements com.example.activiti.demo.InitConfigration
{
    @Override
    public void createTable() {


        ProcessEngineConfiguration configuration= ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti");
        configuration.setJdbcDriver("com.mysql.jdbc.Driver");
        configuration.setJdbcUsername("superboot");
        configuration.setJdbcPassword("123456");

//        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP);
        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println(processEngine);
    }

    public static void main(String[] args) {
        new InitConfigration().createTable();
    }
}
