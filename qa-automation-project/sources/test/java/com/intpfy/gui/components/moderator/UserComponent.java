package com.intpfy.gui.components.moderator;

import com.intpfy.gui.dialogs.moderator.SetActiveChannelsDW;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class UserComponent extends BaseComponent {

    @ElementInfo(name = "Stream", findBy = @FindBy(css = "button.stream-indicator-icon"))
    private Button streamButton;

    @ElementInfo(name = "Mic", findBy = @FindBy(css = "button.mute-status-icon > i"))
    private Button micButton;

    @ElementInfo(name = "Headset", findBy = @FindBy(css = "span._listener-ico"))
    private Element headsetElement;

    @ElementInfo(name = "Incoming language", findBy = @FindBy(css = "div.users-list-item__left > div:nth-child(1)"))
    private Button incomingLanguageButton;

    @ElementInfo(name = "Outgoing language", findBy = @FindBy(css = "div.users-list-item__left > div:nth-child(3)"))
    private Button outgoingLanguageButton;

    @ElementInfo(name = "Languages", findBy = @FindBy(css = "div.users-list-item__left"))
    private Button languagesButton;

    public UserComponent(IParent parent, String username) {
        super("User " + username, parent, By.xpath(".//div[contains(@class, 'users-list-item') and contains(., '" + username + "')]"));
    }

    public boolean hasIncomingLanguage(Language language, Duration timeout) {
        return incomingLanguageButton.waitForTextEquals(language.getAbbreviation(), timeout);
    }

    public boolean hasOutgoingLanguage(Language language, Duration timeout) {
        return outgoingLanguageButton.waitForTextEquals(language.getAbbreviation(), timeout);
    }

    public boolean canStream(Duration timeout) {
        return streamButton.visible(timeout);
    }

    public boolean canListen(Duration timeout) {
        return headsetElement.visible(timeout);
    }

    public boolean isMuted(Duration timeout) {
        return isOff(micButton, timeout) && isOff(outgoingLanguageButton, timeout);
    }

    public boolean isUnmuted(Duration timeout) {
        return isOn(micButton, timeout) && isOn(outgoingLanguageButton, timeout);
    }

    public SetActiveChannelsDW openSetActiveChannelsDW() {
        languagesButton.click();
        return new SetActiveChannelsDW(getPage());
    }

    private boolean isOn(Button button, Duration timeout) {
        return button.waitCssClassContains("_on", timeout) || button.waitCssClassNotContains("_off", timeout);
    }

    private boolean isOff(Button button, Duration timeout) {
        return button.waitCssClassContains("_off", timeout);
    }
}
