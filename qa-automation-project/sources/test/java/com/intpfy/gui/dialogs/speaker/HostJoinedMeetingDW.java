package com.intpfy.gui.dialogs.speaker;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class HostJoinedMeetingDW extends BaseComponent {

    @ElementInfo(name = "Ok", findBy = @FindBy(xpath = ".//button[text()='OK']"))
    private Button okButton;

    public HostJoinedMeetingDW(IParent parent) {
        super("Host joined meeting dialog window", parent,
                By.xpath("//div[@class='modal-content' and contains(.,'Host has joined the meeting.')]"));
    }

    public void confirm() {
        info("Confirm.");
        okButton.click();
    }
}
