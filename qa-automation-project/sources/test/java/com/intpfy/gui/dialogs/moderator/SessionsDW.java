package com.intpfy.gui.dialogs.moderator;

import com.intpfy.gui.dialogs.Cancelable;
import com.intpfy.gui.dialogs.Closeable;
import com.intpfy.gui.dialogs.Savable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class SessionsDW extends BaseComponent implements Closeable, Savable, Cancelable {

    public SessionsDW(IParent parent) {
        super("Sessions dialog window", parent, By.xpath("//div[@class='modal-content' and .//h4[text()='SESSIONS']]"));
    }
}