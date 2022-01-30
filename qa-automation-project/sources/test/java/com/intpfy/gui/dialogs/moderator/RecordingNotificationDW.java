package com.intpfy.gui.dialogs.moderator;

import com.intpfy.gui.dialogs.Closeable;
import com.intpfy.gui.dialogs.Confirmable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class RecordingNotificationDW extends BaseComponent implements Confirmable, Closeable {

    public RecordingNotificationDW(IParent parent) {
        super("Recording notification dialog window", parent,
                By.xpath("//div[@class='modal-content' and .//h4[text()='RECORDING NOTIFICATION']]"));
    }
}
