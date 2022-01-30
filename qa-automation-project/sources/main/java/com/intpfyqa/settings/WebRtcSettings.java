package com.intpfyqa.settings;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class WebRtcSettings extends BaseSettings {

    private static final String PREFIX = "web_rtc";
    private static final WebRtcSettings instance = new WebRtcSettings();
    public static final Duration MEASUREMENT_TIMEOUT = instance().getMeasurementTimeout();
    public static final Duration STATS_TABS_DISPLAYING_TIMEOUT = instance().getStatsTabsDisplayingTimeout();

    private WebRtcSettings() {
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public Map<String, String> listProps() {
        Map<String, String> out = new HashMap<>();
        out.put("Measurement timeout", getMeasurementTimeout().toString());
        out.put("Statistics tabs displaying timeout", getStatsTabsDisplayingTimeout().toString());
        return out;
    }

    public static WebRtcSettings instance() {
        return instance;
    }

    public Duration getMeasurementTimeout() {
        return Duration.ofSeconds(Integer.parseInt(getProperty("measurement.timeout", "10")));
    }

    public Duration getStatsTabsDisplayingTimeout() {
        return Duration.ofMinutes(Integer.parseInt(getProperty("stats_tabs_displaying.timeout", "8")));
    }
}