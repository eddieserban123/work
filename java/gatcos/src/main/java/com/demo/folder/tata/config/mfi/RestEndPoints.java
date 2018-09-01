package com.demo.folder.tata.config.mfi;

public class RestEndPoints {
    private UrlResolver restServices;

    public RestEndPoints(UrlResolver restServices) {
        this.restServices = restServices;
    }

    public RestEndPoints() {

    }

    public UrlResolver getUserRestServices() {
        return restServices;
    }

    public RestEndPoints setRestServices(UrlResolver restServices) {
        this.restServices = restServices;
        return this;
    }
}

