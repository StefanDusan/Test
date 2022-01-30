package com.intpfy.gui.dialogs.emi;

import com.intpfy.gui.dialogs.Cancelable;
import com.intpfy.gui.dialogs.Closeable;
import com.intpfy.gui.dialogs.Confirmable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class DeleteEventDW extends BaseComponent implements Confirmable, Closeable, Cancelable {

    public DeleteEventDW(IParent parent) {
        super("Delete event dialog window", parent, By.xpath("//div[@class='modal-content' and contains(.,'Delete event')]"));
    }
}