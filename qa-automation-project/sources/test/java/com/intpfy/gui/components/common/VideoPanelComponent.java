package com.intpfy.gui.components.common;

import com.intpfy.gui.components.containers.PublisherScreenContainer;
import com.intpfy.gui.components.containers.PublisherVideoContainer;
import com.intpfy.gui.components.containers.ScreenContainer;
import com.intpfy.gui.components.containers.VideoContainer;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class VideoPanelComponent extends BaseComponent {

    @ElementInfo(name = "Fullscreen interface", findBy = @FindBy(css = "div.full-screen-toggle, i.full-screen-icon"))
    private Button fullscreenInterfaceButton;

    @ElementInfo(name = "Active Speaker", findBy = @FindBy(css = "button.btn-only-speaker"))
    private Button activeSpeakerButton;

    private final PublisherVideoContainer publisherVideoContainer;
    private final PublisherScreenContainer publisherScreenContainer;

    public VideoPanelComponent(IParent parent) {
        super("Video panel", parent, By.cssSelector("div.video-wr"));
        publisherVideoContainer = new PublisherVideoContainer(this);
        publisherScreenContainer = new PublisherScreenContainer(this);
    }

    public void enableFullscreenInterface() {
        fullscreenInterfaceButton.click();
    }

    public boolean isFullscreenInterfaceEnabled(Duration timeout) {
        return getComponentElement().waitCssClassContains("_full-screen", timeout);
    }

    public boolean isFullscreenInterfaceDisabled(Duration timeout) {
        return getComponentElement().waitCssClassNotContains("_full-screen", timeout);
    }

    public boolean isPublisherVideoContainerVisible(Duration timeout) {
        return publisherVideoContainer.visible(timeout);
    }

    public boolean isPublisherVideoContainerNotVisible(Duration timeout) {
        return publisherVideoContainer.notVisible(timeout);
    }

    public boolean isPublisherVideoContainerAudioOnly(Duration timeout) {
        return publisherVideoContainer.isAudioOnly(timeout);
    }

    public boolean isPublisherVideoContainerNotAudioOnly(Duration timeout) {
        return publisherVideoContainer.isNotAudioOnly(timeout);
    }

    public boolean isPublisherScreenContainerVisible(Duration timeout) {
        return publisherScreenContainer.visible(timeout);
    }

    public boolean isPublisherScreenContainerNotVisible(Duration timeout) {
        return publisherScreenContainer.notVisible(timeout);
    }

    public boolean isVideoContainerVisible(String streamerName, Duration timeout) {
        return createVideoContainer(streamerName).visible(timeout);
    }

    public boolean isVideoContainerNotVisible(String streamerName, Duration timeout) {
        return createVideoContainer(streamerName).notVisible(timeout);
    }

    public boolean isScreenContainerVisible(String streamerName, Duration timeout) {
        return createScreenContainer(streamerName).visible(timeout);
    }

    public boolean isScreenContainerNotVisible(String streamerName, Duration timeout) {
        return createScreenContainer(streamerName).notVisible(timeout);
    }

    public boolean isActiveSpeakerEnabled(Duration timeout) {
        return activeSpeakerButton.waitForTextEquals("all speakers", timeout);
    }

    public boolean isAllSpeakersEnabled(Duration timeout) {
        return activeSpeakerButton.waitForTextEquals("active speaker", timeout);
    }

    public boolean isVideoContainerActive(String streamerName, Duration timeout) {
        return createVideoContainer(streamerName).isActive(timeout);
    }

    public boolean isVideoContainerInactive(String streamerName, Duration timeout) {
        return createVideoContainer(streamerName).isInactive(timeout);
    }

    private VideoContainer createVideoContainer(String streamerName) {
        return new VideoContainer(this, streamerName);
    }

    private ScreenContainer createScreenContainer(String streamerName) {
        return new ScreenContainer(this, streamerName);
    }
}
