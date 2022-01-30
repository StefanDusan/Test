package com.intpfy.gui.components.speaker.speakers;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class LobbyActionsComponent extends BaseComponent {

    @ElementInfo(name = "Admit All", findBy = @FindBy(css = "button._admit"))
    private Button admitAllButton;

    @ElementInfo(name = "Reject All", findBy = @FindBy(css = "button._reject"))
    private Button rejectAllButton;

    public LobbyActionsComponent(IParent parent) {
        super("Lobby actions", parent, By.cssSelector("div.user-list__lobby-actions"));
    }

    public boolean areAvailable(Duration timeout) {
        return admitAllButton.visible(timeout) && rejectAllButton.visible(timeout);
    }

    public void admitAll() {
        admitAllButton.click();
    }

    public void rejectAll() {
        rejectAllButton.click();
    }
}
