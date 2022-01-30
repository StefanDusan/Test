package utils;

import constants.FrameworkConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class Driver {

    private Driver(){} // private constructor

    public static void initDriver() {
        if (Objects.isNull(DriverManager.getDriver())) { // initialize the driver if it is not already initialized
            System.setProperty("webdriver.chrome.driver", FrameworkConstants.getChromeDriverPath()); // constants introduced
            WebDriver driver = new ChromeDriver();
            DriverManager.setDriver(driver);
            DriverManager.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
    }

    public static void quitDriver() {
        if (Objects.nonNull(DriverManager.getDriver())) { // terminate the driver only if it is initialized
            DriverManager.getDriver().quit();
            DriverManager.unload();
        }
    }

}
