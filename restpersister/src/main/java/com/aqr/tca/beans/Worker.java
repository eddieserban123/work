package com.aqr.tca.beans;

import com.aqr.tca.utils.StatusWork;

import java.util.concurrent.Callable;

public abstract class Worker implements Callable<StatusWork> {

    private String info;

    public Worker(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public abstract StatusWork call();
}
