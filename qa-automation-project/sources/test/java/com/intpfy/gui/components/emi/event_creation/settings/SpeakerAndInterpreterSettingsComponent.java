package com.intpfy.gui.components.emi.event_creation.settings;

import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfy.gui.dialogs.common.AccessChangeDW;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class SpeakerAndInterpreterSettingsComponent extends BaseComponent {

    @ElementInfo(name = "Enable polling complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='enablePolling']]"))
    private ComplexCheckbox enablePollingCheckbox;

    @ElementInfo(name = "Do not allow I-token users to type in Event Chat complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='interpreterBlockToTypeInChat']]"))
    private ComplexCheckbox doNotAllowITokenToUseEventChatCheckbox;

    @ElementInfo(name = "Do not allow S-token users to type in Event Chat (excl. host/chairperson) complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='disableSpeakerTypeEventChat']]"))
    private ComplexCheckbox doNotAllowSTokenToUseEventChatCheckbox;

    @ElementInfo(name = "Use floor fill for Speaker if interpreter's voice is below the audible threshold complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='floorToLanguageOnInterpreterSilence']]"))
    private ComplexCheckbox useFloorFillForSpeakerCheckbox;

    @ElementInfo(name = "Interpreter desks: Floor is inserted by external hardware into language channels complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='preventSourceDuplicate']]"))
    private ComplexCheckbox enableFloorPassThroughCheckbox;

    @ElementInfo(name = "'Pre-call test for interpreter / speaker' complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='allowSpeakerInterpreterPreCallTest']]"))
    private ComplexCheckbox enablePreCallTestCheckbox;

    public SpeakerAndInterpreterSettingsComponent(IParent parent) {
        // './self::*' locator is used as parent component locator.
        super("Speaker and Interpreter settings", parent, By.xpath("./self::*"));
    }

    public void enablePreCallTest() {
        enablePreCallTestCheckbox.select();
    }

    public boolean isPreCallTestEnabled(Duration timeout) {
        return enablePreCallTestCheckbox.waitIsSelected(timeout);
    }

    public void enablePolling() {
        enablePollingCheckbox.select();
    }

    public void enableEventChatForInterpreter() {
        doNotAllowITokenToUseEventChatCheckbox.unselect();
    }

    public AccessChangeDW disableEventChatForInterpreter() {
        doNotAllowITokenToUseEventChatCheckbox.select();
        return new AccessChangeDW(getPage());
    }

    public boolean isEventChatEnabledForInterpreter(Duration timeout) {
        return doNotAllowITokenToUseEventChatCheckbox.waitIsNotSelected(timeout);
    }

    public boolean isEventChatDisabledForInterpreter(Duration timeout) {
        return doNotAllowITokenToUseEventChatCheckbox.waitIsSelected(timeout);
    }

    public AccessChangeDW disableEventChatForSpeaker() {
        doNotAllowSTokenToUseEventChatCheckbox.select();
        return new AccessChangeDW(getPage());
    }

    public boolean isEventChatDisabledForSpeaker(Duration timeout) {
        return doNotAllowSTokenToUseEventChatCheckbox.waitIsSelected(timeout);
    }

    public void enableAutoVolumeForSpeaker() {
        useFloorFillForSpeakerCheckbox.select();
    }

    public void enableFloorPassThrough() {
        enableFloorPassThroughCheckbox.select();
    }
}
