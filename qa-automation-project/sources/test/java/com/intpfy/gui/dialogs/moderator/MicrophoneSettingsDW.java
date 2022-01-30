package com.intpfy.gui.dialogs.moderator;

import com.intpfy.gui.dialogs.Cancelable;
import com.intpfy.gui.dialogs.Confirmable;
import com.intpfy.gui.complex_elements.Dropdown;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class MicrophoneSettingsDW extends BaseComponent implements Cancelable, Confirmable {

    @ElementInfo(name = "Audio devices dropdown", findBy = @FindBy(name = "audioDevices"))
    private Dropdown audioDevicesDropdown;

    @ElementInfo(name = "Confirmation text", findBy = @FindBy(css = "div.confirm-text p"))
    private Element confirmationTextElement;

    public MicrophoneSettingsDW(IParent parent) {
        super("Microphone settings dialog window", parent, By.xpath("//div[@class='modal-content' and .//h4[text()='MICROPHONE SETTINGS']]"));
    }

    public void selectAudioDevice(String deviceName) {
        info(String.format("Select audio device '%s'.", deviceName));
        audioDevicesDropdown.selectContainsTextWithAssertion(deviceName);
    }

    public String getConfirmationText() {
        return confirmationTextElement.getText();
    }
}
