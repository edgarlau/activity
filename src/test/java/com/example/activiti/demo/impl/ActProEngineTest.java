package com.example.activiti.demo.impl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
public class ActProEngineTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


    /**
     * re_deployment 部署对像表
     * re_procdef 流程定义表
     * ge_bytearray 资源文件表
     */
   @Test
    public void deploy() {
        Deployment deployment = processEngine.getRepositoryService().createDeployment().addClasspathResource("diagrams/Leave.bpmn").addClasspathResource("diagrams/Leave.png").deploy();

        System.out.println(deployment.getId()+"     \t "+deployment.getName());
    }


    @Test
    public void startProcess(){

        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("myProcess_1");

        System.out.println("pid:" + processInstance.getId());
    }

    @Test
    public void queryProcessDefinition(){

      List<ProcessDefinition> pdlist = processEngine.getRepositoryService().createProcessDefinitionQuery().orderByProcessDefinitionVersion().asc().list();

        for (ProcessDefinition pd:pdlist
             ) {
            System.out.println("id: "+pd.getId());
            System.out.println("name:"+pd.getName());
            System.out.println("key:"+pd.getKey());
            System.out.println("version:"+pd.getVersion());
            System.out.println("resource:"+pd.getDiagramResourceName());
            System.out.println("***************************************");
        }
    }

    @Test
    public void queryAllLastestVersion(){

        List<ProcessDefinition> pdlist = processEngine.getRepositoryService().createProcessDefinitionQuery().orderByProcessDefinitionVersion().asc().list();

        Map<String,ProcessDefinition> map = new LinkedHashMap<String,ProcessDefinition>();

        for (ProcessDefinition pd:pdlist
             ) {
            map.put(pd.getKey(),pd);
        }

        for (ProcessDefinition pd:map.values()
             ) {
            System.out.println("id:"+pd.getId()+"\nname:"+pd.getName()+"\nkey:"+pd.getKey()+"\nversion:"+pd.getVersion()+"\ndeploy:"+pd.getDeploymentId());
        }
    }

    /***
     * 查询个人任务
     */
    @Test
    public void queryPersonalTask(){
       String asssignee = "张三";
       List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee(asssignee).orderByTaskCreateTime().desc().list();
        System.out.println("========================[个人任务列表]=============================");

        if (list.size()>0){
        for (Task task:list
             ) {
            System.out.println("id:"+task.getId()+"\nname:"+task.getName()+"\ncreate:"+task.getCreateTime()+"\nassignee:"+task.getAssignee());
        }}else{
            System.out.println("\n\t######################个人任务列表为空######################\n\n");
        }
    }

    /***
     * 完成个人任务
     */
    @Test
    public void completePersonalTask(){
        String taskId="7504";
        processEngine.getTaskService().complete(taskId);
    }

    /**
     * 查询流程状态
     */
    @Test
    public void queryProcessState(){

        String processInstanceId = "12501";
        ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        if (null != processInstance){
            System.out.println("当前流程在： "+processInstance.getActivityId()+"\t"+processInstance.getName()+"\t"+processInstance.getId());
        }else{
            System.out.println("流程结束了");
        }
    }

    /**
     * 查询历史任务
     */
    @Test
    public void queryHistoryTask(){

        String assignee = "张三";
        List<HistoricTaskInstance> list  = processEngine.getHistoryService().createHistoricTaskInstanceQuery().taskAssignee(assignee).list();
        if (list != null && list.size()>0) {
            for (HistoricTaskInstance instance:
                 list) {
                System.out.println("taskId:"+instance.getId()+"\ninstanceId:"+instance.getProcessInstanceId()+"\nassignee:"+instance.getAssignee()+"\nexecutionId:"+instance.getExecutionId());
                System.out.println("startTime:"+instance.getStartTime()+"\nendTime:"+instance.getEndTime()+"\nduration:"+instance.getDurationInMillis());
                System.out.println("---------------------------------------------------------");
            }
        }
    }


    /**
     * 查询历史流程实例
     */
    @Test
    public void queryHistoryProcessInstance(){

        String processInstanceId = "12501";
        HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        System.out.println("processDefinitionId:"+historicProcessInstance.getProcessDefinitionId()+"\ninstanceId"+historicProcessInstance.getId());
        System.out.println("startTime:"+historicProcessInstance.getStartTime()+"\nEndTime:"+historicProcessInstance.getEndTime()+"\nDuration"+historicProcessInstance.getDurationInMillis());
        System.out.println("---------------------------------------------------------");
    }

}