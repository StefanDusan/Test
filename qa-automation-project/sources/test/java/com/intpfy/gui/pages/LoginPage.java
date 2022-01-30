package com.intpfy.gui.pages;

import com.intpfy.gui.pages.devices_test.TestDevicesPage;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.gui.pages.event.LobbyPage;
import com.intpfy.gui.pages.keycloak.KeycloakPage;
import com.intpfy.gui.pages.devices_test.StepByStepPage;
import com.intpfy.user.BaseUser;
import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class LoginPage extends BaseAutomationPage {

    @ElementInfo(name = "Token log in form title", findBy = @FindBy(xpath = "//h1[text() = 'Connect to your session']"))
    private Element tokenLogInFormTitle;

    @ElementInfo(name = "Admin log in form title", findBy = @FindBy(xpath = "//h1[text() = 'Login to the admin panel']"))
    private Element adminLogInFormTitle;

    @ElementInfo(name = "Already have token", findBy = @FindBy(xpath = "//span[text() = 'ALREADY HAVE A TOKEN?']"))
    private Button haveTokenButton;

    @ElementInfo(name = "Admin log in", findBy = @FindBy(xpath = "//span[text() = 'ADMIN LOGIN']"))
    private Button adminLogInButton;

    @ElementInfo(name = "To my session", findBy = @FindBy(xpath = "//button[text() = 'To my session!']"))
    private Button toMySessionButton;

    @ElementInfo(name = "Login with keycloak", findBy = @FindBy(xpath = "//button[text() = 'Login with keycloak']"))
    private Button loginWithKeycloakButton;

    @ElementInfo(name = "Login with AD", findBy = @FindBy(xpath = "//button[text() = 'Login with AD']"))
    private Button loginWithActiveDirectoryButton;

    @ElementInfo(name = "Submit", findBy = @FindBy(css = "button.login-username__btn"))
    private Button submitButton;

    @ElementInfo(name = "Forgot password", findBy = @FindBy(xpath = "//a[text() = 'Forgot Password?']"))
    private Button forgotPasswordButton;

    @ElementInfo(name = "Token", findBy = @FindBy(id = "login-token"))
    private Input tokenInput;

    @ElementInfo(name = "Email", findBy = @FindBy(name = "email"))
    private Input emailInput;

    @ElementInfo(name = "Password", findBy = @FindBy(name = "password"))
    private Input passwordInput;

    @ElementInfo(name = "Event Login error.", findBy = @FindBy(xpath = "//*[text() = 'You are not authorised to join this event.']"))
    private Element eventLoginErrorElement;

    @ElementInfo(name = "Keycloak Login error", findBy = @FindBy(css = "p.keycloak-login-error-message"))
    private Element keycloakLoginErrorElement;

    @ElementInfo(name = "Logged out by Host message", findBy = @FindBy(css = "p._logout-message"))
    private Element loggedOutByHostMessageElement;

    @ElementInfo(name = "Test devices", findBy = @FindBy(css = ".login-token__btn-test-device"))
    private Button testDevicesButton;

    public LoginPage(IPageContext pageContext) {
        super("Login page", pageContext);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return haveTokenButton.visible(timeout) || adminLogInButton.visible(timeout);
    }

    public DashboardPage logInToEMI(BaseUser user) {
        info(String.format("Log in to EMI with user: '%s'", user.getEmail()));
        adminLogInButton.click();
        emailInput.visible();
        emailInput.setText(user.getEmail());
        passwordInput.visible();
        passwordInput.setSecret(user.getPassword());
        submitButton.click();
        return new DashboardPage(getPageContext());
    }

    public ActiveDirectoryPage proceedToActiveDirectoryPageAsAdmin() {
        info("Proceed to 'Active directory' page as Admin");
        adminLogInButton.click();
        loginWithActiveDirectoryButton.visible();
        loginWithActiveDirectoryButton.click();
        return new ActiveDirectoryPage(getPageContext());
    }

    public KeycloakPage proceedToKeycloakPageAsAdmin() {
        info("Proceed to 'Keycloak' page as Admin");
        return openKeycloakPageAsAdmin();
    }

    public UsernamePage proceedToUsernamePage(String token) {
        info(String.format("Proceed to 'Username' page with token '%s'.", token));
        tokenInput.setText(token);
        toMySessionButton.click();
        return new UsernamePage(getPageContext());
    }

    public AudiencePage logInAsAudience(String token) {
        info(String.format("Log in to Event as Audience with token '%s'.", token));
        tokenInput.setText(token);
        toMySessionButton.click();
        return new AudiencePage(getPageContext());
    }

    public ActiveDirectoryPage proceedToActiveDirectoryPage(String token) {
        info(String.format("Proceed to 'Active directory' page with token '%s'.", token));
        tokenInput.setText(token);
        toMySessionButton.click();
        return new ActiveDirectoryPage(getPageContext());
    }

    public KeycloakPage proceedToKeycloakPage(String token) {
        info(String.format("Proceed to 'Keycloak' page with token '%s'.", token));
        tokenInput.setText(token);
        toMySessionButton.click();
        return new KeycloakPage(getPageContext());
    }

    public boolean isEventLoginErrorMessageDisplayed(Duration timeout) {
        return eventLoginErrorElement.visible(timeout);
    }

    public boolean isKeycloakLoginErrorMessageDisplayed(Duration timeout) {
        return keycloakLoginErrorElement.visible(timeout);
    }
  
    public boolean isLoggedOutByHostMessageDisplayed(Duration timeout) {
        return loggedOutByHostMessageElement.visible(timeout);
    }

    public StepByStepPage testDevices() {
        info("Test devices.");
        testDevicesButton.click();
        return new StepByStepPage(getPageContext());
    }

    public TestDevicesPage logInWithPreCallTest(String token) {
        info(String.format("Log in with Pre call test with token '%s'.", token));
        tokenInput.setText(token);
        toMySessionButton.click();
        return new TestDevicesPage(getPageContext());
    }

    private KeycloakPage openKeycloakPageAsAdmin() {
        adminLogInButton.click();
        loginWithKeycloakButton.visible();
        loginWithKeycloakButton.click();
        return new KeycloakPage(getPageContext());
    }
  
    public LobbyPage proceedToLobbyPage(String token) {
        info(String.format("Proceed to 'Lobby' page with token '%s'.", token));
        tokenInput.setText(token);
        toMySessionButton.click();
        return new LobbyPage(getPageContext());
    }
}
