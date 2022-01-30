package pages;

import enums.WaitStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;

public final class OrangeHRMHomePage extends BasePage {
    // nobody should extend this page thus it can be declared as final
    private final By welcomeLink = By.id("welcome");
    private final By logoutButton = By.linkText("Logout");

    public OrangeHRMHomePage clickWelcomeLink() {
        click(welcomeLink, WaitStrategy.CLICKABLE);
        return this;
    }

    public OrangeHRMLoginPage clickLogoutButton() {
        click(logoutButton, WaitStrategy.CLICKABLE);
        return new OrangeHRMLoginPage();
    }
}
