package com.intpfy.gui.dialogs.common;

import com.intpfy.gui.dialogs.Closeable;
import com.intpfy.gui.dialogs.Confirmable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class MediaDevicesNotFoundDW extends BaseComponent implements Closeable, Confirmable {

    @ElementInfo(name = "Restart lines", findBy = @FindBy(xpath = ".//button[text() = 'RESTART LINES']"))
    private Button restartLinesButton;

    public MediaDevicesNotFoundDW(IParent parent) {
        super("Audio devices not found dialog window", parent,
                By.xpath("//div[@class='modal-content' and .//div[@class='error-text' " +
                        "and contains(.,\"You can't use or change audio device.\") " +
                        "or contains(.,\"You can't use or change video device.\") " +
                        "or contains(.,\"You can't use or change media devices.\")]]"));
    }

    public void restartLines() {
        info("Restart lines.");
        restartLinesButton.click();
    }
}