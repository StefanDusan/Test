package com.intpfy.gui.pages.event;

import com.intpfy.gui.components.chats.EventChatComponent;
import com.intpfy.gui.components.common.HeaderComponent;
import com.intpfy.gui.components.common.VideoPanelComponent;
import com.intpfy.gui.components.common.VideoSwitchPanelComponent;
import com.intpfy.gui.dialogs.logout.LogOutFromEventDW;
import com.intpfy.gui.pages.BaseAuthorizedPage;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.model.Key;
import com.intpfy.model.Shortcut;
import com.intpfy.model.event.Role;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.function.Predicate;

import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public abstract class BaseEventPage extends BaseAuthorizedPage {

    @ElementInfo(name = "Body", findBy = @FindBy(tagName = "body"))
    protected Element bodyElement;

    @ElementInfo(name = "Role", findBy = @FindBy(xpath = "//span[contains(@class, 'role')]"))
    protected Element roleElement;

    @ElementInfo(name = "Log out", findBy = @FindBy(css = "li._logout"))
    protected Button logOutButton;

    protected final HeaderComponent header;
    protected final VideoSwitchPanelComponent videoSwitchPanel;
    protected final VideoPanelComponent videoPanel;

    protected BaseEventPage(String name, IPageContext pageContext) {
        super(name, pageContext);
        header = new HeaderComponent(this);
        videoSwitchPanel = new VideoSwitchPanelComponent(this);
        videoPanel = new VideoPanelComponent(this);
    }

    @Override
    public LoginPage logOut() {

        info("Log out.");

        logOutButton.click();

        LogOutFromEventDW modal = new LogOutFromEventDW(getPage());
        modal.assertIsOpened();

        modal.confirm();
        modal.assertNotVisible();

        return new LoginPage(getPageContext());
    }

    public abstract EventChatComponent getEventChat();

    public boolean isRalButtonActive(Duration timeout) {
        return header.isRalButtonActive(timeout);
    }

    public boolean isRalButtonInactive(Duration timeout) {
        return header.isRalButtonInactive(timeout);
    }

    public void enableFullscreenMode() {
        info("Enable Fullscreen mode.");
        header.enableFullscreenMode();
    }

    public boolean isFullscreenModeEnabled() {
        return header.isFullscreenModeEnabled();
    }

    public boolean isFullscreenModeDisabled() {
        return header.isFullscreenDisabled();
    }

    public String getUsername() {
        return header.getUsername();
    }

    public boolean isVideoPanelVisible() {
        return videoPanel.visible();
    }

    public boolean isVideoPanelNotVisible() {
        return videoPanel.notVisible();
    }

    public boolean isVideoContainerVisible(String streamerName, Duration timeout) {
        return videoPanel.isVideoContainerVisible(streamerName, timeout);
    }

    public boolean isVideoContainerNotVisible(String streamerName, Duration timeout) {
        return videoPanel.isVideoContainerNotVisible(streamerName, timeout);
    }

    public boolean isScreenContainerVisible(String streamerName, Duration timeout) {
        return videoPanel.isScreenContainerVisible(streamerName, timeout);
    }

    public boolean isScreenContainerNotVisible(String streamerName, Duration timeout) {
        return videoPanel.isScreenContainerNotVisible(streamerName, timeout);
    }

    public boolean isVideoContainerActive(String streamerName, Duration timeout) {
        return videoPanel.isVideoContainerActive(streamerName, timeout);
    }

    public boolean isVideoContainerInactive(String streamerName, Duration timeout) {
        return videoPanel.isVideoContainerInactive(streamerName, timeout);
    }

    public void enableActiveSpeakerSettingByShortcut() {
        info("Enable 'Active Speaker' setting by shortcut.");
        if (isAllSpeakersEnabled(AJAX_TIMEOUT)) {
            switchActiveSpeakerAllSpeakersSettingByShortcut();
        }
    }

    public void enableAllSpeakersSettingByShortcut() {
        info("Enable 'All Speakers' setting by shortcut.");
        if (isActiveSpeakerEnabled(AJAX_TIMEOUT)) {
            switchActiveSpeakerAllSpeakersSettingByShortcut();
        }
    }

    public boolean isActiveSpeakerEnabled(Duration timeout) {
        return videoPanel.isActiveSpeakerEnabled(timeout);
    }

    public boolean isAllSpeakersEnabled(Duration timeout) {
        return videoPanel.isAllSpeakersEnabled(timeout);
    }

    public boolean isEventChatEnabled(Duration timeout) {
        return getEventChat().isEnabled(timeout);
    }

    public boolean isEventChatDisabled(Duration timeout) {
        return getEventChat().isDisabled(timeout);
    }

    public Boolean isEventChatOfAnnouncementsType(Duration timeout) {
        return getEventChat().isOfAnnouncementsType(timeout);
    }

    public boolean isEventChatOfFilesType(Duration timeout) {
        return getEventChat().isOfFilesType(timeout);
    }

    public void sendMessageToEventChat(String message) {
        info(String.format("Send message to Event chat '%s'.", message));
        getEventChat().sendMessage(message);
    }

    public boolean messageExistsInEventChat(String message, Duration timeout) {
        return getEventChat().messageExists(message, timeout);
    }

    public boolean messageNotExistsInEventChat(String message, Duration timeout) {
        return getEventChat().messageNotExists(message, timeout);
    }

    public String getUsernameColorForMessage(String message) {
        return getEventChat().getUsernameColor(message);
    }

    public void switchEventChatToFiles() {
        info("Switch Event chat to 'Files'.");
        getEventChat().switchToFiles();
    }

    public boolean isFilePresent(String fileName, Duration timeout) {
        return getEventChat().isFilePresent(fileName, timeout);
    }

    public boolean isFileNotPresent(String fileName, Duration timeout) {
        return getEventChat().isFileNotPresent(fileName, timeout);
    }

    public boolean isFileScanning(String documentName, Duration timeout) {
        return getEventChat().isFileScanning(documentName, timeout);
    }

    public boolean isFileNotScanning(String documentName, Duration timeout) {
        return getEventChat().isFileNotScanning(documentName, timeout);
    }

    public boolean areFileRolesEqual(String documentName, Set<Role> roles) {
        return getEventChat().areRolesPresent(documentName, roles);
    }

    public boolean isFileAvailableForDownload(String fileName, Duration timeout) {
        return getEventChat().isFileAvailableForDownload(fileName, timeout);
    }

    protected void applyShortcut(Shortcut shortcut) {
        bodyElement.sendKeys(shortcut.keysChord());
    }

    protected void pressAndHoldKey(Key key, Duration holdDuration) {
        String script = createPressAndHoldKeyScript(key, holdDuration);
        executeScript(script);
    }

    private String createPressAndHoldKeyScript(Key key, Duration holdDuration) {
        return String.format(
                "const code = %s" +
                        "\n" +
                        "function pressButton() {\n" +
                        "    document.querySelector('body').dispatchEvent(new KeyboardEvent('keydown', {\n" +
                        "        keyCode: code\n" +
                        "    }));\n" +
                        "}\n" +
                        "\n" +
                        "function pressAndReleaseButton(duration) {\n" +
                        "    pressButton()\n" +
                        "    setTimeout(function releaseButton() {\n" +
                        "            document.querySelector('body').dispatchEvent(new KeyboardEvent('keyup', {\n" +
                        "                keyCode: code\n" +
                        "            }));\n" +
                        "        },\n" +
                        "        duration)\n" +
                        "}\n" +
                        "\n" +
                        "pressAndReleaseButton(%s)",

                key.getCode(), holdDuration.toMillis()
        );
    }

    protected Duration getMaxTimeToSetMaxOrMinVolumeLevel() {
        return Duration.ofSeconds(20);
    }

    protected void pressKeysHoldingAltTillConditionIsTrue(Keys keys, Predicate<Void> condition, Duration max) {
        Action actions = new Actions(getPageContext().getDriver())
                .keyDown(Keys.ALT)
                .sendKeys(keys)
                .keyUp(Keys.ALT)
                .build();
        LocalDateTime end = LocalDateTime.now().plus(max);
        while (condition.test(null) && LocalDateTime.now().isBefore(end)) {
            actions.perform();
        }
    }

    private void switchActiveSpeakerAllSpeakersSettingByShortcut() {
        applyShortcut(Shortcut.SwitchAllSpeakersActiveSpeakerSetting);
    }
}
