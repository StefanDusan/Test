package pages;

import enums.ConfigProperties;
import enums.WaitStrategy;
import org.openqa.selenium.By;
import utils.ReadPropertyFile;

public final class OrangeHRMLoginPage extends BasePage {
    // nobody should extend this page thus it can be declared as final
    private final By usernameInputBox = By.id("txtUsername");
    private final By passwordInputBox = By.id("txtPassword");
    private final By loginButton = By.id("btnLogin");
    private final By invalidCredentialsError = By.id("spanMessage");

    public OrangeHRMLoginPage pageNavigateTo() {
        navigateTo(ReadPropertyFile.getValue(ConfigProperties.URLORANGEHRMLOGINPAGE));
        return this; // use this to chain methods (works for methods from the same page only)
    }

    public OrangeHRMLoginPage enterUsername(String userNameValue) {
        sendKeys(usernameInputBox, userNameValue, WaitStrategy.PRESENT);
        return this; // use this to chain methods (works for methods from the same page only)
    }

    public OrangeHRMLoginPage enterPassword(String passwordValue) {
        sendKeys(passwordInputBox, passwordValue, WaitStrategy.PRESENT);
        return this;
    }

    public OrangeHRMHomePage clickLoginButton() {
        click(loginButton, WaitStrategy.CLICKABLE);
        return new OrangeHRMHomePage();
    }

    public String invalidCredentialsErrorText() {
        return getText(invalidCredentialsError, WaitStrategy.PRESENT);
    }
}
