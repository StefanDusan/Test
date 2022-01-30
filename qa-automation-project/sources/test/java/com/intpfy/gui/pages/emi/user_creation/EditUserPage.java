package com.intpfy.gui.pages.emi.user_creation;

import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfy.gui.components.emi.user_creation.SecurityComponent;
import com.intpfy.gui.dialogs.emi.AllowlistDW;
import com.intpfy.gui.pages.emi.BaseEmiPage;
import com.intpfy.gui.pages.emi.UsersPage;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class EditUserPage extends BaseEmiPage {

    @ElementInfo(name = "Add event page title", findBy = @FindBy(xpath = "//h1[text() = 'Edit User']"))
    private Element pageTitle;

    @ElementInfo(name = "Domain name", findBy = @FindBy(name = "domain"))
    private Input domainNameInput;

    @ElementInfo(name = "Enable Active Directory authentication complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='samlEnable']]"))
    private ComplexCheckbox enableActiveDirectoryAuthenticationCheckbox;

    @ElementInfo(name = "Enable Keycloak complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='keycloakEnabled']]"))
    private ComplexCheckbox enableKeycloakCheckbox;

    private final SecurityComponent security;

    @ElementInfo(name = "Save", findBy = @FindBy(css = "button[type='submit']"))
    protected Button saveButton;

    public EditUserPage(IPageContext pageContext) {
        super("Edit user page", pageContext);
        security = new SecurityComponent(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return pageTitle.visible(timeout);
    }

    public String getDomainName() {
        return domainNameInput.getText();
    }

    public void setDomainName(String name) {
        info(String.format("Set Domain name '%s'.", name));
        domainNameInput.setText(name);
    }

    public boolean isDomainNameEqual(String domainName, Duration timeout) {
        return domainNameInput.waitForTextEquals(domainName, timeout);
    }

    public void enableActiveDirectoryAuthentication() {
        info("Enable Active Directory authentication.");
        enableActiveDirectoryAuthenticationCheckbox.select();
    }

    public void disableActiveDirectoryAuthentication() {
        info("Disable Active Directory authentication.");
        enableActiveDirectoryAuthenticationCheckbox.unselect();
    }

    public boolean isActiveDirectoryAuthenticationEnabled(Duration timeout) {
        return enableActiveDirectoryAuthenticationCheckbox.waitIsSelected(timeout) &&
                security.visible(timeout) &&
                security.isOfActiveDirectoryType(timeout);

    }

    public boolean isActiveDirectoryAuthenticationDisabled(Duration timeout) {
        return enableActiveDirectoryAuthenticationCheckbox.waitIsNotSelected(timeout) &&
                security.notVisible(Duration.ZERO) ||
                security.isOfKeycloakType(timeout);
    }

    public void enableKeycloak() {
        info("Enable Keycloak.");
        enableKeycloakCheckbox.select();
    }

    public void disableKeycloak() {
        info("Disable Keycloak.");
        enableKeycloakCheckbox.unselect();
    }

    public boolean isKeycloakEnabled(Duration timeout) {
        return enableKeycloakCheckbox.waitIsSelected(timeout) &&
                security.visible(timeout) &&
                security.isOfKeycloakType(timeout);
    }

    public boolean isKeycloakDisabled(Duration timeout) {
        return enableKeycloakCheckbox.waitIsNotSelected(timeout) &&
                security.notVisible(Duration.ZERO) ||
                security.isOfActiveDirectoryType(timeout);
    }

    public void setSecurityGroup(String group) {
        info(String.format("Set Security group '%s'.", group));
        security.setSecurityGroup(group);
    }

    public boolean isSecurityGroupEqual(String group, Duration timeout) {
        return security.isSecurityGroupEqual(group, timeout);
    }

    public boolean isSecurityGroupEmpty(Duration timeout) {
        return security.isSecurityGroupEmpty(timeout);
    }

    public void removeSecurityGroup() {
        info("Remove Security group.");
        security.removeSecurityGroup();
    }

    public AllowlistDW createAllowlist() {
        info("Create Allowlist.");
        return security.createAllowlist();
    }

    public void removeAllowlist() {
        info("Remove Allowlist.");
        security.removeAllowlist();
    }

    public boolean isAllowlistCreated(Duration timeout) {
        return security.isAllowlistCreated(timeout);
    }

    public boolean isAllowlistNotCreated(Duration timeout) {
        return security.isAllowlistNotCreated(timeout);
    }

    public void setActiveDirectoryIdentifier(String identifier) {
        info(String.format("Set Active Directory identifier '%s'.", identifier));
        security.setIdentifier(identifier);
    }

    public boolean isActiveDirectoryIdentifierEqual(String identifier, Duration timeout) {
        return security.isIdentifierEqual(identifier, timeout);
    }

    public UsersPage save() {
        info("Save.");
        saveButton.click();
        return new UsersPage(getPageContext());
    }
}
