package com.intpfy.gui.pages.event;

import com.intpfy.gui.components.audience.ConnectionComponent;
import com.intpfy.gui.components.chats.EventChatComponent;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.model.Language;
import com.intpfy.model.Shortcut;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class AudiencePage extends BaseEventPage {

    @ElementInfo(name = "Show / Hide video", findBy = @FindBy(css = "span.button"))
    private Element showHideVideoButton;

    @ElementInfo(name = "Show / Hide video stat", findBy = @FindBy(css = "span.button use"))
    private Button showHideVideoStatElement;

    private final ConnectionComponent connection;
    private final EventChatComponent eventChat;

    public AudiencePage(IPageContext pageContext) {
        super("Audience page", pageContext);
        connection = new ConnectionComponent(this);
        eventChat = new EventChatComponent(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return roleElement.waitForTextContains("audience", timeout);
    }

    @Override
    public EventChatComponent getEventChat() {
        return eventChat;
    }

    @Override
    public boolean isEventChatEnabled(Duration timeout) {
        return eventChat.visible(timeout) && super.isEventChatEnabled(timeout);
    }

    @Override
    public boolean isEventChatDisabled(Duration timeout) {
        return eventChat.notVisible(timeout);
    }

    public void selectSourceChannel() {
        selectLanguageChannel(Language.Source);
    }

    public void selectLanguageChannel(Language language) {
        info(String.format("Select language channel '%s'.", language));
        connection.selectLanguageChannel(language);
    }

    public boolean isLanguageChannelSelected(Language language, Duration timeout) {
        return connection.isLanguageChannelSelected(language, timeout);
    }

    public boolean isNoLanguageChannelSelected(Duration timeout) {
        return connection.isNoLanguageChannelSelected(timeout);
    }

    public void disconnect() {
        info("Disconnect.");
        connection.disconnect();
    }

    public void disconnectByShortcut() {
        info("Disconnect by shortcut.");
        if (isConnected()) {
            applyShortcut(Shortcut.ConnectDisconnect);
        }
    }

    public void connectByShortcut() {
        info("Connect by shortcut.");
        if (isDisconnected()) {
            applyShortcut(Shortcut.ConnectDisconnect);
        }
    }

    public boolean isConnected() {
        return connection.isConnected();
    }

    public boolean isDisconnected() {
        return connection.isDisconnected();
    }

    public void turnVideoOn() {
        info("Turn Video ON");
        if (isShowVideoToggleOff(Duration.ZERO)) {
            switchShowHideVideo();
        }
    }

    public void turnVideoOff() {
        info("Turn Video OFF.");
        if (isShowVideoToggleOn(Duration.ZERO)) {
            switchShowHideVideo();
        }
    }

    private void switchShowHideVideo() {
        showHideVideoButton.click();
    }

    public boolean isShowVideoToggleOn(Duration timeout) {
        return showHideVideoStatElement.waitProperty("xlink:href", "#svg-camera", timeout);
    }

    public boolean isShowVideoToggleOff(Duration timeout) {
        return showHideVideoStatElement.waitProperty("xlink:href", "#svg-camera-off", timeout);
    }

    public boolean isAutoVolumeAvailable(Duration timeout) {
        return connection.isAutoVolumeAvailable(timeout);
    }

    public boolean isAutoVolumeUnavailable(Duration timeout) {
        return connection.isAutoVolumeUnavailable(timeout);
    }

    public void enableAutoVolume() {
        info("Enable Auto-volume.");
        connection.enableAutoVolume();
    }

    public void disableAutoVolume() {
        info("Disable Auto-volume.");
        connection.disableAutoVolume();
    }

    public boolean isAutoVolumeEnabled(Duration timeout) {
        return connection.isAutoVolumeEnabled(timeout);
    }

    public boolean isAutoVolumeDisabled(Duration timeout) {
        return connection.isAutoVolumeDisabled(timeout);
    }

    public LoginPage logOutByShortcut() {
        info("Log out by shortcut.");
        applyShortcut(Shortcut.LogOut);
        return new LoginPage(getPageContext());
    }

    public void enableVideoOnlyFullscreenModeByShortcut() {
        info("Enable 'Video only' fullscreen mode by shortcut.");
        if (isVideoOnlyFullscreenModeDisabled(Duration.ZERO)) {
            switchVideoOnlyFullscreenModeOnOffByShortcut();
        }
    }

    public void disableVideoOnlyFullscreenModeByShortcut() {
        info("Disable 'Video only' fullscreen mode by shortcut.");
        if (isVideoOnlyFullscreenModeEnabled(Duration.ZERO)) {
            switchVideoOnlyFullscreenModeOnOffByShortcut();
        }
    }

    public void disableVideoOnlyFullscreenModeByEscapeShortcut() {
        info("Disable 'Video only' fullscreen mode by Escape shortcut.");
        if (isVideoOnlyFullscreenModeEnabled(Duration.ZERO)) {
            applyShortcut(Shortcut.Escape);
        }
    }

    public boolean isVideoOnlyFullscreenModeEnabled(Duration timeout) {
        return videoPanel.isFullscreenInterfaceEnabled(timeout) &&
                new FullscreenInterfaceSpeakerPage(getPageContext()).isNotOpened(timeout);
    }

    public boolean isVideoOnlyFullscreenModeDisabled(Duration timeout) {
        return videoPanel.isFullscreenInterfaceDisabled(timeout) &&
                new FullscreenInterfaceSpeakerPage(getPageContext()).isNotOpened(timeout);
    }

    private void switchVideoOnlyFullscreenModeOnOffByShortcut() {
        applyShortcut(Shortcut.SwitchVideoOnlyFullscreenModeOnOff);
    }
}
