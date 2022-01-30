package com.intpfy.gui.pages.event;

import com.intpfy.gui.pages.BaseAuthorizedPage;
import com.intpfy.gui.pages.LoginPage;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class LobbyPage extends BaseAuthorizedPage {

    @ElementInfo(name = "Lobby message", findBy = @FindBy(css = "div.lobby-message"))
    private Element lobbyMessageElement;

    @ElementInfo(name = "Log out", findBy = @FindBy(css = "li._logout"))
    private Button logOutButton;

    public LobbyPage(IPageContext pageContext) {
        super("Lobby Room page", pageContext);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return lobbyMessageElement.visible(timeout);
    }

    @Override
    public LoginPage logOut() {
        info("Log out.");
        logOutButton.click();
        return new LoginPage(getPageContext());
    }

    public boolean isMessageDisplayed(Duration timeout) {
        return lobbyMessageElement.visible(timeout);
    }
}
