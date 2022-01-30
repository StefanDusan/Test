package com.intpfyqa.settings;

import java.util.HashMap;
import java.util.Map;

public final class LobbySettings extends BaseSettings {

    private static final String PREFIX = "lobby";

    private static final LobbySettings instance = new LobbySettings();

    private LobbySettings() {
    }

    public static LobbySettings instance() {
        return instance;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public Map<String, String> listProps() {

        Map<String, String> props = new HashMap<>();

        props.put("domain", getDomain());

        return props;
    }

    public String getDomain() {
        return getProperty("domain", "ifqa");
    }
}
