package com.intpfy.gui.components.containers;

import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class VideoContainer extends BaseContainer {

    public VideoContainer(IParent parent, String streamerName) {
        super("Video container", parent,
                By.xpath("//div[@class='OT_widget-container']/parent::" +
                        "div[.//h1[contains(@class, 'OT_name') and text()='" + streamerName + "']]"));
    }
}
