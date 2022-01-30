package com.intpfy.gui.dialogs.speaker;

import com.intpfy.gui.dialogs.Closeable;
import com.intpfy.gui.dialogs.Confirmable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class HostHasStoppedYourStreamingDW extends BaseComponent implements Closeable, Confirmable {

    public HostHasStoppedYourStreamingDW(IParent parent) {
        super("Host has stopped your streaming dialog window", parent,
                By.xpath("//div[@class='modal-content' and contains(.,'Host has stopped your streaming')]"));
    }
}