package com.intpfy.gui.components.emi.dashboard;

import com.intpfy.gui.complex_elements.Dropdown;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class KeycloakOrganizationsComponent extends BaseComponent {

    @ElementInfo(name = "Dropdown", findBy = @FindBy(css = "div"))
    private Dropdown dropdown;

    public KeycloakOrganizationsComponent(IParent parent) {
        super("Keycloak Organizations", parent, By.xpath(".//div[./label[contains(text(),'User organizations')]]"));
    }

    public int getCount() {
        return dropdown.getOptionsCount();
    }

    public boolean isSelected(String organization, Duration timeout) {
        return dropdown.isSelected(organization, timeout);
    }

    public void select(String organization) {
        dropdown.select(organization);
    }
}
