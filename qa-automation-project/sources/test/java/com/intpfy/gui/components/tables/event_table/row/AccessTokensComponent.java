package com.intpfy.gui.components.tables.event_table.row;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class AccessTokensComponent extends BaseComponent {

    @ElementInfo(name = "Audience token", findBy =
    @FindBy(xpath = ".//div[contains(@class, 'token-line') and not (contains(text(), '-'))]"))
    private Element audienceTokenElement;

    @ElementInfo(name = "I-token", findBy =
    @FindBy(xpath = ".//div[contains(@class, 'token-line') and contains(text(), 'I-')]"))
    private Element iTokenElement;

    @ElementInfo(name = "S-token", findBy =
    @FindBy(xpath = ".//div[contains(@class, 'token-line') and contains(text(), 'S-')]"))
    private Element sTokenElement;

    @ElementInfo(name = "M-token", findBy =
    @FindBy(xpath = ".//div[contains(@class, 'token-line') and contains(text(), 'M-')]"))
    private Element mTokenElement;

    public AccessTokensComponent(IParent parent) {
        super("Access tokens", parent, By.cssSelector("td[title=\"'Access tokens'\"]"));
    }

    public String getAudienceToken() {
        return audienceTokenElement.getText();
    }

    public String getInterpreterToken() {
        return iTokenElement.getText();
    }

    public String getSpeakerToken() {
        return sTokenElement.getText();
    }

    public String getModeratorToken() {
        return mTokenElement.getText();
    }
}