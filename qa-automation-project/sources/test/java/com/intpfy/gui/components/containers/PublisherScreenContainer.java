package com.intpfy.gui.components.containers;

import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class PublisherScreenContainer extends BaseContainer {

    public PublisherScreenContainer(IParent parent) {
        super("Publisher screen container", parent, By.id("publisherScreenContainer"));
    }
}
