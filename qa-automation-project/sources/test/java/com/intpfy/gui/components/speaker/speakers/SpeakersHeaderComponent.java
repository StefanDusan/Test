package com.intpfy.gui.components.speaker.speakers;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class SpeakersHeaderComponent extends BaseComponent {

    private final ParticipantsTabComponent participantsTab;
    private final LobbyTabComponent lobbyTab;

    @ElementInfo(name = "Search", findBy = @FindBy(css = "input._search"))
    private Input searchInput;

    public SpeakersHeaderComponent(IParent parent) {
        super("Speakers header", parent, By.cssSelector("div.user-list__header"));
        participantsTab = new ParticipantsTabComponent(this);
        lobbyTab = new LobbyTabComponent(this);
    }

    public boolean isOfParticipantsType(Duration timeout) {
        return participantsTab.isActive(timeout);
    }

    public void switchToLobby() {
        lobbyTab.clickAndMoveMouseOut();
    }

    public void switchToParticipants() {
        participantsTab.clickAndMoveMouseOut();
    }

    public boolean isLobbySpeakersCountEqual(int count, Duration timeout) {
        return lobbyTab.isSpeakersCountEqual(count, timeout);
    }

    public boolean isOfLobbyType(Duration timeout) {
        return lobbyTab.isActive(timeout);
    }

    public void searchInLobby(String speakerName) {
        searchInput.setText(speakerName);
    }
}
