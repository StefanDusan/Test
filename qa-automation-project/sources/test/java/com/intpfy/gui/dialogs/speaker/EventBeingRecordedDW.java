package com.intpfy.gui.dialogs.speaker;

import com.intpfy.gui.dialogs.Closeable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class EventBeingRecordedDW extends BaseComponent implements Closeable {

    @ElementInfo(name = "Close", findBy = @FindBy(css = "button.recordings-notification__button-close"))
    private Button closeButton;

    public EventBeingRecordedDW(IParent parent) {
        super("Event being recorded notification", parent, By.cssSelector("div.recordings-notification"));
    }

    @Override
    public Button getCloseButton() {
        return closeButton;
    }
}