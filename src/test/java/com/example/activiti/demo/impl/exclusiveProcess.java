package com.example.activiti.demo.impl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.io.InputStream;

public class exclusiveProcess {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    String sequenceBPMN = "sequenceProcess.bpmn";
    String sequencePNG = "sequenceProcess.png";
    String preStr = "/diagrams/";

    /**
     * 流程部署
     */
    @Test
    public void deploy(){
        InputStream isBPMN = this.getClass().getResourceAsStream(preStr + sequenceBPMN);
        InputStream isPNG = this.getClass().getResourceAsStream(preStr + sequencePNG);
       Deployment deployment =  processEngine.getRepositoryService().createDeployment().addInputStream(sequenceBPMN,isBPMN).addInputStream(sequencePNG,isPNG).deploy();

        System.out.println("deploymentId: "+deployment.getId());
    }

    /**
     * 流程启动
     */
    @Test
    public void startInstance(){
        String instanceId="exclusiveProcess";
        ProcessInstance instance =  processEngine.getRuntimeService().startProcessInstanceByKey(instanceId);
        System.out.println("instanceId: "+instance.getId());
    }
}
