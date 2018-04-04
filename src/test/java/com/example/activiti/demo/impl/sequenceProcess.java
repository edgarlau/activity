package com.example.activiti.demo.impl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class sequenceProcess {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    String processInstanceId = "sequenceProcess";
    String processBPMN = "sequenceProcess.bpmn";
    String processPNG ="sequenceProcess.png";

    /**
     * 部署启动
     */
    @Test
    public void deploySequenceFlow(){
        //部署流程
        InputStream isBPMN = this.getClass().getResourceAsStream("/diagrams/"+processBPMN);
        InputStream isPNG = this.getClass().getResourceAsStream("/diagrams/"+processPNG);
        processEngine.getRepositoryService().createDeployment().addInputStream(processBPMN,isBPMN).addInputStream(processPNG,isPNG).deploy();

        //启动流程
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey(processInstanceId);

        System.out.println("pid : "+ pi.getId());
    }

    /**
     * 个人任务
     */
    @Test
    public void finePersonTaskList(){
        String userId = "张三";
        List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee(userId).list();
        for (Task task: list
             ) {
            System.out.println("id: "+task.getId()+"\nname: "+task.getName()+"\nassignee: "+task.getAssignee()+"\nCreateTime: "+task.getCreateTime()+"\nexecutionId: "+task.getExecutionId());
            System.out.println("-------------------------------------------------------");
        }
    }


    /**
     * 完成任务
     */
    @Test
    public void completeTask(){
        String taskId = "7504";
        Map<String,Object> variables = new HashMap<String,Object>();
        variables.put("message","no");
        processEngine.getTaskService().complete(taskId,variables);
        System.out.println("--------------------------任务完成-----------------------------");
    }

    @Test
    public void instanceState(){
        String instanceId = "7504";
        ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if(processInstance ==null){
            System.out.println("实例执行完成");
        }else {
            System.out.println("实例还在执行");
        }

    }
}
