package com.intpfy.gui.components.chats;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class ChatMessageMetaComponent extends BaseComponent {

    @ElementInfo(name = "Delete", findBy = @FindBy(xpath = ".//*[@* = '#svg-remove-msg']/parent::*"))
    private Button deleteButton;

    @ElementInfo(name = "Restore", findBy = @FindBy(xpath = ".//*[@* = '#svg-recovery-msg']/parent::*"))
    private Button restoreButton;

    public ChatMessageMetaComponent(IParent parent, String message) {
        super(String.format("Chat message '%s' meta.", message), parent, By.cssSelector("div.meta"));
    }

    public void deleteMessage() {
        clickDeleteButton();
    }

    public void restoreMessage() {
        restoreButton.click();
    }

    public void deleteMessageForever() {
        clickDeleteButton();
    }

    private void clickDeleteButton() {
        deleteButton.click();
    }
}
