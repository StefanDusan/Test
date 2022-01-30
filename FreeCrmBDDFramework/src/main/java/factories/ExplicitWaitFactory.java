package factories;

import constants.FrameworkConstants;
import enums.WaitStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;

public final class ExplicitWaitFactory {

    private ExplicitWaitFactory() {
    }

    public static WebElement performExplicitWait(By by, WaitStrategy waitStrategy) {
        WebElement element = null;
        if (waitStrategy == WaitStrategy.CLICKABLE) {
            element = new WebDriverWait(DriverManager.getDriver(), FrameworkConstants.getWaittimeseconds()).
                until(ExpectedConditions.elementToBeClickable(by));
        } else if (waitStrategy == WaitStrategy.PRESENT) {
            element = new WebDriverWait(DriverManager.getDriver(), FrameworkConstants.getWaittimeseconds()).
                until(ExpectedConditions.presenceOfElementLocated(by));
        } else if (waitStrategy == WaitStrategy.VISIBLE) {
            element = new WebDriverWait(DriverManager.getDriver(), FrameworkConstants.getWaittimeseconds()).
                until(ExpectedConditions.visibilityOfElementLocated(by));
        }
        return element;
    }
}
