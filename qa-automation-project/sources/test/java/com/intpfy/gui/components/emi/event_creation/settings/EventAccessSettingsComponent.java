package com.intpfy.gui.components.emi.event_creation.settings;

import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfy.gui.components.emi.event_creation.SecurityGroupSetComponent;
import com.intpfy.gui.dialogs.emi.AllowlistDW;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class EventAccessSettingsComponent extends BaseComponent {

    @ElementInfo(name = "Active Directory Authentication complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='samlAuthenticationRequired']]"))
    private ComplexCheckbox enableActiveDirectoryAuthenticationCheckbox;

    @ElementInfo(name = "Active Keycloak Authentication complex checkbox", findBy =
    @FindBy(xpath = ".//div[./input[@id='keycloakAuthentication']]"))
    private ComplexCheckbox enableKeycloakAuthenticationCheckbox;

    private final SecurityGroupSetComponent securityGroupSet;

    @ElementInfo(name = "+ Add set", findBy = @FindBy(xpath = ".//button[text() = '+ Add Set']"))
    private Button addSetButton;

    public EventAccessSettingsComponent(IParent parent) {
        // './self::*' locator is used as parent component locator.
        super("Event access settings", parent, By.xpath("./self::*"));
        securityGroupSet = new SecurityGroupSetComponent(this, 1);
    }

    public void enableActiveDirectoryAuthentication() {
        enableActiveDirectoryAuthenticationCheckbox.select();
    }

    public void enableKeycloakAuthentication() {
        enableKeycloakAuthenticationCheckbox.select();
    }

    public void applySecurityGroupSetToAudience(int securityGroupSetIndex) {
        createSecurityGroupSetComponent(securityGroupSetIndex).applyToAudience();
    }

    public void applySecurityGroupSetToInterpreter(int securityGroupSetIndex) {
        createSecurityGroupSetComponent(securityGroupSetIndex).applyToInterpreter();
    }

    public void applySecurityGroupSetToSpeaker(int securityGroupSetIndex) {
        createSecurityGroupSetComponent(securityGroupSetIndex).applyToSpeaker();
    }

    public void applySecurityGroupSetToModerator(int securityGroupSetIndex) {
        createSecurityGroupSetComponent(securityGroupSetIndex).applyToModerator();
    }

    public void disableSecurityGroups(int securityGroupSetIndex) {
        createSecurityGroupSetComponent(securityGroupSetIndex).disableSecurityGroups();
    }

    public void addSecurityGroup(int securityGroupSetIndex, String securityGroup) {
        createSecurityGroupSetComponent(securityGroupSetIndex).addSecurityGroup(securityGroup);
    }

    public AllowlistDW createAllowlist(int securityGroupSetIndex) {
        return createSecurityGroupSetComponent(securityGroupSetIndex).createAllowlist();
    }

    public void addSecurityGroupSet() {
        addSetButton.click();
    }

    private SecurityGroupSetComponent createSecurityGroupSetComponent(int index) {

        checkSecurityGroupSetComponentIndex(index);

        if (index == 1) {
            return securityGroupSet;
        } else {
            return new SecurityGroupSetComponent(this, index);
        }
    }

    private void checkSecurityGroupSetComponentIndex(int index) {
        if (index <= 0) {
            throw new IllegalArgumentException("Index must be greater than 0.");
        }
    }
}
