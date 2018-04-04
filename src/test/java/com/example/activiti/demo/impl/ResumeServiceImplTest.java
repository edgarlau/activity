package com.example.activiti.demo.impl;

import com.example.activiti.ActivitiApplication;
import com.example.activiti.ActivitiApplicationTests;
import org.activiti.engine.RuntimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ActivitiApplication.class)
public class ResumeServiceImplTest {

    @Autowired
    RuntimeService runtimeService;

    @Test
    public void storeResume() {
        Map<String,Object > var = new HashMap<>();
        var.put("applicationName : ","John Doe ");

        var.put("email: ","service@innjia.com");
        var.put("phone: ","123456789");

        runtimeService.startProcessInstanceByKey("myProcess",var);


    }
}