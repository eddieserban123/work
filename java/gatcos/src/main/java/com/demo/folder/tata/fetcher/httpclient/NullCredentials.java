package com.demo.folder.tata.fetcher.httpclient;

import org.apache.http.auth.Credentials;

import java.security.Principal;

public class NullCredentials implements Credentials {
    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }
}
