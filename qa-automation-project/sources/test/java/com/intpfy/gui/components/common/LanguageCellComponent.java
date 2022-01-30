package com.intpfy.gui.components.common;

import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class LanguageCellComponent extends BaseComponent {

    @ElementInfo(name = "Language", findBy = @FindBy(tagName = "span"))
    private Button languageButton;

    private final VolumeBarComponent volumeBar;

    public LanguageCellComponent(String name, IParent parent, ElementFromListMatcher matcher) {
        super(name, parent, By.cssSelector("div.stream-session__cell"), matcher);
        volumeBar = new VolumeBarComponent(this);
    }

    public LanguageCellComponent(IParent parent) {
        super("Language cell", parent, By.cssSelector("div.stream-session__cell"));
        volumeBar = new VolumeBarComponent(this);
    }

    public void click() {
        languageButton.click();
    }

    public void select() {
        if (isUnselected(Duration.ZERO)) {
            click();
            isSelected(AJAX_TIMEOUT);
        }
    }

    public boolean isSelected(Language language, Duration timeout) {
        return languageButton.waitForTextEquals(language.toString(), timeout) && isSelected(timeout);
    }

    public boolean isNoLanguageSelected(Duration timeout) {
        return languageButton.waitForTextEquals("select", timeout);
    }

    public boolean isSelected(Duration timeout) {
        return getComponentElement().waitCssClassNotContains("_unselected", timeout) && volumeBar.visible(timeout);
    }

    public boolean isUnselected(Duration timeout) {
        return getComponentElement().waitCssClassContains("_unselected", timeout) && volumeBar.notVisible(timeout);
    }

    public boolean isVolumeLevelChanging(Duration timeout) {
        return volumeBar.isChanging(timeout);
    }

    public boolean isVolumeLevelNotChanging(Duration timeout) {
        return volumeBar.isNotChanging(timeout);
    }
}
