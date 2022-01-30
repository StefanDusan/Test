package com.intpfy.gui.components.emi.profile;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class ProfileDataComponent extends BaseComponent {

    @ElementInfo(name = "Email", findBy = @FindBy(name = "email"))
    private Input emailInput;

    public ProfileDataComponent(IParent parent) {
        super("Profile data", parent, By.cssSelector("div.profile-content"));
    }

    public String getEmail() {
        return emailInput.getText();
    }

    public boolean isEmailNotEqual(String email, Duration timeout) {
        return emailInput.waitForTextNotEquals(email, timeout);
    }
}
