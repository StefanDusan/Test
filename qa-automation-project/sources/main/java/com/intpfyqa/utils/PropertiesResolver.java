package com.intpfyqa.utils;

import com.google.common.base.Strings;
import com.intpfyqa.Constants;
import com.intpfyqa.run.IApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Properties;

public class PropertiesResolver {

    private final String propName;
    private String prefix;
    private String appName;
    private String envName;

    private PropertiesResolver(String propName) {
        Objects.requireNonNull(propName);
        this.propName = propName;
    }

    public static PropertiesResolver findProperty(String propName) {
        return new PropertiesResolver(propName);
    }

    public PropertiesResolver inApplication(IApplicationContext context) {
        if (null != context.getEnvironmentName())
            this.envName = context.getEnvironmentName().toLowerCase();
        if (null != context.getAlias())
            this.appName = context.getAlias().toLowerCase();
        return this;
    }

    public PropertiesResolver withPrefix(String prefix) {
        this.prefix = prefix.toLowerCase();
        return this;
    }


    public String getValue() {
        return getValue(null);
    }

    public String getValue(String defaultValue) {
        LinkedHashSet<String> orderedSearch = new LinkedHashSet<>();
        String namedPrefix = null == prefix ? "" : prefix + ".";
        String namedAppName = null == appName ? "" : appName + ".";
        String namedEnvName = null == envName ? "" : envName + ".";
        orderedSearch.add(String.format("%s.%s%s%s", Constants.TOOL_NAME, namedPrefix, namedAppName, namedEnvName));
        orderedSearch.add(String.format("%s.%s%s%s", Constants.TOOL_NAME, namedPrefix, namedEnvName, namedAppName));
        orderedSearch.add(String.format("%s.%s%s", Constants.TOOL_NAME, namedPrefix, namedEnvName));
        orderedSearch.add(String.format("%s.%s%s", Constants.TOOL_NAME, namedPrefix, namedAppName));
        orderedSearch.add(String.format("%s.%s", Constants.TOOL_NAME, namedPrefix));
        orderedSearch.add(String.format("%s.", Constants.TOOL_NAME));
        orderedSearch.add(String.format("%s%s%s", namedPrefix, namedAppName, namedEnvName));
        orderedSearch.add(String.format("%s%s%s", namedPrefix, namedEnvName, namedAppName));
        orderedSearch.add(String.format("%s%s", namedPrefix, namedEnvName));
        orderedSearch.add(String.format("%s%s", namedPrefix, namedAppName));
        orderedSearch.add(String.format("%s", namedPrefix));
        orderedSearch.add(String.format("%s%s", namedAppName, namedEnvName));
        orderedSearch.add(String.format("%s%s", namedEnvName, namedAppName));
        orderedSearch.add(String.format("%s", namedEnvName));
        orderedSearch.add(String.format("%s", namedAppName));

        String foundValue = searchProperty(orderedSearch, propName);
        if (Strings.isNullOrEmpty(foundValue)) {
            return System.getProperty(propName, defaultValue);
        } else {
            return foundValue;
        }
    }

    private String searchProperty(LinkedHashSet<String> searchOrder, String propName) {
        for (String item : searchOrder) {
            String val = System.getProperty(item + propName);
            if (Strings.isNullOrEmpty(val)) {
                InputStream stream = PropertiesResolver.class.getResourceAsStream("/" + item + "properties");
                if (null != stream) {
                    try {
                        Properties props = new Properties();
                        props.load(new InputStreamReader(stream, StandardCharsets.UTF_8));
                        val = props.getProperty(propName);
                    } catch (IOException ignore) {

                    }
                }
            }
            if (!Strings.isNullOrEmpty(val))
                return val;
        }

        return null;
    }
}