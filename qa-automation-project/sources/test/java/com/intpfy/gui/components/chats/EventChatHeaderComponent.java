package com.intpfy.gui.components.chats;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class EventChatHeaderComponent extends BaseComponent {

    @ElementInfo(name = "Files", findBy = @FindBy(css = "div.file-tab"))
    private Button filesButton;

    private final EventChatTabComponent eventChatTab;

    public EventChatHeaderComponent(IParent parent) {
        super("Chat header", parent, By.cssSelector("div.chat__header"));
        eventChatTab = new EventChatTabComponent(this);
    }

    public boolean isOfAnnouncementsType(Duration timeout) {
        return eventChatTab.isOfAnnouncementsType(timeout);
    }

    public void switchToChat() {
        eventChatTab.clickAndMoveMouseOut();
    }

    public void switchToFiles() {
        filesButton.clickAndMoveMouseOut();
    }
}
