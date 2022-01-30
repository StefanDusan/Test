package com.intpfy.gui.dialogs.common;

import com.intpfy.gui.dialogs.Confirmable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class ConfirmationDW extends BaseComponent implements Confirmable {

    public ConfirmationDW(IParent parent) {
        super("Confirmation dialog window", parent,
                By.xpath("//div[@class='modal-content' and .//h4[text()='CONFIRMATION']]"));
    }
}