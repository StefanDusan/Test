package com.intpfy.gui.components.chats;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class MiniChatMessageComponent extends BaseComponent {

    public MiniChatMessageComponent(IParent parent, By locator) {
        super("Mini chat message", parent, locator);
    }
}