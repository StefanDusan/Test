package com.intpfyqa.settings;

import java.util.HashMap;
import java.util.Map;

public final class FileSettings extends BaseSettings {

    private static final String PREFIX = "files";

    private static final FileSettings instance = new FileSettings();

    private FileSettings() {
    }

    public static FileSettings instance() {
        return instance;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public Map<String, String> listProps() {

        Map<String, String> props = new HashMap<>();

        props.put("Selenoid test file path", getSelenoidTestFilePath());

        return props;
    }

    public String getSelenoidTestFilePath() {
        return getProperty("selenoid.testFile.path", "/streams/1_MB_test_file.txt");
    }
}
