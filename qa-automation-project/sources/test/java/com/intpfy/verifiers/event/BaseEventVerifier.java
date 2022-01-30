package com.intpfy.verifiers.event;

import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.dialogs.common.MiniEventChatDW;
import com.intpfy.gui.dialogs.common.MiniPartnerChatDW;
import com.intpfy.gui.pages.event.BaseEventPage;
import com.intpfy.model.event.Role;
import com.intpfy.util.ColorContainer;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.factories.WebFactoryHelper;
import com.intpfyqa.test.Verify;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.Set;

import static com.intpfy.verifiers.event.common.CommonVerificationMessages.*;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public abstract class BaseEventVerifier {

    private final BaseEventPage page;

    public BaseEventVerifier(BaseEventPage page) {
        this.page = page;
    }

    public void assertUsernameDisplayed(String username) {
        Verify.assertEquals(page.getUsername(), username, USERNAME_DISPLAYED);
    }

    public void assertVideoPanelVisible() {
        Verify.assertTrue(page.isVideoPanelVisible(), VIDEO_PANEL_DISPLAYED);
    }

    public void assertVideoPanelNotVisible() {
        Verify.assertTrue(page.isVideoPanelNotVisible(), VIDEO_PANEL_NOT_DISPLAYED);
    }

    public void verifyVideoContainerVisible(String streamerName) {
        String message = String.format(VIDEO_CONTAINER_VISIBLE, streamerName);
        Verify.verifyTrue(page.isVideoContainerVisible(streamerName, AJAX_TIMEOUT), message);
    }

    public void assertVideoContainerVisible(String streamerName) {
        String message = String.format(VIDEO_CONTAINER_VISIBLE, streamerName);
        Verify.assertTrue(page.isVideoContainerVisible(streamerName, AJAX_TIMEOUT), message);
    }

    public void verifyVideoContainerNotVisible(String streamerName) {
        String message = String.format(VIDEO_CONTAINER_NOT_VISIBLE, streamerName);
        Verify.verifyTrue(page.isVideoContainerNotVisible(streamerName, AJAX_TIMEOUT), message);
    }

    public void assertVideoContainerNotVisible(String streamerName) {
        String message = String.format(VIDEO_CONTAINER_NOT_VISIBLE, streamerName);
        Verify.assertTrue(page.isVideoContainerNotVisible(streamerName, AJAX_TIMEOUT), message);
    }

    public void assertScreenContainerVisible(String streamerName) {
        String message = String.format(SCREEN_CONTAINER_VISIBLE, streamerName);
        Verify.assertTrue(page.isScreenContainerVisible(streamerName, AJAX_TIMEOUT), message);
    }

    public void verifyPageGrayedOut() {
        Verify.verifyTrue(createReconnectionMask().visible(), PAGE_GRAYED_OUT);
    }

    public void verifyPageNotGrayedOut() {
        Verify.verifyTrue(createReconnectionMask().notVisible(), PAGE_NOT_GRAYED_OUT);
    }

    public void assertPageNotGrayedOut() {
        Verify.assertTrue(createReconnectionMask().notVisible(), PAGE_NOT_GRAYED_OUT);
    }

    public void assertActiveSpeakerSettingEnabled() {
        Verify.assertTrue(page.isActiveSpeakerEnabled(AJAX_TIMEOUT), ACTIVE_SPEAKER_SETTING_ENABLED);
    }

    public void assertAllSpeakersSettingEnabled() {
        Verify.assertTrue(page.isAllSpeakersEnabled(AJAX_TIMEOUT), ALL_SPEAKERS_SETTING_ENABLED);
    }

    public void assertVideoContainerActive(String streamerName) {
        String message = String.format(VIDEO_CONTAINER_ACTIVE_FOR_STREAMER, streamerName);
        Verify.assertTrue(page.isVideoContainerActive(streamerName, AJAX_TIMEOUT), message);
    }

    public void assertVideoContainerInactive(String streamerName) {
        String message = String.format(VIDEO_CONTAINER_INACTIVE_FOR_STREAMER, streamerName);
        Verify.assertTrue(page.isVideoContainerInactive(streamerName, AJAX_TIMEOUT), message);
    }

    public void assertEventChatEnabled() {
        Verify.assertTrue(page.isEventChatEnabled(AJAX_TIMEOUT), EVENT_CHAT_ENABLED);
    }

    public void verifyEventChatEnabled() {
        Verify.verifyTrue(page.isEventChatEnabled(AJAX_TIMEOUT), EVENT_CHAT_ENABLED);
    }

    public void assertEventChatDisabled() {
        Verify.assertTrue(page.isEventChatDisabled(AJAX_TIMEOUT), EVENT_CHAT_DISABLED);
    }

    public void verifyEventChatDisabled() {
        Verify.verifyTrue(page.isEventChatDisabled(AJAX_TIMEOUT), EVENT_CHAT_DISABLED);
    }

    public void assertUsernameColorGreenForMessage(String message) {
        String rgba = page.getUsernameColorForMessage(message);
        String verificationMessage = String.format(USERNAME_COLOR_GREEN_FOR_MESSAGE, message);
        Verify.assertEquals(ColorContainer.fromCssBackground(rgba), ColorContainer.GREEN, verificationMessage);
    }

    public void assertMessageExistsInEventChat(String message) {
        String verificationMessage = String.format(MESSAGE_EXISTS_IN_EVENT_CHAT, message);
        Verify.assertTrue(page.messageExistsInEventChat(message, AJAX_TIMEOUT), verificationMessage);
    }

    public void assertMessageNotExistsInEventChat(String message) {
        String verificationMessage = String.format(MESSAGE_NOT_EXISTS_IN_EVENT_CHAT, message);
        Verify.assertTrue(page.messageNotExistsInEventChat(message, AJAX_TIMEOUT), verificationMessage);
    }

    public void verifyEventChatIsOfAnnouncementsType() {
        Verify.verifyTrue(page.isEventChatOfAnnouncementsType(AJAX_TIMEOUT), EVENT_CHAT_IS_OF_ANNOUNCEMENTS_TYPE);
    }

    public void assertEventChatIsOfFilesType() {
        Verify.assertTrue(page.isEventChatOfFilesType(AJAX_TIMEOUT), EVENT_CHAT_IS_OF_FILES_TYPE);
    }

    public void assertFilePresent(String fileName) {
        String message = String.format(FILE_PRESENT, fileName);
        Verify.assertTrue(page.isFilePresent(fileName, AJAX_TIMEOUT), message);
    }

    public void assertFileNotPresent(String fileName) {
        String message = String.format(FILE_NOT_PRESENT, fileName);
        Verify.assertTrue(page.isFileNotPresent(fileName, AJAX_TIMEOUT), message);
    }

    public void assertFileRolesEqual(String fileName, Set<Role> roles) {
        String message = String.format(FILE_ROLES_EQUAL, fileName, roles);
        Verify.assertTrue(page.areFileRolesEqual(fileName, roles), message);
    }

    public void assertFileScanning(String fileName) {
        String message = String.format(FILE_SCANNING, fileName);
        Verify.assertTrue(page.isFileScanning(fileName, AJAX_TIMEOUT), message);
    }

    public void assertFileNotScanning(String fileName) {
        String message = String.format(FILE_NOT_SCANNING, fileName);
        Verify.assertTrue(page.isFileNotScanning(fileName, Duration.ofMinutes(1)), message);
    }

    public void assertFileAvailableForDownload(String fileName) {
        String message = String.format(FILE_AVAILABLE_FOR_DOWNLOAD, fileName);
        Verify.assertTrue(page.isFileAvailableForDownload(fileName, AJAX_TIMEOUT), message);
    }

    public void assertMessageExistsInPrivateChat(ChatDW privateChat, String message) {
        String verificationMessage = String.format(MESSAGE_EXISTS_IN_PRIVATE_CHAT, message);
        Verify.assertTrue(privateChat.messageExists(message, AJAX_TIMEOUT), verificationMessage);
    }

    public void assertMessageExistsInRemoteSupportHelpChat(ChatDW remoteSupportHelpChat, String message) {
        String verificationMessage = String.format(MESSAGE_EXISTS_IN_REMOTE_SUPPORT_HELP_CHAT, message);
        Verify.assertTrue(remoteSupportHelpChat.messageExists(message, AJAX_TIMEOUT), verificationMessage);
    }

    public void assertMessageExistsInEventChat(MiniEventChatDW eventChat, String message) {
        String verificationMessage = String.format(MESSAGE_EXISTS_IN_EVENT_CHAT, message);
        Verify.assertTrue(eventChat.messageExists(message, AJAX_TIMEOUT), verificationMessage);
    }

    public void assertMessageExistsInPartnerChat(MiniPartnerChatDW partnerChat, String message) {
        String verificationMessage = String.format(MESSAGE_EXISTS_IN_PARTNER_CHAT, message);
        Verify.assertTrue(partnerChat.messageExists(message, AJAX_TIMEOUT), verificationMessage);
    }

    private Element createReconnectionMask() {
        String name = "Reconnection mask";
        By locator = By.cssSelector("div.reconnection-mask");
        return WebFactoryHelper.getElementFactory().createElement(Element.class, name, page, locator);
    }
}
