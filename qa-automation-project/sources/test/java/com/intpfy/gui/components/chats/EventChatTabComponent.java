package com.intpfy.gui.components.chats;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class EventChatTabComponent extends BaseComponent {

    @ElementInfo(name = "Title", findBy = @FindBy(className = "chat-header__title"))
    private Element titleElement;

    public EventChatTabComponent(IParent parent) {
        super("Chat tab", parent, By.cssSelector("div.chat-tab"));
    }

    public boolean isOfAnnouncementsType(Duration timeout) {
        return titleElement.waitForTextEquals("announcements", timeout);
    }
}
