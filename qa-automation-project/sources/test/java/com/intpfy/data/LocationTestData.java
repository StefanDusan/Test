package com.intpfy.data;

import com.intpfy.model.event.Location;

import java.util.Random;

public class LocationTestData {

    private static final Location[] locations = new Location[] {
            new Location("Belgrade", "rs", "Serbia", "+02:00", 7200, "Europe/Belgrade"),
            new Location("London", "uk", "United Kingdom", "+01:00", 3600, "Europe/London"),
            new Location("Milan", "it", "Italy", "+02:00", 7200, "Europe/Rome"),
            new Location("Minsk", "by", "Belarus", "+03:00", 10800, "Europe/Minsk"),
            new Location("Tallinn", "ee", "Estonia", "+03:00", 10800, "Europe/Tallinn")
    };

    public static Location getRandomLocation() {
        return locations[new Random().nextInt(locations.length)];
    }
}
