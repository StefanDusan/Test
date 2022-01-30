package pages;

import enums.WaitStrategy;
import factories.ExplicitWaitFactory;
import org.openqa.selenium.By;
import utils.DriverManager;

public class BasePage {

    protected void navigateTo(String url) {
        try {
            DriverManager.getDriver().get(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void sendKeys(By by, String value, WaitStrategy waitStrategy) {
        ExplicitWaitFactory.performExplicitWait(by, waitStrategy).sendKeys(value);
    }

    protected void click(By by, WaitStrategy waitStrategy) {
        ExplicitWaitFactory.performExplicitWait(by, waitStrategy).click();
    }

    protected String getText(By by, WaitStrategy waitStrategy) {
        return ExplicitWaitFactory.performExplicitWait(by, waitStrategy).getText();
    }
}
