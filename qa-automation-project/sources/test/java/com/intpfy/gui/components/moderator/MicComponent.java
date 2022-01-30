package com.intpfy.gui.components.moderator;

import com.intpfy.gui.dialogs.moderator.MicrophoneSettingsDW;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.Locale;

public class MicComponent extends BaseComponent {

    @ElementInfo(name = "Mic status", findBy = @FindBy(css = "li._mics"))
    private Element statusElement;

    @ElementInfo(name = "Mic", findBy = @FindBy(css = "li._mic-global"))
    private Button micButton;

    public MicComponent(IParent parent) {
        super("Mic", parent, By.cssSelector("ul.header__options-menu"));
    }

    public boolean isOn() {
        return statusElement.waitCssClassContains("_on", WebSettings.AJAX_TIMEOUT) &&
                statusElement.waitForTextContains("mic on", WebSettings.AJAX_TIMEOUT);
    }

    public boolean isOff() {
        return statusElement.waitCssClassNotContains("_on", WebSettings.AJAX_TIMEOUT) &&
                statusElement.waitForTextContains("mic off", WebSettings.AJAX_TIMEOUT);
    }

    public Language getLanguage() {
        String languageName = statusElement.getText().toLowerCase(Locale.ROOT).replaceAll("mic on: ", "");
        return Language.getLanguage(languageName);
    }

    public MicrophoneSettingsDW openSettings() {
        micButton.click();
        return new MicrophoneSettingsDW(getPage());
    }
}