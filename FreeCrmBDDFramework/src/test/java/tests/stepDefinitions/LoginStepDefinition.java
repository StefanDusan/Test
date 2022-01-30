package tests.stepDefinitions;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class LoginStepDefinition {

    WebDriver driver;
    Properties property;
    FileInputStream objFile;

    public void beforeScenario() throws FileNotFoundException {
        property = new Properties();
        objFile = new FileInputStream("src/main/resources/app.properties");
        try {
            property.load(objFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Given("^I have chosen to log in$")
    public void i_have_chosen_to_log_in() {
        if (property == null){
            try {
                beforeScenario();
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        assert property != null;
        System.setProperty("webdriver.chrome.driver", property.getProperty("chromedriver"));
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(property.getProperty("loginPageUrl"));
    }

    @And("^Login page is opened$")
    public void login_page_is_opened() {
        String title = driver.getTitle();
        System.out.println(title);
        Assert.assertEquals(property.getProperty("homePageTitle"), title);
    }

    @When("^I enter email address and password$")
    public void i_enter_email_address_and_password() {
        driver.findElement(By.xpath(property.getProperty("xpathEmail"))).sendKeys(property.getProperty("email"));
        driver.findElement(By.xpath(property.getProperty("xpathPassword"))).sendKeys(property.getProperty("password"));
    }

    @When("^I click on login button$")
    public void i_click_on_login_button() {
        //driver.findElement(By.xpath(property.getProperty("xpathLoginButton"))).click();
        WebElement loginBtn = driver.findElement(By.xpath(property.getProperty("xpathLoginButton")));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click()",loginBtn);
    }

    @Then("^I should see a home page$")
    public void i_should_see_a_home_page() {

        Assert.assertTrue(driver.findElement(By.xpath(property.getProperty("xpathMainNav"))).isDisplayed());
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(1000); // just to make it visible for a bit longer
        driver.quit();
    }
}
