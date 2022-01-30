package com.intpfy.gui.components.moderator;

import com.intpfy.gui.components.speaker.speakers.LobbyActionsComponent;
import com.intpfy.gui.components.speaker.speakers.LobbyContainerComponent;
import com.intpfy.gui.components.speaker.speakers.LobbyItemComponent;
import com.intpfy.gui.dialogs.moderator.SetActiveChannelsDW;
import com.intpfy.gui.dialogs.moderator.UsersSessionDW;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.stream.Collectors;

public class UsersComponent extends BaseComponent {

    private final UsersHeaderComponent usersHeaderComponent;
    private final LobbyActionsComponent lobbyActions;
    private final LobbyContainerComponent lobbyContainer;

    @ElementInfo(name = "Users list", findBy = @FindBy(css = "div.users-expand__list"))
    private Button usersList;

    @ElementInfo(name = "Expand users", findBy = @FindBy(css = "button.users-expand-header__external"))
    private Button expandUsersButton;

    @ElementInfo(name = "Spinner", findBy = @FindBy(css = "div.spinner"))
    private Element spinnerElement;

    private final Language language;

    public UsersComponent(IParent parent, Language language) {
        super("Users", parent, By.cssSelector("div.users-expand"));
        lobbyActions = new LobbyActionsComponent(this);
        lobbyContainer = new LobbyContainerComponent(this);
        usersHeaderComponent = new UsersHeaderComponent(this);
        this.language = language;
    }

    public boolean isPresent(String username, Duration timeout) {
        return createUserComponent(username).visible(timeout);
    }

    public boolean isNotPresent(String username, Duration timeout) {
        return createUserComponent(username).notVisible(timeout);
    }

    public boolean canStream(String username, Duration timeout) {
        return createUserComponent(username).canStream(timeout);
    }

    public boolean canListen(String username, Duration timeout) {
        return createUserComponent(username).canListen(timeout);
    }

    public boolean hasIncomingLanguage(String username, Language language, Duration timeout) {
        return createUserComponent(username).hasIncomingLanguage(language, timeout);
    }

    public boolean hasOutgoingLanguage(String username, Language language, Duration timeout) {
        return createUserComponent(username).hasOutgoingLanguage(language, timeout);
    }

    public boolean isMuted(String username, Duration timeout) {
        return createUserComponent(username).isMuted(timeout);
    }

    public boolean isUnmuted(String username, Duration timeout) {
        return createUserComponent(username).isUnmuted(timeout);
    }

    public boolean isStreamingMultiple(Duration timeout) {
        return usersList.waitCssClassContains("_multiple-streaming", timeout);
    }

    public boolean isStreamingNotMultiple(Duration timeout) {
        return usersList.waitCssClassNotContains("_multiple-streaming", timeout);
    }

    public int getOrdinalPosition(String username) {
        return this.getComponentElement()
                .children(By.cssSelector("div.users-list-item__username"))
                .stream()
                .map(Element::getText)
                .collect(Collectors.toList())
                .indexOf(username) + 1;
    }

    public boolean isReloading(Duration timeout) {
        return spinnerElement.visible(timeout);
    }

    public boolean isNotReloading(Duration timeout) {
        return spinnerElement.notVisible(timeout);
    }

    public SetActiveChannelsDW openSetActiveChannelsDW(String username) {
        return createUserComponent(username).openSetActiveChannelsDW();
    }

    public UsersSessionDW openUsersSessionDW() {
        expandUsersButton.click();
        return new UsersSessionDW(getPage(), language);
    }

    public void switchToLobby() {
        usersHeaderComponent.switchToLobby();
    }

    public void switchToUsers() {
        usersHeaderComponent.switchToUsers();
    }

    public boolean isLobbySpeakersCountEqual(int count, Duration timeout) {
        return usersHeaderComponent.isLobbySpeakersCountEqual(count, timeout);
    }

    public boolean isLobbySpeakersCountNotVisible(Duration timeout) {
        return usersHeaderComponent.isLobbySpeakersCountNotVisible(timeout);
    }

    public boolean isOfLobbyType(Duration timeout) {
        return usersHeaderComponent.isOfLobbyType(timeout);
    }

    public boolean isOfUsersType(Duration timeout) {
        return usersHeaderComponent.isOfUsersType(timeout);
    }

    public boolean areLobbyActionsAvailable(Duration timeout) {
        return lobbyActions.areAvailable(timeout);
    }

    public boolean areLobbyActionsAvailable(String speakerName, Duration timeout) {
        return lobbyContainer.areActionsAvailable(speakerName, timeout);
    }

    public boolean isPresentInLobby(String speakerName, Duration timeout) {
        return lobbyContainer.isPresent(speakerName, timeout);
    }

    public boolean isNotPresentInLobby(String speakerName, Duration timeout) {
        return lobbyContainer.isNotPresent(speakerName, timeout);
    }

    public void admitInLobby(String speakerName) {
        createLobbyItemComponent(speakerName).admit();
    }

    public void rejectInLobby(String speakerName) {
        createLobbyItemComponent(speakerName).reject();
    }

    public void admitAllInLobby() {
        lobbyActions.admitAll();
    }

    public void rejectAllInLobby() {
        lobbyActions.rejectAll();
    }

    private LobbyItemComponent createLobbyItemComponent(String speakerName) {
        return new LobbyItemComponent(this, speakerName);
    }

    private UserComponent createUserComponent(String username) {
        return new UserComponent(this, username);
    }
}
