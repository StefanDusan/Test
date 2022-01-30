package com.intpfy.gui.dialogs.common;

import com.intpfy.gui.dialogs.Cancelable;
import com.intpfy.gui.dialogs.Closeable;
import com.intpfy.gui.dialogs.Savable;
import com.intpfy.gui.complex_elements.Dropdown;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class CallSettingsDW extends BaseComponent implements Closeable, Savable, Cancelable {

    @ElementInfo(name = "Audio only", findBy = @FindBy(xpath = ".//button[text() = 'Audio Only']"))
    private Button audioOnlyButton;

    @ElementInfo(name = "Video", findBy = @FindBy(xpath = ".//button[text() = 'Video']"))
    private Button videoButton;

    @ElementInfo(name = "Meeting control only", findBy = @FindBy(xpath = ".//button[text() = 'Meeting control only']"))
    private Button meetingControlOnlyButton;

    @ElementInfo(name = "Video dropdown", findBy =
    @FindBy(xpath = ".//div[contains(@ng-if, 'isNoVideoDevices')]//div[contains(@class, 'ui-select-container')]"))
    private Dropdown videoDropdown;

    @ElementInfo(name = "Audio dropdown", findBy =
    @FindBy(xpath = ".//div[contains(@ng-if, 'isNoAudioDevices')]//div[contains(@class, 'ui-select-container')]"))
    private Dropdown audioDropdown;

    public CallSettingsDW(IParent parent) {
        super("Call settings dialog window", parent, By.xpath("//div[@class='modal-content' and .//h4[text()='CALL SETTINGS']]"));
    }

    public void selectMeetingControlOnly() {
        info("Select 'Meeting control only'.");
        meetingControlOnlyButton.click();
    }

    public void selectAudioOnly() {
        info("Select 'Audio only'.");
        audioOnlyButton.click();
    }

    public void selectVideo() {
        info("Select 'Video'.");
        videoButton.click();
    }

    public void selectVideoDevice(String device) {
        info(String.format("Select video device '%s'.", device));
        videoDropdown.selectWithAssertion(device);
    }

    public void selectAudioDevice(String device) {
        info(String.format("Select audio device '%s'.", device));
        audioDropdown.selectWithAssertion(device);
    }
}
