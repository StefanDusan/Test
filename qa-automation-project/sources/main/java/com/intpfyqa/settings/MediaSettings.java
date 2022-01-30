package com.intpfyqa.settings;

import java.util.HashMap;
import java.util.Map;

public class MediaSettings extends BaseSettings {

    private static final String PREFIX = "media";
    private static final MediaSettings instance = new MediaSettings();

    private MediaSettings() {
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public Map<String, String> listProps() {
        Map<String, String> out = new HashMap<>();
        out.put("Local audio file path", getLocalAudioFilePath());
        out.put("Local video file path", getLocalVideoFilePath());
        out.put("Selenoid audio file path", getSelenoidAudioFilePath());
        out.put("Selenoid video file path", getSelenoidVideoFilePath());
        return out;
    }

    public static MediaSettings instance() {
        return instance;
    }

    public String getLocalAudioFilePath() {
        return System.getProperty("user.dir") + getProperty("local.audioFilePath");
    }

    public String getLocalVideoFilePath() {
        return System.getProperty("user.dir") + getProperty("local.videoFilePath");
    }

    public String getSelenoidAudioFilePath() {
        return getProperty("selenoid.audioFilePath");
    }

    public String getSelenoidVideoFilePath() {
        return getProperty("selenoid.videoFilePath");
    }
}