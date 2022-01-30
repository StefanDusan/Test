package com.intpfy.gui.components.common;

import com.intpfy.gui.components.moderator.MicComponent;
import com.intpfy.gui.components.moderator.TimerComponent;
import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.gui.dialogs.moderator.CountdownTimerDW;
import com.intpfy.gui.dialogs.moderator.MicrophoneSettingsDW;
import com.intpfy.gui.dialogs.moderator.RecordingSettingsDW;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class HeaderComponent extends BaseComponent {

    @ElementInfo(name = "Recording settings", findBy = @FindBy(css = "li._recording-btn"))
    private Button recordingSettingsButton;

    private final MicComponent mic;
    private final FullscreenComponent fullscreen;

    @ElementInfo(name = "Restart all lines", findBy = @FindBy(css = "li._restart"))
    private Button restartAllLinesButton;

    @ElementInfo(name = "Language settings", findBy = @FindBy(css = "li._language"))
    private Button languageSettingsButton;

    @ElementInfo(name = "Settings", findBy = @FindBy(css = "li._settings"))
    private Button settingsButton;

    private final TimerComponent timer;

    @ElementInfo(name = "Username", findBy = @FindBy(css = "li._username > div.options-menu__user"))
    private Element usernameElement;

    public HeaderComponent(IParent parent) {
        super("Header", parent, By.className("header"));
        mic = new MicComponent(this);
        fullscreen = new FullscreenComponent(this);
        timer = new TimerComponent(this);
    }

    public RecordingSettingsDW openRecordingSettings() {
        recordingSettingsButton.click();
        return new RecordingSettingsDW(getPage());
    }

    public boolean isMicOn() {
        return mic.isOn();
    }

    public boolean isMicOff() {
        return mic.isOff();
    }

    public Language getMicLanguage() {
        return mic.getLanguage();
    }

    public MicrophoneSettingsDW openMicSettings() {
        return mic.openSettings();
    }

    public void enableFullscreenMode() {
        fullscreen.enable();
    }

    public void disableFullscreenMode() {
        fullscreen.disable();
    }

    public boolean isFullscreenModeEnabled() {
        return fullscreen.isEnabled();
    }

    public boolean isFullscreenDisabled() {
        return fullscreen.isDisabled();
    }

    public void restartAllLines() {
        restartAllLinesButton.click();
    }

    public boolean isRalButtonActive(Duration timeout) {
        return restartAllLinesButton.enabled(timeout);
    }

    public boolean isRalButtonInactive(Duration timeout) {
        return restartAllLinesButton.disabled(timeout);
    }

    public LanguageSettingsDW openLanguageSettings() {
        languageSettingsButton.click();
        return new LanguageSettingsDW(getPage());
    }

    public CountdownTimerDW openCountdownTimerDW() {
        return timer.openCountdownTimerDW();
    }

    public boolean isTimerHoursValueEqual(int value, Duration timeout) {
        return timer.isHoursValueEqual(value, timeout);
    }

    public boolean isTimerMinutesValueEqual(int value, Duration timeout) {
        return timer.isMinutesValueEqual(value, timeout);
    }

    public int getTimerSeconds() {
        return timer.getSeconds();
    }

    public String getUsername() {
        return usernameElement.getText();
    }
}
