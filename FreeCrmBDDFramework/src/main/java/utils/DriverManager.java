package utils;

import org.openqa.selenium.WebDriver;

public final class DriverManager {

    private DriverManager(){
        // private constructor
    }

    private static final ThreadLocal<WebDriver> dr =  new ThreadLocal<>();

    public static WebDriver getDriver(){
        return dr.get();
    }

    public static void setDriver(WebDriver driverRef){
        dr.set(driverRef);
    }

    public static void unload(){
        dr.remove(); // alternative to: driver = null
    }
}
