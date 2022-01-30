package com.intpfy.gui.pages.event;

import com.intpfy.gui.components.chats.EventChatComponent;
import com.intpfy.gui.components.moderator.LanguageSessionComponent;
import com.intpfy.gui.components.moderator.SourceSessionComponent;
import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.dialogs.moderator.*;
import com.intpfy.model.Language;
import com.intpfy.model.event.Role;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.Set;

public class ModeratorPage extends BaseEventPage {

    @ElementInfo(name = "Help request", findBy = @FindBy(css = "button.moderator-feedback-btn"))
    private Button helpRequestButton;

    @ElementInfo(name = "Show 'Source' section", findBy = @FindBy(css = "button.moderator-toggle-btn"))
    private Button showSourceSectionButton;

    @ElementInfo(name = "Spinner", findBy = @FindBy(css = "div.loader-container > div.spinner"))
    private Element spinnerElement;

    private final SourceSessionComponent sourceSession;

    public ModeratorPage(IPageContext pageContext) {
        this("Moderator page", pageContext);
    }

    public ModeratorPage(String pageName, IPageContext pageContext) {
        super(pageName, pageContext);
        sourceSession = new SourceSessionComponent(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return roleElement.waitForTextContains("moderator", timeout);
    }

    public boolean isMicOn() {
        return header.isMicOn();
    }

    public boolean isMicOff() {
        return header.isMicOff();
    }

    public MicrophoneSettingsDW openMicSettings() {
        info("Open microphone settings.");
        return header.openMicSettings();
    }

    @Override
    public EventChatComponent getEventChat() {
        return sourceSession.getEventChat();
    }

    public boolean areFileRolesEqual(String documentName, Set<Role> roles) {
        return getEventChat().areRolesPresent(documentName, roles);
    }

    public ChatDW openRemoteSupportHelpChat() {
        info("Open 'Remote Support Help' dialog window.");
        helpRequestButton.visible();
        helpRequestButton.click();
        return new ChatDW(this);
    }

    public void sendMessageToPartnerChat(String message, Language chatLanguage) {
        info(String.format("Send message '%s' to Partner chat for language '%s'.", message, chatLanguage));
        LanguageSessionComponent sessionComponent = createLanguageSessionComponent(chatLanguage);
        sessionComponent.assertIsOpened();
        sessionComponent.sendMessageToChat(message);
    }

    public boolean messageExistsInPartnerChat(String message, Language chatLanguage, Duration timeout) {
        LanguageSessionComponent sessionComponent = createLanguageSessionComponent(chatLanguage);
        sessionComponent.assertIsOpened();
        return sessionComponent.messageExistsInChat(message, timeout);
    }

    public ChatDW openPartnerChat(Language chatLanguage) {
        info(String.format("Open 'Partner chat' dialog window for language '%s'.", chatLanguage));
        LanguageSessionComponent sessionComponent = createLanguageSessionComponent(chatLanguage);
        sessionComponent.assertIsOpened();
        return sessionComponent.openChat();
    }

    public boolean isLanguageSessionVisible(Language language, Duration timeout) {
        return createLanguageSessionComponent(language).visible(timeout);
    }

    public boolean isLanguageSessionNotVisible(Language language, Duration timeout) {
        return createLanguageSessionComponent(language).notVisible(timeout);
    }

    public void turnVolumeOnForSourceSession() {
        info("Turn volume on for Source session.");
        sourceSession.turnVolumeOn();
    }

    public void turnVolumeOnForLanguageSession(Language language) {
        info(String.format("Turn volume on for '%s' language session.", language));
        createLanguageSessionComponent(language).turnVolumeOn();
    }

    public void turnVolumeOffForSourceSession() {
        info("Turn volume off for Source session.");
        sourceSession.turnVolumeOff();
    }

    public void turnVolumeOffForLanguageSession(Language language) {
        info(String.format("Turn volume off for '%s' language session.", language));
        createLanguageSessionComponent(language).turnVolumeOff();
    }

    public boolean isVolumeOnForSourceSession() {
        return sourceSession.isVolumeOn();
    }

    public boolean isVolumeOnForLanguageSession(Language language) {
        return createLanguageSessionComponent(language).isVolumeOn();
    }

    public boolean isVolumeOffForSourceSession() {
        return sourceSession.isVolumeOff();
    }

    public boolean isVolumeOffForLanguageSession(Language language) {
        return createLanguageSessionComponent(language).isVolumeOff();
    }

    public StartVoicePublishingDW turnMicOnForLanguageSession(Language language) {
        info(String.format("Turn mic on for '%s' language session.", language));
        return createLanguageSessionComponent(language).turnMicOn();
    }

    public void turnMicOffForLanguageSession(Language language) {
        info(String.format("Turn mic off for '%s' language session.", language));
        createLanguageSessionComponent(language).turnMicOff();
    }

    public boolean isMicOffForSourceSession(Duration timeout) {
        return sourceSession.isMicOff(timeout);
    }

    public boolean isMicOnForLanguageSession(Language language, Duration timeout) {
        return createLanguageSessionComponent(language).isMicOn(timeout) && header.getMicLanguage() == language;
    }

    public boolean isMicOffForLanguageSession(Language language, Duration timeout) {
        return createLanguageSessionComponent(language).isMicOff(timeout);
    }

    public boolean isIncomingVolumeLevelChangingInLanguageSession(Language language, Duration timeout) {
        return createLanguageSessionComponent(language).isIncomingVolumeLevelChanging(timeout);
    }

    public boolean isIncomingVolumeLevelNotChangingInLanguageSession(Language language, Duration timeout) {
        return createLanguageSessionComponent(language).isIncomingVolumeLevelNotChanging(timeout);
    }

    public boolean isOutgoingVolumeLevelChangingInSourceSession(Duration timeout) {
        return sourceSession.isOutgoingVolumeLevelChanging(timeout);
    }

    public boolean isOutgoingVolumeLevelChangingInLanguageSession(Language language, Duration timeout) {
        return createLanguageSessionComponent(language).isOutgoingVolumeLevelChanging(timeout);
    }

    public boolean isOutgoingVolumeLevelNotChangingInSourceSession(Duration timeout) {
        return sourceSession.isOutgoingVolumeLevelNotChanging(timeout);
    }

    public boolean isOutgoingVolumeLevelNotChangingInLanguageSession(Language language, Duration timeout) {
        return createLanguageSessionComponent(language).isOutgoingVolumeLevelNotChanging(timeout);
    }

    private LanguageSessionComponent createLanguageSessionComponent(Language language) {
        return new LanguageSessionComponent(language.name() + " language session component", this,
                By.xpath("//div[contains(@class, 'session-item') and .//div[text() = '" + language.name() + "']]"));
    }

    public void turnVideoOnForSourceSession() {
        info("Turn video ON for Source session.");
        sourceSession.turnVideoOn();
    }

    public void turnVideoOffForSourceSession() {
        info("Turn video OFF for Source session.");
        sourceSession.turnVideoOff();
    }

    public boolean isVideoOnForSourceSession(Duration timeout) {
        return sourceSession.isVideoOn(timeout);
    }

    public boolean isVideoOffForSourceSession(Duration timeout) {
        return sourceSession.isVideoOff(timeout);
    }

    public void restartAllLines() {
        info("Restart all lines.");
        header.restartAllLines();
    }

    public boolean isSpinnerDisplayed(Duration timeout) {
        return spinnerElement.visible(timeout);
    }

    public boolean isSpinnerNotDisplayed(Duration timeout) {
        return spinnerElement.notVisible(timeout);
    }

    public CountdownTimerDW openCountdownTimerDW() {
        info("Open 'Countdown Timer' dialog window.");
        return header.openCountdownTimerDW();
    }

    public boolean isTimerHoursValueEqual(int value, Duration timeout) {
        return header.isTimerHoursValueEqual(value, timeout);
    }

    public boolean isTimerMinutesValueEqual(int value, Duration timeout) {
        return header.isTimerMinutesValueEqual(value, timeout);
    }

    public int getTimerSeconds() {
        return header.getTimerSeconds();
    }

    public SessionsDW openSessionsDW() {
        info("Open 'Sessions' dialog window.");
        return sourceSession.openSessionsDW();
    }

    public void hideSourceSection() {
        info("Hide 'Source' section.");
        sourceSession.hide();
    }

    public void showSourceSection() {
        info("Show 'Source' section.");
        showSourceSectionButton.click();
    }

    public boolean isSourceSectionHidden(Duration timeout) {
        return sourceSession.notVisible(timeout);
    }

    public boolean isSourceSectionShown(Duration timeout) {
        return sourceSession.visible(timeout);
    }

    public boolean isSlowDownDWDisplayed() {
        SlowDownDW slowDownDW = createSlowDownDW();
        return slowDownDW.visible();
    }

    public boolean isSlowDownDWNotDisplayed(Duration timeout) {
        SlowDownDW slowDownDW = createSlowDownDW();
        return slowDownDW.notVisible(timeout);
    }

    public String getSlowDownMessage() {
        SlowDownDW slowDownDW = createSlowDownDW();
        slowDownDW.visible();
        return slowDownDW.getMessage();
    }

    public String getSlowDownInitials() {
        SlowDownDW slowDownDW = createSlowDownDW();
        slowDownDW.visible();
        return slowDownDW.getInitials();
    }

    public Language getSlowDownLanguage() {
        SlowDownDW slowDownDW = createSlowDownDW();
        slowDownDW.visible();
        return slowDownDW.getLanguage();
    }

    private SlowDownDW createSlowDownDW() {
        return new SlowDownDW(this);
    }

    @Override
    public boolean isVideoContainerVisible(String streamerName, Duration timeout) {
        return sourceSession.isVideoContainerVisible(streamerName, timeout);
    }

    @Override
    public boolean isVideoContainerNotVisible(String streamerName, Duration timeout) {
        return sourceSession.isVideoContainerNotVisible(streamerName, timeout);
    }

    @Override
    public boolean isScreenContainerVisible(String streamerName, Duration timeout) {
        return sourceSession.isScreenContainerVisible(streamerName, timeout);
    }

    @Override
    public boolean isScreenContainerNotVisible(String streamerName, Duration timeout) {
        return sourceSession.isScreenContainerNotVisible(streamerName, timeout);
    }

    public void closeBrowserTabInactiveErrorDW() {

        BrowserTabInactiveErrorDW browserTabInactiveErrorDW = new BrowserTabInactiveErrorDW(this);

        if (browserTabInactiveErrorDW.visible()) {
            browserTabInactiveErrorDW.close();
            browserTabInactiveErrorDW.notVisible();
        }
    }
}
