package com.intpfy.gui.pages.keycloak;

import com.intpfy.gui.pages.UsernamePage;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.user.BaseUser;
import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class KeycloakPage extends BaseAutomationPage {

    @ElementInfo(name = "Header", findBy = @FindBy(id = "kc-header"))
    private Element headerElement;

    @ElementInfo(name = "Username or email", findBy = @FindBy(id = "username"))
    private Input usernameOrEmailInput;

    @ElementInfo(name = "Sign in error message", findBy = @FindBy(id = "input-error"))
    private Element signInErrorMessageElement;

    @ElementInfo(name = "Password", findBy = @FindBy(id = "password"))
    private Input passwordInput;

    @ElementInfo(name = "Sign in", findBy = @FindBy(id = "kc-login"))
    private Button signInButton;

    public KeycloakPage(IPageContext pageContext) {
        super("Keycloak page", pageContext);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return headerElement.visible(timeout);
    }

    public DashboardPage signIn(BaseUser user) {
        proceedWithCredentials(user);
        return new DashboardPage(getPageContext());
    }

    public AudiencePage signInAsAudience(BaseUser user) {
        proceedWithCredentials(user);
        return new AudiencePage(getPageContext());
    }

    public UsernamePage proceedToUsernamePage(BaseUser user) {
        proceedWithCredentials(user);
        return new UsernamePage(getPageContext());
    }

    public boolean isSignInErrorMessageDisplayed(Duration timeout) {
        return signInErrorMessageElement.visible(timeout);
    }

    private void proceedWithCredentials(BaseUser user) {
        info(String.format("Proceed with user: '%s'", user));
        usernameOrEmailInput.setText(user.getEmail());
        passwordInput.setSecret(user.getPassword());
        signInButton.click();
    }
}
