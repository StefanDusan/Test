package com.intpfy.gui.components.moderator;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class UsersHeaderComponent extends BaseComponent {

    private static final String ACTIVE_CLASS = "_active";

    @ElementInfo(name = "Users", findBy = @FindBy(xpath = ".//div[contains(text(),'USERS')]"))
    private Button usersButton;

    @ElementInfo(name = "Lobby", findBy = @FindBy(xpath = ".//div[contains(text(),'Lobby')]"))
    private Button lobbyButton;

    @ElementInfo(name = "Speakers counter", findBy = @FindBy(className = "users-expand-header__counter"))
    private Element speakersCounterElement;

    public UsersHeaderComponent(IParent parent) {
        super("Users header", parent, By.cssSelector("div.users-expand__header"));
    }

    public void switchToUsers() {
        usersButton.clickAndMoveMouseOut();
    }

    public void switchToLobby() {
        lobbyButton.clickAndMoveMouseOut();
    }

    public boolean isLobbySpeakersCountEqual(int count, Duration timeout) {
        return speakersCounterElement.waitForTextEquals(String.valueOf(count), timeout);
    }

    public boolean isLobbySpeakersCountNotVisible(Duration timeout) {
        return speakersCounterElement.notVisible(timeout);
    }

    public boolean isOfLobbyType(Duration timeout) {
        return isActive(lobbyButton, timeout);
    }

    public boolean isOfUsersType(Duration timeout) {
        return isActive(usersButton, timeout);
    }

    private boolean isActive(Element element, Duration timeout) {
        return element.waitCssClassContains(ACTIVE_CLASS, timeout);
    }
}
