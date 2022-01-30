package tests.seleniumClass;

import enums.ConfigProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import utils.DriverManager;
import utils.ReadPropertyFile;

import java.util.Locale;

public final class FirstTest extends BaseTest {

    @Test (enabled = false)
    public void test1() throws Exception {
        DriverManager.getDriver().get(ReadPropertyFile.getValue(ConfigProperties.URLGOOGLE));
        DriverManager.getDriver().findElement(By.name("q")).sendKeys("Selenium Automation", Keys.ENTER);
        Thread.sleep(1500);
    }

    @Test (enabled = false)
    public void test2() throws Exception {
        DriverManager.getDriver().get(ReadPropertyFile.getValue(ConfigProperties.URLGOOGLE));
        DriverManager.getDriver().findElement(By.name("q")).sendKeys("Automation", Keys.ENTER);
        Thread.sleep(1500);
    }
/*    public void test2() throws InterruptedException {
        // initial version, no constants using, no basetest class, no driver util class
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/java/resources/drivers.chrome.v94/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://google.com");
        driver.findElement(By.name("q")).sendKeys("Selenium Automation", Keys.ENTER);
        Thread.sleep(1500);
        driver.quit();
    }*/
}
