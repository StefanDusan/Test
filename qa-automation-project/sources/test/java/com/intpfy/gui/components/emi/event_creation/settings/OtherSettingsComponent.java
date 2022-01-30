package com.intpfy.gui.components.emi.event_creation.settings;

import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class OtherSettingsComponent extends BaseComponent {

    @ElementInfo(name = "Enable Recording Panel for Moderator complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='enableModeratorRecording']]"))
    private ComplexCheckbox enableRecordingPanelForModeratorCheckbox;

    @ElementInfo(name = "Enable Document Sharing complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='documentSharing']]"))
    private ComplexCheckbox enableDocumentSharingCheckbox;

    @ElementInfo(name = "Enable Lobby Room checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='lobbyEnabled']]"))
    private ComplexCheckbox enableLobbyRoomCheckbox;


    public OtherSettingsComponent(IParent parent) {
        // './self::*' locator is used as parent component locator.
        super("Other settings", parent, By.xpath("./self::*"));
    }

    public void enableRecordingPanelForModerator() {
        enableRecordingPanelForModeratorCheckbox.select();
    }

    public void enableDocumentSharing() {
        enableDocumentSharingCheckbox.select();
    }

    public boolean isDocumentSharingEnabled(Duration timeout) {
        return enableDocumentSharingCheckbox.waitIsSelected(timeout);
    }

    public void enableLobbyRoom() {
        enableLobbyRoomCheckbox.select();
    }

    public boolean isLobbyRoomEnabled(Duration timeout) {
        return enableLobbyRoomCheckbox.waitIsSelected(timeout);
    }

}
