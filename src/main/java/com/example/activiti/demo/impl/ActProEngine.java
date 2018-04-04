package com.example.activiti.demo.impl;


import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;

public class ActProEngine {



    public static void main(String[] args) {
      ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

     RepositoryService repositoryService = processEngine.getRepositoryService();

     DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();


    }
}
