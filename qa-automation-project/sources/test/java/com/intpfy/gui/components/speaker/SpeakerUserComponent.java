package com.intpfy.gui.components.speaker;

import com.intpfy.gui.dialogs.logout.LogOutUserDW;
import com.intpfy.gui.dialogs.speaker.DisableStreamingDW;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class SpeakerUserComponent extends BaseComponent {

    private final NameContainerComponent nameContainer;

    @ElementInfo(name = "Log out", findBy = @FindBy(css = "i.icon._logout-ico"))
    private Button logOutButton;

    @ElementInfo(name = "Hand", findBy = @FindBy(css = "i._raisehand-ico"))
    private Element handElement;

    @ElementInfo(name = "Stream", findBy = @FindBy(css = "i._streaming-ico"))
    private Button streamButton;

    public SpeakerUserComponent(IParent parent, String speakerName) {
        super("Speaker '" + speakerName + "'", parent,
                By.xpath(".//div[@class = 'speaker__name' and contains(text(), '" + speakerName + "')]/ancestor::div[contains(@class, 'speaker')][2]"));
        nameContainer = new NameContainerComponent(this);
    }

    public boolean hasHostRole(Duration timeout) {
        return nameContainer.hasHostRole(timeout);
    }

    public LogOutUserDW logOut() {
        logOutButton.click();
        return new LogOutUserDW(getPage());
    }

    public boolean isHandRaised(Duration timeout) {
        return handElement.visible(timeout);
    }

    public boolean isHandDown(Duration timeout) {
        return handElement.notVisible(timeout);
    }

    public void allowToStream() {
        streamButton.click();
    }

    public boolean isAskedToStream(Duration timeout) {
        return streamButton.waitCssClassContains("_is-waiting", timeout);
    }

    public boolean isStreaming(Duration timeout) {
        return streamButton.waitCssClassContains("_is-streaming", timeout);
    }

    public boolean isNotStreaming() {
        return isNotStreaming(WebSettings.AJAX_TIMEOUT);
    }

    public boolean isNotStreaming(Duration timeout) {
        return streamButton.waitCssClassNotContains("_is-streaming", timeout);
    }

    public DisableStreamingDW disallowToStream() {
        streamButton.click();
        return new DisableStreamingDW(getPage());
    }

    private static final class NameContainerComponent extends BaseComponent {

        private static final String HOST = "HOST";

        @ElementInfo(name = "Role", findBy = @FindBy(className = "speaker__role"))
        private Element roleElement;

        private NameContainerComponent(IParent parent) {
            super("Name container", parent, By.className("speaker-name-container"));
        }

        private boolean hasHostRole(Duration timeout) {
            return roleElement.visible(timeout) && roleElement.waitForTextContains(HOST, timeout);
        }
    }
}
