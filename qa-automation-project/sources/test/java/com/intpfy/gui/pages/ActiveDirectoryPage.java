package com.intpfy.gui.pages;

import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class ActiveDirectoryPage extends BaseAutomationPage {

    @ElementInfo(name = "Email", findBy = @FindBy(id = "userNameInput"))
    private Input emailInput;

    @ElementInfo(name = "Password", findBy = @FindBy(id = "passwordInput"))
    private Input passwordInput;

    @ElementInfo(name = "Sign in", findBy = @FindBy(id = "submitButton"))
    private Button signInButton;

    public ActiveDirectoryPage(IPageContext pageContext) {
        super("Active directory page", pageContext);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return emailInput.visible(timeout) && passwordInput.visible(timeout);
    }

    public DashboardPage logInAsOrganization(String email, String password) {
        info(String.format("Log in as Organization with email '%s'.", email));
        logIn(email, password);
        return new DashboardPage(getPageContext());
    }

    public AudiencePage logInAsAudience(String email, String password) {
        info(String.format("Log in as Audience with email '%s'.", email));
        logIn(email, password);
        return new AudiencePage(getPageContext());
    }

    public LanguageSettingsDW logInAsInterpreter(String email, String password) {
        info(String.format("Log in as Interpreter with email '%s'.", email));
        logIn(email, password);
        return new LanguageSettingsDW(getPage());
    }

    public SpeakerPage logInAsSpeakerToEventPro(String email, String password) {
        info(String.format("Log in as Speaker to 'Event Pro' with email '%s'.", email));
        logIn(email, password);
        return new SpeakerPage(getPageContext());
    }

    private void logIn(String email, String password) {
        emailInput.setText(email);
        passwordInput.setSecret(password);
        signInButton.click();
    }
}
