package com.intpfy.gui.components.emi.event_creation.settings;

import com.intpfy.exception.NoSuchEventTypeException;
import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfy.gui.components.emi.event_creation.HostPasswordComponent;
import com.intpfy.gui.components.emi.event_creation.SpeakerInterfaceComponent;
import com.intpfy.model.event.EventType;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class GeneralSettingsComponent extends BaseComponent {

    private final SpeakerInterfaceComponent speakerInterface;

    @ElementInfo(name = "Hide delegates status complex checkbox", findBy = @FindBy(xpath = ".//div[./input[@id='hideParticipants']]"))
    private ComplexCheckbox hideDelegatesStatusCheckbox;

    @ElementInfo(name = "Default full screen for speakers complex checkbox", findBy = @FindBy(xpath = ".//div[./input[@id='fullScreen']]"))
    private ComplexCheckbox enableDefaultFullscreenForSpeakersCheckbox;

    private final HostPasswordComponent hostPassword;

    public GeneralSettingsComponent(IParent parent) {
        super("General settings", parent, By.tagName("div"));
        speakerInterface = new SpeakerInterfaceComponent(this);
        hostPassword = new HostPasswordComponent(this);
    }

    public void selectEventType(EventType eventType) {

        switch (eventType) {

            case EventPro:
                selectEventPro();
                break;
            case WebMeet:
                selectConnectWebMeet();
                break;
            case Classroom:
                selectConnectPro();
                break;
            default:
                throw new NoSuchEventTypeException(eventType);
        }
    }

    public void hideDelegatesStatus() {
        hideDelegatesStatusCheckbox.select();
    }

    public void generateHostPassword() {
        hostPassword.generate();
    }

    public String getHostPassword() {
        return hostPassword.get();
    }

    public void setHostPassword(String password) {
        hostPassword.set(password);
    }

    public boolean isHostPasswordEqual(String password, Duration timeout) {
        return hostPassword.isEqual(password, timeout);
    }

    private void selectConnectPro() {
        speakerInterface.selectConnectPro();
        isConnectProSelected();
    }

    private void selectEventPro() {
        speakerInterface.selectEventPro();
        isEventProSelected();
    }

    private void selectConnectWebMeet() {
        speakerInterface.selectConnectWebMeet();
        isConnectWebMeetSelected();
    }

    private boolean isConnectProSelected() {
        return hostPassword.visible();
    }

    private boolean isEventProSelected() {
        return enableDefaultFullscreenForSpeakersCheckbox.notVisible();
    }

    private boolean isConnectWebMeetSelected() {
        return enableDefaultFullscreenForSpeakersCheckbox.visible() && hostPassword.notVisible();
    }
}
