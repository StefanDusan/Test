package com.intpfy.gui.components.emi.event_creation;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfy.gui.complex_elements.selection.ComplexRadioButton;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class SpeakerInterfaceComponent extends BaseComponent {

    @ElementInfo(name = "Event Pro complex radio button", findBy = @FindBy(xpath = ".//div[./input[@id='OnSiteOption']]"))
    private ComplexRadioButton eventProRadioButton;

    @ElementInfo(name = "Connect (WebMeet) complex radio button", findBy = @FindBy(xpath = ".//div[./input[@id='WebMeetOption']]"))
    private ComplexRadioButton webMeetRadioButton;

    @ElementInfo(name = "Connect Pro (Classroom) complex radio button", findBy = @FindBy(xpath = ".//div[./input[@id='WebMeetClassroomOption']]"))
    private ComplexRadioButton connectProRadioButton;

    public SpeakerInterfaceComponent(IParent parent) {
        super("Speaker interface", parent, By.xpath(".//label[text() = 'Default speaker interface']/parent::div"));
    }

    public void selectConnectPro() {
        connectProRadioButton.select();
    }

    public void selectEventPro() {
        eventProRadioButton.select();
    }

    public void selectConnectWebMeet() {
        webMeetRadioButton.select();
    }
}
