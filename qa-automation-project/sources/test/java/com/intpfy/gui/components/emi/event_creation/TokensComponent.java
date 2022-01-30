package com.intpfy.gui.components.emi.event_creation;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class TokensComponent extends BaseComponent {

    @ElementInfo(name = "Audience token", findBy = @FindBy(css = "#audienceToken, [name=audienceToken]"))
    private Input audienceTokenInput;

    @ElementInfo(name = "Interpreter token", findBy = @FindBy(css = "#interpreterToken, [name=interpreterToken]"))
    private Input interpreterTokenInput;

    @ElementInfo(name = "Speaker token", findBy = @FindBy(css = "#speakerToken, [name=floorToken]"))
    private Input speakerTokenInput;

    @ElementInfo(name = "Moderator token", findBy = @FindBy(css = "#moderatorToken, [name=moderatorToken]"))
    private Input moderatorTokenInput;

    public TokensComponent(IParent parent) {
        // './self::*' locator is used as parent component locator.
        super("Tokens", parent, By.xpath("./self::*"));
    }

    public String getAudienceToken() {
        return audienceTokenInput.getText();
    }

    public void setAudienceToken(String token) {
        audienceTokenInput.setText(token);
    }

    public String getInterpreterToken() {
        return interpreterTokenInput.getText();
    }

    public void setInterpreterToken(String token) {
        interpreterTokenInput.setText(token);
    }

    public String getSpeakerToken() {
        return speakerTokenInput.getText();
    }

    public void setSpeakerToken(String token) {
        speakerTokenInput.setText(token);
    }

    public String getModeratorToken() {
        return moderatorTokenInput.getText();
    }

    public void setModeratorToken(String token) {
        moderatorTokenInput.setText(token);
    }

    public boolean isAudienceTokenEqual(String token, Duration timeout) {
        return audienceTokenInput.waitForTextEquals(token, timeout);
    }

    public boolean isInterpreterTokenEqual(String token, Duration timeout) {
        return interpreterTokenInput.waitForTextEquals(token, timeout);
    }

    public boolean isSpeakerTokenEqual(String token, Duration timeout) {
        return speakerTokenInput.waitForTextEquals(token, timeout);
    }

    public boolean isModeratorTokenEqual(String token, Duration timeout) {
        return moderatorTokenInput.waitForTextEquals(token, timeout);
    }
}
