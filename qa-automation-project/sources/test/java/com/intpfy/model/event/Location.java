package com.intpfy.model.event;

public class Location {

    private final String cityName;
    private final String countryCode;
    private final String countryName;
    private final String gmt;
    private final int timeZone;
    private final String timeZoneName;

    public Location(String cityName, String countryCode, String countryName, String gmt, int timeZone, String timeZoneName) {
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.gmt = gmt;
        this.timeZone = timeZone;
        this.timeZoneName = timeZoneName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getGmt() {
        return gmt;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public String getTimeZoneName() {
        return timeZoneName;
    }
}
