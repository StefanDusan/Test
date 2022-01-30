package com.intpfyqa.settings;

import com.intpfyqa.run.RunSession;
import com.intpfyqa.run.RunSessions;
import com.intpfyqa.utils.PropertiesResolver;

import java.util.Map;

public abstract class BaseSettings {

    abstract String getPrefix();

    public String getProperty(String propName, String defaultValue) {
        RunSession currentSession;
        try {
            currentSession = RunSessions.current();
        } catch (IllegalStateException ignore) {
            currentSession = null;
        }

        PropertiesResolver resolver = PropertiesResolver.findProperty(propName).withPrefix(getPrefix());
        if (null != currentSession && null != currentSession.getCurrentContext()) {
            resolver.inApplication(currentSession.getCurrentContext());
        }
        return resolver.getValue(defaultValue);
    }

    public String getProperty(String propName) {
        return getProperty(propName, null);
    }

    public abstract Map<String, String> listProps();
}