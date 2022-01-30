package com.intpfy.gui.components.emi.event_creation;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class SecurityGroupRolesComponent extends BaseComponent {

    @ElementInfo(name = "Audience", findBy = @FindBy(xpath = ".//label[contains(@class, 'btn') and text() = 'Audience']"))
    private Button audienceButton;

    @ElementInfo(name = "Interpreter", findBy = @FindBy(xpath = ".//label[contains(@class, 'btn') and text() = 'Interpreter']"))
    private Button interpreterButton;

    @ElementInfo(name = "Speaker", findBy = @FindBy(xpath = ".//label[contains(@class, 'btn') and text() = 'Speaker']"))
    private Button speakerButton;

    @ElementInfo(name = "Moderator", findBy = @FindBy(xpath = ".//label[contains(@class, 'btn') and text() = 'Moderator']"))
    private Button moderatorButton;

    public SecurityGroupRolesComponent(IParent parent) {
        super("Security group roles", parent, By.cssSelector("div.security-groups-roles"));
    }

    public void selectAudience() {
        clickIfNotActive(audienceButton);
    }

    public void selectInterpreter() {
        clickIfNotActive(interpreterButton);
    }

    public void selectSpeaker() {
        clickIfNotActive(speakerButton);
    }

    public void selectModerator() {
        clickIfNotActive(moderatorButton);
    }

    private void clickIfNotActive(Button button) {
        if (!isActive(button)) {
            button.click();
        }
    }

    private boolean isActive(Button button) {
        return button.waitCssClassContains("active", Duration.ZERO);
    }
}