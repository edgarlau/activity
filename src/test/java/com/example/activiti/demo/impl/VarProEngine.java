package com.example.activiti.demo.impl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class VarProEngine {


    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义
     */
    @Test
    public void deploy_inputStream(){

        InputStream isBPMN = this.getClass().getResourceAsStream("/diagrams/approve.bpmn");
        InputStream isPNG = this.getClass().getResourceAsStream("/diagrams/approve.png");


        Deployment deployment = processEngine.getRepositoryService().createDeployment().addInputStream("approve.bpmn",isBPMN).addInputStream("approve.png",isPNG).deploy();
        System.out.println(deployment.getId()+"\t"+deployment.getName());
    }

    /**
     * 启动流程
     */
    @Test
    public void startProcess(){
        String piKey = "approveProcess";
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(piKey);
        System.out.println("PID: "+processInstance.getId());
    }

    /**
     * 设置变量
     * 1、runtimeService config
     * 2、taskService config
     */
    @Test
    public void setVariables(){

        TaskService taskService =  processEngine.getTaskService();
        String assigneeUser = "张三";
        String processInstanceId = "7501";

        Task task = taskService.createTaskQuery().taskAssignee(assigneeUser).processInstanceId(processInstanceId).singleResult();

        System.out.println(task.getId());

        taskService.setVariable(task.getId(),"请假人","Eric");
        taskService.setVariable(task.getId(),"请假天数",6);
        taskService.setVariable(task.getId(),"请假日期",new Date());

    }

    @Test
    public  void getVariables(){
        TaskService taskService = processEngine.getTaskService();
        String assigneeUser = "张三";
        String processInstanceId = "7501";

        Task task = taskService.createTaskQuery().taskAssignee(assigneeUser).processInstanceId(processInstanceId).singleResult();


        String userName = (String) taskService.getVariable(task.getId(),"请假人");
        Integer leaveDY= (Integer) taskService.getVariable(task.getId(),"请假天数");
        Date leaveDT= (Date) taskService.getVariable(task.getId(),"请假日期");

        System.out.println("请假人："+userName+"\n请假天数"+leaveDY+"\n请假日期："+leaveDT);
    }


    /**
     * 历史流程变量
     */
    @Test
    public void getHistoryVariables(){
        List<HistoricVariableInstance> list = processEngine.getHistoryService().createHistoricVariableInstanceQuery().variableName("请假天数").list();

        if (list != null && list.size() >0 ){
            for (HistoricVariableInstance hpi:list
                 ) {
                System.out.println(hpi.getVariableName() +"\t"+ hpi.getValue());
            }
        }

    }


    /**
     * 历史流程实例查看
     */
    @Test
    public void queryHistoricProcessInstance(){
        List<HistoricProcessInstance>  hpiList=processEngine.getHistoryService().createHistoricProcessInstanceQuery().processDefinitionKey("approveProcess").orderByProcessInstanceStartTime().desc().list();
        for (HistoricProcessInstance hpi:hpiList
             ) {
            System.out.println("pid:"+hpi.getId() +"\npdid: "+hpi.getProcessDefinitionId()+"\nstartTime: "+hpi.getStartTime()+"\nendTime: "+hpi.getEndTime()+"\nduration: "+hpi.getDurationInMillis());
            System.out.println("-----------------------------------");
        }
    }


    /**
     * 历史活动
     */
    @Test
    public void queryHistoricActivityInstance(){
        String processInstanceId = "2501";
        List<HistoricActivityInstance> haiList = processEngine.getHistoryService().createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceEndTime().asc().list();
        for (HistoricActivityInstance hai: haiList
             ) {
            System.out.println("activitiId: "+hai.getActivityId()+"\nname: "+hai.getActivityName() +"\ntype: "+hai.getActivityType()+"\npid: "+hai.getProcessInstanceId()+"\nassignee: "+hai.getAssignee()+"\nstartTime: "+hai.getStartTime()+"\nendTime: "+hai.getEndTime()+"\nduration: "+hai.getDurationInMillis());
            System.out.println("-----------------------------------");

        }
    }

    /**
     * 历史任务
     */
    @Test
    public void queryHistoricTask(){
        String processInstanceId = "2501";
        List<HistoricTaskInstance> htiList = processEngine.getHistoryService().createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByHistoricTaskInstanceEndTime().desc().list();

        for (HistoricTaskInstance hti: htiList
             ) {
            System.out.println("taskId: "+hti.getId()+"\nname: "+ hti.getName()+" \npdid: "+hti.getProcessDefinitionId() + "\npid: "+hti.getProcessInstanceId() + "\n assignee: "+ hti.getAssignee()+ "\n startTime: "+hti.getStartTime() + "\n endTime : "+hti.getEndTime() + "\n duration : "+hti.getDurationInMillis());
            System.out.println("-----------------------------------");
        }

    }

    /**
     * 历史流程变量
     */
    @Test
    public void queryHistoricVariables(){
        String processInstanceId = "2501";
        List<HistoricVariableInstance> hviList = processEngine.getHistoryService().createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).orderByVariableName().desc().list();
        if (hviList!=null && hviList.size()>0){
            for (HistoricVariableInstance hvi :hviList
                 ) {
                System.out.println("pid: "+ hvi.getProcessInstanceId() + "\nvarName "+hvi.getVariableName() + "\nvarValue: "+hvi.getValue());
                System.out.println("-----------------------------------");
            }
        }
    }
}
