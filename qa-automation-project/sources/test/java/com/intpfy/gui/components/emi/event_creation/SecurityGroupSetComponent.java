package com.intpfy.gui.components.emi.event_creation;

import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfy.gui.components.emi.common.AllowlistComponent;
import com.intpfy.gui.dialogs.emi.AllowlistDW;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class SecurityGroupSetComponent extends BaseComponent {

    private final SecurityGroupRolesComponent roles;

    @ElementInfo(name = "No security groups complex checkbox", findBy = @FindBy(xpath = "//div[./input[@id='emptySet0']]"))
    private ComplexCheckbox enableNoSecurityGroupsCheckbox;

    @ElementInfo(name = "+ Add Security Group", findBy = @FindBy(xpath = ".//button[contains(text(), '+ Add Security Group')]"))
    private Button addSecurityGroupButton;

    private final AllowlistComponent allowlist;

    public SecurityGroupSetComponent(IParent parent, int index) {
        super("Security group", parent, By.xpath(String.format("(.//h4[text() = 'Security Group Set']/parent::div)[%s]", index)));
        roles = new SecurityGroupRolesComponent(this);
        allowlist = new AllowlistComponent(this);
    }

    public void applyToAudience() {
        roles.selectAudience();
    }

    public void applyToInterpreter() {
        roles.selectInterpreter();
    }

    public void applyToSpeaker() {
        roles.selectSpeaker();
    }

    public void applyToModerator() {
        roles.selectModerator();
    }

    public void disableSecurityGroups() {
        enableNoSecurityGroupsCheckbox.select();
    }

    public void addSecurityGroup(String securityGroup) {

        addSecurityGroupButton.click();

        SecurityGroupComponent securityGroupComponent = createLastSecurityGroupComponent();
        securityGroupComponent.visible();

        securityGroupComponent.add(securityGroup);
    }

    public AllowlistDW createAllowlist() {
        return allowlist.create();
    }

    private SecurityGroupComponent createLastSecurityGroupComponent() {
        return new SecurityGroupComponent(this);
    }
}
