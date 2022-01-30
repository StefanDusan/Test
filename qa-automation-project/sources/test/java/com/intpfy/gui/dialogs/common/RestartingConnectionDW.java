package com.intpfy.gui.dialogs.common;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class RestartingConnectionDW extends BaseComponent {

    public RestartingConnectionDW(IParent parent) {
        super("Restarting connection dialog window", parent, By.cssSelector("div.reconnection-mask__info"));
    }
}