package com.intpfy.gui.dialogs.logout;

import com.intpfy.gui.dialogs.Confirmable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class LogOutWarningDW extends BaseComponent implements Confirmable {

    public LogOutWarningDW(IParent parent) {
        super("Log out warning dialog window", parent, By.xpath("//div[@class = 'modal-content' and contains(., 'Host has logged you out')]"));
    }
}