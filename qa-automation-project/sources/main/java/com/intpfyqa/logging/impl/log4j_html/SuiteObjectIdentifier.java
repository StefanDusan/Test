package com.intpfyqa.logging.impl.log4j_html;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SuiteObjectIdentifier {

    private final String name;
    private final LocalDateTime startTime;
    private Map<String, Object> properties;

    public SuiteObjectIdentifier(String name) {
        this(name, new HashMap<>());
    }

    public SuiteObjectIdentifier(String name, Map<String, Object> properties) {
        this.name = Objects.requireNonNull(name);
        this.properties = Objects.requireNonNull(properties);
        this.startTime = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void addProperty(String name, Object value) {
        properties.put(name, value);
    }
}
