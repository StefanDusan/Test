package com.intpfy.gui.dialogs.speaker;

import com.intpfy.gui.dialogs.Closeable;
import com.intpfy.gui.dialogs.Confirmable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class HostLeftMeetingDW extends BaseComponent implements Closeable, Confirmable {

    public HostLeftMeetingDW(IParent parent) {
        super("Host left meeting dialog window", parent,
                By.xpath("//div[@class='modal-content' and contains(.,'Host has left the meeting.')]"));
    }
}