package com.intpfy.gui.components.speaker.speakers;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class LobbyItemComponent extends BaseComponent {

    @ElementInfo(name = "Admit", findBy = @FindBy(css = "button._lobby-btn._admit"))
    private Button admitButton;

    @ElementInfo(name = "Reject", findBy = @FindBy(css = "button._lobby-btn._reject"))
    private Button rejectButton;

    public LobbyItemComponent(IParent parent, String speakerName) {
        super("Lobby item", parent, By.xpath(".//li[.//span[@class = 'lobby-list__name' and contains(text(),'" + speakerName + "')]]"));
    }

    public boolean areActionsAvailable(Duration timeout) {
        return isAdmitButtonVisible(timeout) && isRejectButtonVisible(timeout);
    }

    public void admit() {
        admitButton.click();
    }

    public void reject() {
        rejectButton.click();
    }

    private boolean isAdmitButtonVisible(Duration timeout) {
        return admitButton.visible(timeout);
    }

    private boolean isRejectButtonVisible(Duration timeout) {
        return rejectButton.visible(timeout);
    }
}
