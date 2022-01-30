package com.intpfy.gui.components.speaker;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class RequestFloorComponent extends BaseComponent {

    @ElementInfo(name = "Raise hand", findBy = @FindBy(css = "div.control__button"))
    private Button raiseHandButton;

    protected RequestFloorComponent(IParent parent) {
        super("Request floor", parent, By.xpath(".//div[contains(@class, 'control__button')]/parent::div"));
    }

    public void raiseHand() {
        clickRaiseHandButton();
    }

    public void lowerHand() {
        clickRaiseHandButton();
    }

    public boolean isHandRaised(Duration duration) {
        return raiseHandButton.waitCssClassContains("_hand-up", duration);
    }

    public boolean isHandDown(Duration duration) {
        return raiseHandButton.waitCssClassContains("_hand-down", duration);
    }

    private void clickRaiseHandButton() {
        raiseHandButton.click();
    }
}
