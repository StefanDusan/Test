package com.intpfy.gui.components.speaker.speakers;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

public class LobbyContainerComponent extends BaseComponent {

    public LobbyContainerComponent(IParent parent) {
        super("Lobby container", parent, By.cssSelector("div.lobby-list-container"));
    }

    public boolean isPresent(String speakerName, Duration timeout) {
        return createLobbyItemComponent(speakerName).visible(timeout);
    }

    public boolean isNotPresent(String speakerName, Duration timeout) {
        return createLobbyItemComponent(speakerName).notVisible(timeout);
    }

    public boolean areActionsAvailable(String speakerName, Duration timeout) {
        return createLobbyItemComponent(speakerName).areActionsAvailable(timeout);
    }

    public void admit(String speakerName) {
        createLobbyItemComponent(speakerName).admit();
    }

    public void reject(String speakerName) {
        createLobbyItemComponent(speakerName).reject();
    }

    private LobbyItemComponent createLobbyItemComponent(String speakerName) {
        return new LobbyItemComponent(this, speakerName);
    }
}
