package com.demo.folder.tata.fetcher.main;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

import java.util.Map;

public class NameChangingStrategy extends PropertyNamingStrategy {

    private Map<String, String> replaceMap;

    public NameChangingStrategy(Map<String, String> replaceMap) {
        this.replaceMap = replaceMap;
    }

    @Override
    public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        if (replaceMap.containsKey(defaultName)) {
            return replaceMap.get(defaultName);
        }
        return super.nameForSetterMethod(config, method, defaultName);
    }
}
