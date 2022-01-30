package com.intpfy.gui.components.emi.event_creation;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class HostPasswordComponent extends BaseComponent {

    @ElementInfo(name = "Generate", findBy = @FindBy(xpath = ".//button[text()='Generate Password']"))
    private Button generateButton;

    @ElementInfo(name = "Password", findBy = @FindBy(name = "chairpersonToken"))
    private Input passwordInput;

    public HostPasswordComponent(IParent parent) {
        super("Host password", parent, By.xpath(".//label[text() = 'Meeting Host password']/parent::div"));
    }

    public void generate() {
        generateButton.click();
    }

    public String get() {
        return passwordInput.getText();
    }

    public void set(String password) {
        passwordInput.setText(password);
    }

    public boolean isEqual(String password, Duration timeout) {
        return passwordInput.waitForTextEquals(password, timeout);
    }
}
