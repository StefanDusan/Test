package com.intpfy.gui.dialogs.logout;

import com.intpfy.gui.dialogs.Cancelable;
import com.intpfy.gui.dialogs.Confirmable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class LogOutFromEventDW extends BaseComponent implements Cancelable, Confirmable {

    public LogOutFromEventDW(IParent parent) {
        super("Log out from event dialog window", parent,
                By.xpath("//div[@class='modal-content' and contains(.,'ARE YOU SURE YOU WANT TO LOG OUT?')]"));
    }
}