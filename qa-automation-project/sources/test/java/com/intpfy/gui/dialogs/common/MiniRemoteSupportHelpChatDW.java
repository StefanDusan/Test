package com.intpfy.gui.dialogs.common;

import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class MiniRemoteSupportHelpChatDW extends BaseMiniChatDW {

    public MiniRemoteSupportHelpChatDW(IParent parent) {
        super("Mini 'Remote support help' chat", parent, By.xpath("//div[./div[@id = 'chat_body_personalChat']]"));
    }
}
