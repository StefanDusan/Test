package com.intpfy.gui.dialogs.common;

import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class MiniEventChatDW extends BaseMiniChatDW {

    public MiniEventChatDW(IParent parent) {
        super("Mini Event chat", parent, By.xpath("//div[./div[@id = 'chat_body_moderator']]"));
    }
}
