package com.intpfyqa;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;


public class Environment {

    private static final Environment instance = new Environment();
    private final Properties properties;

    private Environment() {
        properties = new Properties();
        try {
            properties.load(Environment.class.getResourceAsStream("/intpfy." + getEnvironmentName()
                    .toLowerCase() + ".properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Environment instance() {
        return instance;
    }

    public static String getEnvironmentName() {
        return System.getProperty("environment", "uat");
    }

    public String getPropertiesAsString() {
        Map<String, String> propertiesMap = getProperties();
        return propertiesMap.keySet().stream()
                .map(key -> key + " : " + propertiesMap.get(key))
                .collect(Collectors.joining("\n"));
    }

    public Map<String, String> getProperties() {
        Map<String, String> res = new HashMap<>();
        res.put("Environment", getEnvironmentName());
        res.put("Browser", getBrowser());
        res.put("URL", getAppUrl());
        return res;
    }

    public String getProperty(String propName) {
        return System.getProperty(propName, properties.getProperty(propName));
    }

    public String getAppUrl() {
        return getProperty("app.url");
    }

    public String getBrowser() {
        return getProperty("browser");
    }
}