package com.intpfy.gui.components.interpreter;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class HandoverComponent extends BaseComponent {

    @ElementInfo(name = "Header", findBy = @FindBy(css = "div.handover__header"))
    private Element headerElement;

    @ElementInfo(name = "Now", findBy = @FindBy(xpath = ".//button[contains(text(), 'NOW')]"))
    private Button nowButton;

    @ElementInfo(name = "Later", findBy = @FindBy(xpath = ".//button[contains(text(), 'LATER')]"))
    private Button laterButton;

    @ElementInfo(name = "Waiting for partner response", findBy = @FindBy(css = "div.handover-response._waiting"))
    private Element waitingForPartnerResponseElement;

    public HandoverComponent(IParent parent) {
        super("Handover", parent, By.cssSelector("div.handover"));
    }

    public boolean isAvailable() {
        return headerElement.waitCssClassContains("_unmuted", WebSettings.AJAX_TIMEOUT);
    }

    public boolean isUnavailable() {
        return headerElement.waitCssClassNotContains("_unmuted", WebSettings.AJAX_TIMEOUT);
    }

    public void requestNow() {
        nowButton.click();
        isWaitingForPartnerResponse();
    }

    public boolean isWaitingForPartnerResponse() {
        return waitingForPartnerResponseElement.visible();
    }
}