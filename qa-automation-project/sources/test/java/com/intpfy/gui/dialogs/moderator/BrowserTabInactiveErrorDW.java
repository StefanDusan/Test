package com.intpfy.gui.dialogs.moderator;

import com.intpfy.gui.dialogs.Closeable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class BrowserTabInactiveErrorDW extends BaseComponent implements Closeable {

    @ElementInfo(name = "Reload page", findBy = @FindBy(xpath = ".//button[text() = 'Reload page']"))
    private Button reloadPageButton;

    public BrowserTabInactiveErrorDW(IParent parent) {
        super("Browser tab inactive error dialog window", parent,
                By.xpath("//div[@class='modal-content' and .//span[text()='Browser tab was inactive for over 1 minute']]"));
    }

    public void reloadPage() {
        reloadPageButton.click();
    }
}
