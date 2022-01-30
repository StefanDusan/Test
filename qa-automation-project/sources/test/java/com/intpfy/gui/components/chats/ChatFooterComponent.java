package com.intpfy.gui.components.chats;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.TextArea;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class ChatFooterComponent extends BaseComponent {

    private static final String DISABLED = "disabled";

    @ElementInfo(name = "Type your message", findBy =
    @FindBy(css = "#moderator_message-input-textarea, div.chat-msg textarea, #partner_message-input-textarea, textarea.chat-msg__text"))
    private TextArea textArea;

    @ElementInfo(name = "Send message", findBy =
    @FindBy(css = "button.chat__send svg, button.chat-msg__send-btn svg"))
    private Button sendMessageButton;

    public ChatFooterComponent(IParent parent) {
        super("Chat footer", parent, By.cssSelector("div.chat__footer, div.chat-msg"));
    }

    public void sendMessage(String message) {
        textArea.click();
        textArea.setText(message);
        sendMessageButton.click();
    }

    public boolean isEnabled(Duration timeout) {
        return textArea.waitPropertyEmpty(DISABLED, timeout);
    }

    public boolean isDisabled(Duration timeout) {
        return textArea.waitProperty(DISABLED, "true", timeout);
    }
}
