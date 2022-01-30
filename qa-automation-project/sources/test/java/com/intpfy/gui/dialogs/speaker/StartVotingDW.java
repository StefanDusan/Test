package com.intpfy.gui.dialogs.speaker;

import com.intpfy.gui.dialogs.Cancelable;
import com.intpfy.gui.dialogs.Closeable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class StartVotingDW extends BaseComponent implements Closeable, Cancelable {

    @ElementInfo(name = "Start", findBy = @FindBy(css = "button.main"))
    private Button startButton;

    @ElementInfo(name = "Cancel", findBy = @FindBy(css = "button.secondary"))
    private Button cancelButton;

    public StartVotingDW(IParent parent) {
        super("Start voting dialog window", parent, By.xpath("//div[@class='modal-content' and contains(.,'start voting')]"));
    }

    @Override
    public Button getCancelButton() {
        return cancelButton;
    }

    public void start() {
        info("Start.");
        startButton.click();
    }
}