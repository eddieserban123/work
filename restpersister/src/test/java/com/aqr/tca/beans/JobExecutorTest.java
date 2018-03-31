package com.aqr.tca.beans;

import com.aqr.tca.utils.StatusWorkHelper;
import org.junit.BeforeClass;
import org.junit.Test;


public class JobExecutorTest {

    public JobExecutorTest(){

    }


    @Test
    public void test1() throws InterruptedException {
        JobExecutor exec = new JobExecutor();
        exec.executeWork(() -> {
            Thread.sleep(100);
            return StatusWorkHelper.buildOkStatus("coffe");
        });
        exec.executeWork(() -> {
            Thread.sleep(100);
            return StatusWorkHelper.buildOkStatus("coffe");
        });
        exec.executeWork(() -> {
            Thread.sleep(5000);
            return StatusWorkHelper.buildOkStatus("coffe");
        });
        Thread.sleep(2500);
        System.out.println(exec.getWorkBeeingExecuted().size());
    }
}
