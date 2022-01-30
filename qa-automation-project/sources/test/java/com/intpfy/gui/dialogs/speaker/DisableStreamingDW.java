package com.intpfy.gui.dialogs.speaker;

import com.intpfy.gui.dialogs.Cancelable;
import com.intpfy.gui.dialogs.Confirmable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class DisableStreamingDW extends BaseComponent implements Cancelable, Confirmable {

    public DisableStreamingDW(IParent parent) {
        super("'Disable streaming' dialog window", parent,
                By.xpath("//div[@class = 'modal-content' " +
                        "and contains(., \"You're going to DISABLE this user's outgoing streaming. Are you sure?\")]"));
    }
}