package com.intpfy.gui.components.emi.event_creation.settings;

import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfy.gui.dialogs.common.AccessChangeDW;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class AudienceSettingsComponent extends BaseComponent {

    @ElementInfo(name = "Block Audience access complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='audienceBlocked']]"))
    private ComplexCheckbox blockAccessCheckbox;

    @ElementInfo(name = "Allow access to Source Audio", findBy =
    @FindBy(xpath = ".//div[./input[@id='floorAccessAudio']]"))
    private ComplexCheckbox allowAccessToSourceAudioCheckbox;

    @ElementInfo(name = "Allow access to Source Video on the web interface (PC & Mac)", findBy =
    @FindBy(xpath = ".//div[./input[@id='floorAccessVideo']]"))
    private ComplexCheckbox allowAccessToSourceVideoOnWebCheckbox;

    @ElementInfo(name = "Enable floor fill on Web interfaces complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='audienceAccessToAutoVolume']]"))
    private ComplexCheckbox enableFloorFillOnWebCheckbox;

    @ElementInfo(name = "Allow Access to Event Chat for QA complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='floorAccessChat']]"))
    private ComplexCheckbox allowAccessToChatCheckbox;

    @ElementInfo(name = "'Pre-call test for audience' complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='allowAudiencePreCallTest']]"))
    private ComplexCheckbox enablePreCallTestCheckbox;

    public AudienceSettingsComponent(IParent parent) {
        // './self::*' locator is used as parent component locator.
        super("Audience settings", parent, By.xpath("./self::*"));
    }

    public AccessChangeDW blockAccess() {
        blockAccessCheckbox.select();
        return new AccessChangeDW(getPage());
    }

    public void allowAccessToSourceAudio() {
        allowAccessToSourceAudioCheckbox.select();
    }

    public boolean isAccessToSourceAudioAllowed(Duration timeout) {
        return allowAccessToSourceAudioCheckbox.waitIsSelected(timeout);
    }

    public AccessChangeDW allowAccessToSourceVideoOnWeb() {
        allowAccessToSourceVideoOnWebCheckbox.select();
        return new AccessChangeDW(getPage());
    }

    public boolean isAccessToSourceVideoOnWebAllowed(Duration timeout) {
        return allowAccessToSourceVideoOnWebCheckbox.waitIsSelected(timeout);
    }

    public void enableFloorFillOnWeb() {
        enableFloorFillOnWebCheckbox.select();
    }

    public void allowAccessToChat() {
        allowAccessToChatCheckbox.select();
    }

    public void enablePreCallTest() {
        enablePreCallTestCheckbox.select();
    }

    public boolean isPreCallTestEnabled(Duration timeout) {
        return enablePreCallTestCheckbox.waitIsSelected(timeout);
    }
}
