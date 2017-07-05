package com.myolnir.services;

import com.myolnir.model.JenkinsConfigData;
import com.myolnir.model.JenkinsManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JenkinsManagerTest {

    @Autowired
    private JenkinsManager manager;

    @Autowired
    private JenkinsConfigData data;

    @Test
    public void whenStartsManagerWithArgumentsThenSetsDataOK() {
        manager.start("args1", "args2");
        assertEquals(data.getJenkinsJobsUrl(), "args1/api/json/");
        assertEquals(data.getBackupUrl(), "args2");
    }

    @Test
    public void whenStartsManagerWithoutArgumentsThenSetsDataOK() {
        manager.start();
        assertEquals(data.getJenkinsJobsUrl(), "http://jenki-maste-1ewv3mc3k2e8i-219952325.eu-west-1.elb.amazonaws.com/api/json/");
        assertEquals(data.getBackupUrl(), "/tmp");
    }



}
