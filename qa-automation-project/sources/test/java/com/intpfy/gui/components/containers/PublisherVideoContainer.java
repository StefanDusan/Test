package com.intpfy.gui.components.containers;

import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class PublisherVideoContainer extends BaseContainer {

    @ElementInfo(name = "Container root", findBy = @FindBy(css = "div.OT_root"))
    private Element rootElement;

    public PublisherVideoContainer(IParent parent) {
        super("Publisher video container", parent, By.id("publisherVideoContainer"));
    }

    public boolean isAudioOnly(Duration timeout) {
        return rootElement.waitCssClassContains("audio-only", timeout);
    }

    public boolean isNotAudioOnly(Duration timeout) {
        return rootElement.waitCssClassNotContains("audio-only", timeout);
    }
}
