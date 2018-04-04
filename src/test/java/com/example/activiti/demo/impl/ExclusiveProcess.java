package com.example.activiti.demo.impl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.io.InputStream;

public class ExclusiveProcess {


    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义
     */
    @Test
    public void deploy_inputStream(){

        InputStream isBPMN = this.getClass().getResourceAsStream("/diagrams/exclusiveGateWay.bpmn");
        InputStream isPNG = this.getClass().getResourceAsStream("/diagrams/exclusiveGateWay.png");


        Deployment deployment = processEngine.getRepositoryService().createDeployment().addInputStream("exclusiveGateWay.bpmn",isBPMN).addInputStream("exclusiveGateWay.png",isPNG).deploy();
        System.out.println(deployment.getId()+"\t"+deployment.getName());
    }



    /**
     * 启动流程
     */
    @Test
    public void startProcess(){
        String piKey = "exclusiveProcess";
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(piKey);
        System.out.println("PID: "+processInstance.getId());
    }
}
