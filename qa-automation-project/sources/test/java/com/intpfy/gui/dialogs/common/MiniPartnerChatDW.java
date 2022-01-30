package com.intpfy.gui.dialogs.common;

import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class MiniPartnerChatDW extends BaseMiniChatDW {

    public MiniPartnerChatDW(IParent parent) {
        super("Mini Partner chat", parent, By.xpath("//div[./div[@id = 'chat_body_partner']]"));
    }
}
