package com.intpfy.gui.pages;

import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfy.gui.dialogs.common.CallSettingsDW;
import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.gui.pages.devices_test.TestDevicesPage;
import com.intpfy.gui.pages.event.*;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class UsernamePage extends BaseAuthorizedPage {

    @ElementInfo(name = "Page title", findBy =
    @FindBy(xpath = "//h1[text()='Please choose a username that will be used in a chat']"))
    private Element pageTitle;

    @ElementInfo(name = "Back", findBy = @FindBy(css = "button.link-previous"))
    private Button backButton;

    @ElementInfo(name = "Username", findBy = @FindBy(id = "username-input"))
    private Input usernameInput;

    @ElementInfo(name = "Advanced Channel Monitoring complex checkbox", findBy = @FindBy(css = "div.login-username__checkbox"))
    private ComplexCheckbox advancedChannelMonitoringCheckbox;

    @ElementInfo(name = "Meeting Host complex checkbox", findBy = @FindBy(css = "div.login-username__checkbox"))
    private ComplexCheckbox meetingHostCheckbox;

    @ElementInfo(name = "Host password", findBy = @FindBy(name = "chairpersonToken"))
    private Input hostPasswordInput;

    @ElementInfo(name = "Submit", findBy = @FindBy(css = "button.login-username__btn"))
    private Button submitButton;

    public UsernamePage(IPageContext pageContext) {
        super("Username page", pageContext);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return pageTitle.visible(timeout);
    }

    @Override
    public LoginPage logOut() {
        info("Log out.");
        backButton.click();
        return new LoginPage(getPageContext());
    }

    public AudiencePage logInAsAudience(String username) {
        info(String.format("Log in as Audience with username '%s'.", username));
        usernameInput.setText(username);
        submitButton.click();
        return new AudiencePage(getPageContext());
    }

    public LanguageSettingsDW logInAsInterpreter(String username) {
        info(String.format("Log in as Interpreter with username '%s'.", username));
        usernameInput.setText(username);
        submitButton.click();
        return new LanguageSettingsDW(new InterpreterPage(getPageContext()));
    }

    public SpeakerPage logInAsSpeakerWithoutCallSettings(String username) {
        info(String.format("Log in as Speaker with username '%s'.", username));
        usernameInput.setText(username);
        submitButton.click();
        return new SpeakerPage(getPageContext());
    }

    public LobbyPage logInToLobbyAsSpeaker(String username) {
        info(String.format("Log in to Lobby as Speaker with username '%s'.", username));
        usernameInput.setText(username);
        submitButton.click();
        return new LobbyPage(getPageContext());
    }

    public CallSettingsDW logInAsSpeaker(String username) {
        info(String.format("Log in as Speaker with username '%s'.", username));
        usernameInput.setText(username);
        submitButton.click();
        return new CallSettingsDW(new SpeakerPage(getPageContext()));
    }

    public TestDevicesPage logInWithPreCallTest(String username) {
        info(String.format("Log in with Pre call test with username '%s'.", username));
        usernameInput.setText(username);
        submitButton.click();
        return new TestDevicesPage(getPageContext());
    }

    public CallSettingsDW logInAsHost(String username, String password) {
        info(String.format("Log in as Host with username '%s' and password '%s'.", username, password));
        usernameInput.setText(username);
        meetingHostCheckbox.select();
        hostPasswordInput.visible();
        hostPasswordInput.setText(password);
        submitButton.click();
        return new CallSettingsDW(new SpeakerPage(getPageContext()));
    }

    public ModeratorPage logInAsModerator(String username) {
        info(String.format("Log in as Moderator with username '%s'.", username));
        usernameInput.setText(username);
        submitButton.click();
        return new ModeratorPage(getPageContext());
    }

    public AdvancedModeratorPage logInAsModeratorWithAdvancedMonitoring(String username) {
        info(String.format("Log in as Moderator with Advanced Monitoring with username '%s'.", username));
        usernameInput.setText(username);
        advancedChannelMonitoringCheckbox.select();
        submitButton.click();
        return new AdvancedModeratorPage(getPageContext());
    }
}
