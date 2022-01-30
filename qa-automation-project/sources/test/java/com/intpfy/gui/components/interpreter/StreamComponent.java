package com.intpfy.gui.components.interpreter;

import com.intpfy.gui.components.common.LanguageCellComponent;
import com.intpfy.gui.components.common.VolumeControlComponent;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.util.matchers.ElementFromListMatcherByIndex;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;

import java.time.Duration;

public class StreamComponent extends BaseComponent {

    private final LanguageCellComponent firstLanguageCell;
    private final LanguageCellComponent secondLanguageCell;
    private final VolumeControlComponent volumeControl;

    public StreamComponent(String name, IParent parent, By locator) {
        super(name, parent, locator);
        firstLanguageCell = new LanguageCellComponent("First language cell", this, new ElementFromListMatcherByIndex(1));
        secondLanguageCell = new LanguageCellComponent("Second language cell", this, new ElementFromListMatcherByIndex(2));
        volumeControl = new VolumeControlComponent(this);
    }

    public void changeLanguageToFirst() {
        if (isSecondLanguageActive(Duration.ZERO)) {
            selectFirstLanguage();
            isFirstLanguageActive(WebSettings.AJAX_TIMEOUT);
        }
    }

    public void changeLanguageToSecond() {
        if (isFirstLanguageActive(Duration.ZERO)) {
            selectSecondLanguage();
            isSecondLanguageActive(WebSettings.AJAX_TIMEOUT);
        }
    }

    public void mute() {
        volumeControl.mute();
    }

    public void unmute() {
        volumeControl.unmute();
    }

    public boolean isMuted(Duration timeout) {
        return volumeControl.isMuted(timeout);
    }

    public boolean isUnmuted(Duration timeout) {
        return volumeControl.isUnmuted(timeout);
    }

    public boolean isVolumeLevelChanging(Duration timeout) {
        if (isFirstLanguageActive(Duration.ZERO)) {
            return isFirstLanguageVolumeLevelChanging(timeout);
        }
        return isSecondLanguageVolumeLevelChanging(timeout);
    }

    public boolean isVolumeLevelNotChanging(Duration timeout) {
        if (isFirstLanguageActive(Duration.ZERO)) {
            return isFirstLanguageVolumeLevelNotChanging(timeout);
        }
        return isSecondLanguageVolumeLevelNotChanging(timeout);
    }

    public void setMaxVolumeLevel() {
        volumeControl.setMaxVolumeLevel();
    }

    public void setMinVolumeLevel() {
        volumeControl.setMinVolumeLevel();
    }

    public boolean isVolumeLevelMax(Duration timeout) {
        return volumeControl.isVolumeLevelMax(timeout);
    }

    public boolean isVolumeLevelMin(Duration timeout) {
        return volumeControl.isVolumeLevelMin(timeout);
    }

    private void selectFirstLanguage() {
        firstLanguageCell.select();
    }

    private void selectSecondLanguage() {
        secondLanguageCell.select();
    }

    private boolean isFirstLanguageActive(Duration timeout) {
        return firstLanguageCell.isSelected(timeout);
    }

    private boolean isSecondLanguageActive(Duration timeout) {
        return secondLanguageCell.isSelected(timeout);
    }

    private boolean isFirstLanguageVolumeLevelChanging(Duration timeout) {
        return firstLanguageCell.isVolumeLevelChanging(timeout);
    }

    private boolean isSecondLanguageVolumeLevelChanging(Duration timeout) {
        return secondLanguageCell.isVolumeLevelChanging(timeout);
    }

    private boolean isFirstLanguageVolumeLevelNotChanging(Duration timeout) {
        return firstLanguageCell.isVolumeLevelNotChanging(timeout);
    }

    private boolean isSecondLanguageVolumeLevelNotChanging(Duration timeout) {
        return secondLanguageCell.isVolumeLevelNotChanging(timeout);
    }

    public boolean isShortcutsIndicatorDisplayed(Duration timeout) {
        return getComponentElement().waitCssClassContains("_shortcuts-indicator", timeout);
    }

    public boolean isShortcutsIndicatorNotDisplayed(Duration timeout) {
        return getComponentElement().waitCssClassNotContains("_shortcuts-indicator", timeout);
    }

    public boolean isLanguageActive(Language language, Duration timeout) {
        return isFirstLanguageActive(Duration.ZERO) ? firstLanguageCell.isSelected(language, timeout) : secondLanguageCell.isSelected(language, timeout);
    }
}
