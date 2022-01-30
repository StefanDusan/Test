package com.intpfy.gui.components.containers;

import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class ScreenContainer extends BaseContainer {

    public ScreenContainer(IParent parent, String streamerName) {
        super("Screen container", parent,
                By.xpath("//div[@class='OT_widget-container']/parent::" +
                        "div[.//h1[contains(@class, 'OT_name') and text()='" + String.format("%s (screen)", streamerName) + "' ]]"));
    }
}
