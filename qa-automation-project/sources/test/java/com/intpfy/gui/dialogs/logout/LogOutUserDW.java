package com.intpfy.gui.dialogs.logout;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class LogOutUserDW extends BaseComponent {

    @ElementInfo(name = "Yes", findBy = @FindBy(xpath = ".//button[contains(text(),'Yes')]"))
    private Button yesButton;

    @ElementInfo(name = "No", findBy = @FindBy(xpath = ".//button[contains(text(),'No')]"))
    private Button noButton;

    public LogOutUserDW(IParent parent) {
        super("Log out user dialog window", parent, By.xpath("//div[@class = 'modal-content' " +
                "and contains(., \"You're going to LOG this user OUT. Are you sure?\") " +
                "or contains(., \"You're going to LOG OUT this user. Are you sure?\")]"));
    }

    public void confirm() {
        info("Confirm.");
        yesButton.click();
    }

    public void cancel() {
        info("Cancel.");
        noButton.click();
    }
}