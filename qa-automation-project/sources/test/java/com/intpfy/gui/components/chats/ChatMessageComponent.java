package com.intpfy.gui.components.chats;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class ChatMessageComponent extends BaseComponent {

    @ElementInfo(name = "Username", findBy = @FindBy(css = "div.username"))
    private Element usernameElement;

    public ChatMessageComponent(IParent parent, String message) {
        super(String.format("Chat message '%s'.", message), parent, By.cssSelector("div.message"));
    }

    public String getUsernameColor() {
        return usernameElement.getCssValue("color");
    }

    public boolean isHidden(Duration timeout) {
        return getComponentElement().waitCssClassContains("_hidden", timeout);
    }
}
