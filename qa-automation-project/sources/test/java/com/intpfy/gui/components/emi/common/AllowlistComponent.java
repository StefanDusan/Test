package com.intpfy.gui.components.emi.common;

import com.intpfy.gui.dialogs.emi.AllowlistDW;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class AllowlistComponent extends BaseComponent {

    @ElementInfo(name = "Upload new", findBy = @FindBy(xpath = ".//label[contains(@class, 'btn') and text() = 'Upload new']"))
    private Button uploadNewButton;

    @ElementInfo(name = "Create", findBy = @FindBy(xpath = ".//div[contains(@class, 'btn') and text() = 'Create allowlist']"))
    private Button createButton;

    @ElementInfo(name = "Edit allowlist", findBy = @FindBy(xpath = ".//div[contains(@class, 'btn') and text() = 'Edit allowlist']"))
    private Button editAllowlistButton;

    @ElementInfo(name = "Remove", findBy = @FindBy(xpath = ".//div[contains(@class, 'btn') and text() = 'Remove']"))
    private Button removeButton;

    @ElementInfo(name = "No allowlist created", findBy = @FindBy(xpath = ".//span[text() = 'No Allowlist created']"))
    private Element notCreatedElement;

    public AllowlistComponent(IParent parent) {
        super("Allowlist", parent, By.cssSelector("div.whitelist-component"));
    }

    public AllowlistDW create() {
        createButton.click();
        return new AllowlistDW(getPage());
    }

    public boolean isCreated(Duration timeout) {
        return createButton.notVisible(timeout) && notCreatedElement.notVisible(timeout);
    }

    public boolean isNotCreated(Duration timeout) {
        return createButton.visible(timeout) && notCreatedElement.visible(timeout);
    }

    public void remove() {
        if (isCreated(Duration.ZERO)) {
            removeButton.click();
        }
    }
}
