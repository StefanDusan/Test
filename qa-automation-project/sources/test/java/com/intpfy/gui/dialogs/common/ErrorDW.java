package com.intpfy.gui.dialogs.common;

import com.intpfy.gui.dialogs.Closeable;
import com.intpfy.gui.dialogs.Confirmable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class ErrorDW extends BaseComponent implements Closeable, Confirmable {

    @ElementInfo(name = "Restart lines", findBy = @FindBy(xpath = ".//button[text() = 'RESTART LINES']"))
    private Button restartLinesButton;

    public ErrorDW(IParent parent) {
        super("Error dialog window", parent,
                By.xpath("//div[@class='modal-content' and contains(.,'ERROR MESSAGE')]"));
    }

    public void restartLines() {
        info("Restart lines.");
        restartLinesButton.click();
    }
}