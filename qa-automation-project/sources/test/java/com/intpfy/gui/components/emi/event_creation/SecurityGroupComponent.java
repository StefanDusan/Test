package com.intpfy.gui.components.emi.event_creation;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class SecurityGroupComponent extends BaseComponent {

    @ElementInfo(name = "Input", findBy = @FindBy(tagName = "input"))
    private Input input;

    @ElementInfo(name = "Delete", findBy = @FindBy(css = "a.multiple-input-remove"))
    private Button deleteButton;

    public SecurityGroupComponent(IParent parent) {
        super("Security group", parent, By.xpath("(.//div[@class = 'form-inline'])[last()]"));
    }

    public void add(String securityGroup) {
        input.setText(securityGroup);
    }
}
