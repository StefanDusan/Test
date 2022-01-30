package com.intpfy.gui.components.moderator;

import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfy.gui.components.containers.ScreenContainer;
import com.intpfy.gui.components.containers.VideoContainer;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class SourceVideoControlComponent extends BaseComponent {

    @ElementInfo(name = "Turn Video ON complex checkbox", findBy = @FindBy(css = "div.toggle-switch"))
    private ComplexCheckbox turnVideoOnCheckbox;

    @ElementInfo(name = "Video status", findBy = @FindBy(css = "span.title"))
    private Element statusElement;

    public SourceVideoControlComponent(IParent parent) {
        super("Source video control", parent, By.cssSelector("div.source-video-control"));
    }

    public void turnOn() {
        turnVideoOnCheckbox.select();
    }

    public void turnOff() {
        turnVideoOnCheckbox.unselect();
    }

    public boolean isOn(Duration timeout) {
        return turnVideoOnCheckbox.waitIsSelected(timeout) && statusElement.waitForTextEquals("video on", timeout);
    }

    public boolean isOff(Duration timeout) {
        return turnVideoOnCheckbox.waitIsNotSelected(timeout) && statusElement.waitForTextEquals("video off", timeout);
    }

    public boolean isVideoContainerVisible(String streamerName, Duration timeout) {
        return createVideoContainer(streamerName).visible(timeout);
    }

    public boolean isScreenContainerVisible(String streamerName, Duration timeout) {
        return createScreenContainer(streamerName).visible(timeout);
    }

    public boolean isVideoContainerNotVisible(String streamerName, Duration timeout) {
        return createVideoContainer(streamerName).notVisible(timeout);
    }

    public boolean isScreenContainerNotVisible(String streamerName, Duration timeout) {
        return createScreenContainer(streamerName).notVisible(timeout);
    }

    private VideoContainer createVideoContainer(String streamerName) {
        return new VideoContainer(this, streamerName);
    }

    private ScreenContainer createScreenContainer(String streamerName) {
        return new ScreenContainer(this, streamerName);
    }
}
