package com.intpfy.gui.components.web_rtc.audio;


import com.intpfy.gui.components.web_rtc.BaseWebRtcComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import com.intpfyqa.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;


public abstract class BaseWebRtcAudioComponent extends BaseWebRtcComponent {

    @ElementInfo(name = "Audio level", findBy = @FindBy(xpath = ".//td[text() = 'audioLevel']/following-sibling::td"))
    private Element audioLevelElement;

    @ElementInfo(name = "Total audio energy", findBy = @FindBy(xpath = ".//td[text() = 'totalAudioEnergy']/following-sibling::td"))
    private Element totalAudioEnergyElement;

    protected BaseWebRtcAudioComponent(String name, IParent parent, By componentLocator, ElementFromListMatcher matcher) {
        super(name, parent, componentLocator, matcher);
    }

    public double getAudioLevel() {
        return getElementValue(audioLevelElement);
    }

    public double getTotalAudioEnergy() {
        return getElementValue(totalAudioEnergyElement);
    }

    public double getTotalAudioEnergyDelta(Duration timeout) {
        double audioEnergyBeforeTimeout = getTotalAudioEnergy();
        TestUtils.sleep(timeout.toMillis());
        double audioEnergyAfterTimeout = getTotalAudioEnergy();
        return audioEnergyAfterTimeout - audioEnergyBeforeTimeout;
    }
}
