package com.intpfy.gui.dialogs.common;

import com.intpfy.gui.dialogs.Cancelable;
import com.intpfy.gui.dialogs.Confirmable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class AccessChangeDW extends BaseComponent implements Confirmable, Cancelable {

    public AccessChangeDW(IParent parent) {
        super("Access change dialog window", parent,
                By.xpath("//div[@class='modal-content' and contains(.,'access')]"));
    }
}