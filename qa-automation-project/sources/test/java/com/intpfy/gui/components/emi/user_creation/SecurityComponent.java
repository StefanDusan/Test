package com.intpfy.gui.components.emi.user_creation;

import com.intpfy.gui.components.emi.common.AllowlistComponent;
import com.intpfy.gui.dialogs.emi.AllowlistDW;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class SecurityComponent extends BaseComponent {

    @ElementInfo(name = "Indicate Security Group", findBy = @FindBy(name = "samlRequiredGroup"))
    private Input securityGroupInput;

    private final AllowlistComponent allowlist;

    @ElementInfo(name = "Identifier", findBy = @FindBy(name = "samlIdProviderCode"))
    private Input identifierInput;

    public SecurityComponent(IParent parent) {
        super("Security", parent, By.xpath(".//label[text() = 'Indicate Security Group']/ancestor::div[./hr]"));
        allowlist = new AllowlistComponent(this);
    }

    public boolean isOfActiveDirectoryType(Duration timeout) {
        return identifierInput.visible(timeout);
    }

    public boolean isOfKeycloakType(Duration timeout) {
        return identifierInput.notVisible(timeout);
    }

    public void setSecurityGroup(String group) {
        securityGroupInput.setText(group);
    }

    public boolean isSecurityGroupEqual(String group, Duration timeout) {
        return securityGroupInput.waitForTextEquals(group, timeout);
    }

    public boolean isSecurityGroupEmpty(Duration timeout) {
        return securityGroupInput.waitForTextEmpty(timeout);
    }

    public void removeSecurityGroup() {
        if (!isSecurityGroupEmpty(Duration.ZERO)) {
            securityGroupInput.clear();
        }
    }

    public AllowlistDW createAllowlist() {
        return allowlist.create();
    }

    public boolean isAllowlistCreated(Duration timeout) {
        return allowlist.isCreated(timeout);
    }

    public boolean isAllowlistNotCreated(Duration timeout) {
        return allowlist.isNotCreated(timeout);
    }

    public void removeAllowlist() {
        allowlist.remove();
    }

    public void setIdentifier(String identifier) {
        identifierInput.setText(identifier);
    }

    public boolean isIdentifierEqual(String identifier, Duration timeout) {
        return identifierInput.waitForTextEquals(identifier, timeout);
    }
}
