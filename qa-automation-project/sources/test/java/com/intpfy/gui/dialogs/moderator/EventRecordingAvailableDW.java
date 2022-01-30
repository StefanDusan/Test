package com.intpfy.gui.dialogs.moderator;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class EventRecordingAvailableDW extends BaseComponent {

    @ElementInfo(name = "Yes", findBy = @FindBy(xpath = ".//button[contains(text(),'Yes')]"))
    private Button yesButton;

    @ElementInfo(name = "No", findBy = @FindBy(xpath = ".//button[contains(text(),'No')]"))
    private Button noButton;

    @ElementInfo(name = "Close", findBy = @FindBy(css = "button.close"))
    private Button closeButton;

    public EventRecordingAvailableDW(IParent parent) {
        super("Event recording available dialog window", parent,
                By.xpath("//div[@class='modal-content' and .//h4[text()='Event recording available']]"));
    }

    public void confirm() {
        info("Confirm.");
        yesButton.click();
    }

    public void close() {
        info("Close.");
        closeButton.click();
    }
}
