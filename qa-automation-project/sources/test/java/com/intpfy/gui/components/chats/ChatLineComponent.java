package com.intpfy.gui.components.chats;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

public class ChatLineComponent extends BaseComponent {

    private final ChatMessageComponent chatMessage;
    private final ChatMessageMetaComponent chatMessageMeta;

    public ChatLineComponent(IParent parent, String message) {
        super(String.format("Chat line for message '%s'", message), parent,
                By.xpath(".//div[contains(@class, '__line') and .//*[text() = '" + message + "']]"));
        chatMessage = new ChatMessageComponent(this, message);
        chatMessageMeta = new ChatMessageMetaComponent(this, message);
    }

    public String getUsernameColor() {
        return chatMessage.getUsernameColor();
    }

    public void deleteMessage() {
        chatMessageMeta.deleteMessage();
    }

    public boolean isMessageHidden(Duration timeout) {
        return chatMessage.isHidden(timeout);
    }

    public void restoreMessage() {
        chatMessageMeta.restoreMessage();
    }

    public void deleteMessageForever() {
        chatMessageMeta.deleteMessageForever();
    }
}
