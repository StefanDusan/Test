package com.intpfy.gui.dialogs.moderator;

import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class SlowDownDW extends BaseComponent {

    @ElementInfo(name = "Message", findBy = @FindBy(css = "div._message"))
    private Element messageElement;

    @ElementInfo(name = "Language", findBy = @FindBy(css = "div._channel"))
    private Element languageElement;

    @ElementInfo(name = "Initials", findBy = @FindBy(css = "div._initials"))
    private Element initialsElement;

    public SlowDownDW(IParent parent) {
        super("'Slow down' dialog window", parent, By.cssSelector("div.top-bar-notification"));
    }

    public String getMessage(){
        return messageElement.getText();
    }

    public Language getLanguage() {
        String language = languageElement.getText().split(":")[1].trim();
        return Language.getLanguage(language);
    }

    public String getInitials() {
        return initialsElement.getText();
    }
}
