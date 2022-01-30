package com.intpfy.gui.pages.event;

import com.intpfy.gui.components.chats.EventChatComponent;
import com.intpfy.gui.components.chats.PartnerChatComponent;
import com.intpfy.gui.components.interpreter.InterpreterControlPanelComponent;
import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.gui.dialogs.common.RestartingConnectionDW;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.model.Key;
import com.intpfy.model.Language;
import com.intpfy.model.Shortcut;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.function.Predicate;

import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class InterpreterPage extends BaseEventPage {

    @ElementInfo(name = "Slow down", findBy = @FindBy(css = "button.slow-down-button"))
    private Button slowDownButton;

    @ElementInfo(name = "Remote support help", findBy = @FindBy(css = "div.private-chat-control_moderator"))
    private Button remoteSupportHelpButton;

    @ElementInfo(name = "Slow down request message", findBy =
    @FindBy(xpath = "//div[@class = 'info-notification' and text()='Request Sent']"))
    private Element requestSentElement;

    private final InterpreterControlPanelComponent controlPanel;
    private final EventChatComponent eventChat;
    private final PartnerChatComponent partnerChat;

    public InterpreterPage(IPageContext pageContext) {
        super("Interpreter page", pageContext);
        controlPanel = new InterpreterControlPanelComponent(this);
        eventChat = new EventChatComponent(this);
        partnerChat = new PartnerChatComponent(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return roleElement.waitForTextContains("interpreter", timeout);
    }

    @Override
    public EventChatComponent getEventChat() {
        return eventChat;
    }

    @Override
    public boolean isEventChatEnabled(Duration timeout) {
        return super.isEventChatEnabled(timeout) && eventChat.isDisabledByOrganizerTooltipNotDisplayedOnHover(timeout);
    }

    @Override
    public boolean isEventChatDisabled(Duration timeout) {
        return super.isEventChatDisabled(timeout) && eventChat.isDisabledByOrganizerTooltipDisplayedOnHover(timeout);
    }

    public void sendMessageToPartnerChat(String message) {
        info(String.format("Send message to Partner chat '%s'.", message));
        partnerChat.sendMessage(message);
    }

    public boolean isPartnerChatEnabled(Duration timeout) {
        return partnerChat.isEnabled(timeout);
    }

    public boolean messageExistsInPartnerChat(String message, Duration timeout) {
        return partnerChat.messageExists(message, timeout);
    }

    public ChatDW openRemoteSupportHelpChat() {
        info("Open 'Remote support help' chat.");
        remoteSupportHelpButton.clickable(AJAX_TIMEOUT);
        remoteSupportHelpButton.click();
        return new ChatDW(this);
    }

    public ChatDW openRemoteSupportHelpChatByShortcut() {
        info("Open 'Remote support help' chat by shortcut.");
        if (remoteSupportHelpChatCanBeOpened()) {
            applyShortcut(Shortcut.OpenRemoteSupportHelpChat);
        }
        return new ChatDW(this);
    }

    public void closeRemoteSupportHelpChatByShortcut() {
        info("Close 'Remote support help' chat by shortcut.");
        applyShortcut(Shortcut.Escape);
    }

    private boolean remoteSupportHelpChatCanBeOpened() {
        return remoteSupportHelpButton.clickable(AJAX_TIMEOUT);
    }

    public void requestSlowDown() {
        info("Request Slow down.");
        slowDownButton.click();
    }

    public FullscreenInterfaceInterpreterPage enableFullscreenInterface() {
        info("Enable fullscreen interface.");
        videoPanel.enableFullscreenInterface();
        return new FullscreenInterfaceInterpreterPage(getPageContext());
    }

    public boolean isOutgoingLanguageChangedMessageDisplayed(Language language, Duration timeout) {
        return partnerChat.isOutgoingLanguageChangedMessageDisplayed(language, timeout);
    }

    public boolean isSlowDownButtonActive(Duration timeout) {
        return slowDownButton.waitCssClassContains("_active", timeout);
    }

    public boolean isSlowDownButtonNotInactive(Duration timeout) {
        return slowDownButton.waitCssClassNotContains("_active", timeout);
    }

    public boolean isRequestSentDWDisplayed(Duration timeout) {
        return requestSentElement.visible(timeout);
    }

    public boolean isRequestSentPopUpNotDisplayed(Duration timeout) {
        return requestSentElement.notVisible(timeout);
    }

    public LanguageSettingsDW openLanguageSettings() {
        info("Open Language settings.");
        return header.openLanguageSettings();
    }

    public void changeIncomingLanguageToRelay() {
        info("Change incoming language to relay.");
        controlPanel.changeIncomingLanguageToRelay();
    }

    public void changeOutgoingLanguageToRelay() {
        info("Change outgoing language to relay.");
        controlPanel.changeOutgoingLanguageToRelay();
    }

    public void changeIncomingLanguageToSource() {
        info("Change incoming language to source.");
        controlPanel.changeIncomingLanguageToSource();
    }

    public void changeOutgoingLanguageToMain() {
        info("Change outgoing language to main.");
        controlPanel.changeOutgoingLanguageToMain();
    }

    public void switchMute() {
        info(isUnmuted(Duration.ZERO) ? "Mute." : "Unmute.");
        controlPanel.switchMute();
    }

    public void mute() {
        info("Mute.");
        controlPanel.mute();
    }

    public void muteByShortcut() {
        info("Mute by shortcut.");
        if (isUnmuted(Duration.ZERO)) {
            switchMicOnOffByShortcut();
        }
    }

    public boolean isMuted(Duration timeout) {
        return controlPanel.isMuted(timeout);
    }

    public void unmute() {
        info("Unmute.");
        controlPanel.unmute();
    }

    public void unmuteByShortcut() {
        info("Unmute by shortcut.");
        if (isMuted(Duration.ZERO)) {
            switchMicOnOffByShortcut();
        }
    }

    private void switchMicOnOffByShortcut() {
        applyShortcut(Shortcut.SwitchMicOnOff);
    }

    public boolean isUnmuted(Duration timeout) {
        return controlPanel.isUnmuted(timeout);
    }

    public boolean isMuteIndicatorDisabled(Duration timeout) {
        return controlPanel.isMuteIndicatorDisabled(timeout);
    }

    public RestartingConnectionDW restartAllLines() {
        info("Restart all lines.");
        header.restartAllLines();
        return new RestartingConnectionDW(this);
    }

    public boolean isHandoverAvailable() {
        return controlPanel.isHandoverAvailable();
    }

    public boolean isHandoverUnavailable() {
        return controlPanel.isHandoverUnavailable();
    }

    public void requestHandoverNow() {
        info("Request handover Now.");
        controlPanel.requestHandoverNow();
    }

    public boolean isHandoverWaitingForPartnerResponse() {
        return controlPanel.isHandoverWaitingForPartnerResponse();
    }

    public void selectHdVideoQuality() {
        info("Select HD video quality.");
        videoSwitchPanel.selectHdQuality();
    }

    public void selectHighVideoQuality() {
        info("Select High video quality.");
        videoSwitchPanel.selectHighQuality();
    }

    public void selectLowVideoQuality() {
        info("Select Low video quality.");
        videoSwitchPanel.selectLowQuality();
    }

    public void showVideoPanel() {
        info("Show video panel.");
        videoSwitchPanel.showVideo();
        videoPanel.visible();
    }

    public void hideVideoPanel() {
        info("Hide video panel.");
        videoSwitchPanel.hideVideo();
        videoPanel.notVisible();
    }

    public void muteIncomingChannel() {
        info("Mute Incoming channel.");
        controlPanel.muteIncomingChannel();
    }

    public void muteOutgoingChannel() {
        info("Mute Outgoing channel.");
        controlPanel.muteOutgoingChannel();
    }

    public void unmuteIncomingChannel() {
        info("Unmute Incoming channel.");
        controlPanel.unmuteIncomingChannel();
    }

    public void unmuteOutgoingChannel() {
        info("Unmute Outgoing channel.");
        controlPanel.unmuteOutgoingChannel();
    }

    public boolean isIncomingChannelMuted(Duration timeout) {
        return controlPanel.isIncomingChannelMuted(timeout);
    }

    public boolean isOutgoingChannelMuted(Duration timeout) {
        return controlPanel.isOutgoingChannelMuted(timeout);
    }

    public boolean isIncomingChannelUnmuted(Duration timeout) {
        return controlPanel.isIncomingChannelUnmuted(timeout);
    }

    public boolean isOutgoingChannelUnmuted(Duration timeout) {
        return controlPanel.isOutgoingChannelUnmuted(timeout);
    }

    public boolean isIncomingChannelVolumeLevelChanging(Duration timeout) {
        return controlPanel.isIncomingChannelVolumeLevelChanging(timeout);
    }

    public boolean isOutgoingChannelVolumeLevelChanging(Duration timeout) {
        return controlPanel.isOutgoingChannelVolumeLevelChanging(timeout);
    }

    public boolean isIncomingChannelVolumeLevelNotChanging(Duration timeout) {
        return controlPanel.isIncomingChannelVolumeLevelNotChanging(timeout);
    }

    public boolean isOutgoingChannelVolumeLevelNotChanging(Duration timeout) {
        return controlPanel.isOutgoingChannelVolumeLevelNotChanging(timeout);
    }

    public void setMaxVolumeLevelForIncomingChannel() {
        info("Set Max volume level for Incoming channel.");
        controlPanel.setMaxVolumeLevelForIncomingChannel();
    }

    public void setMaxVolumeLevelForIncomingChannelByShortcut() {
        info("Set Max volume level for Incoming channel by shortcut.");
        Predicate<Void> volumeLevelNotMax = Predicate.not(p -> isVolumeLevelMaxForIncomingChannel(Duration.ZERO));
        if (volumeLevelNotMax.test(null)) {
            pressKeysHoldingAltTillConditionIsTrue(Keys.ARROW_UP, volumeLevelNotMax, getMaxTimeToSetMaxOrMinVolumeLevel());
        }
    }

    public void setMaxVolumeLevelForOutgoingChannel() {
        info("Set Max volume level for Outgoing channel.");
        controlPanel.setMaxVolumeLevelForOutgoingChannel();
    }

    public void setMinVolumeLevelForIncomingChannel() {
        info("Set Min volume level for Incoming channel.");
        controlPanel.setMinVolumeLevelForIncomingChannel();
    }

    public void setMinVolumeLevelForIncomingChannelByShortcut() {
        info("Set Min volume level for Incoming channel by shortcut.");
        Predicate<Void> volumeLevelNotMin = Predicate.not(p -> isVolumeLevelMinForIncomingChannel(Duration.ZERO));
        if (volumeLevelNotMin.test(null)) {
            pressKeysHoldingAltTillConditionIsTrue(Keys.ARROW_DOWN, volumeLevelNotMin, getMaxTimeToSetMaxOrMinVolumeLevel());
        }
    }

    public void setMinVolumeLevelForOutgoingChannel() {
        info("Set Min volume level for Outgoing channel.");
        controlPanel.setMinVolumeLevelForOutgoingChannel();
    }

    public boolean isVolumeLevelMaxForIncomingChannel() {
        return isVolumeLevelMaxForIncomingChannel(AJAX_TIMEOUT);
    }

    private boolean isVolumeLevelMaxForIncomingChannel(Duration timeout) {
        return controlPanel.isVolumeLevelMaxForIncomingChannel(timeout);
    }

    public boolean isVolumeLevelMaxForOutgoingChannel() {
        return controlPanel.isVolumeLevelMaxForOutgoingChannel(AJAX_TIMEOUT);
    }

    public boolean isVolumeLevelMinForIncomingChannel() {
        return isVolumeLevelMinForIncomingChannel(AJAX_TIMEOUT);
    }

    private boolean isVolumeLevelMinForIncomingChannel(Duration timeout) {
        return controlPanel.isVolumeLevelMinForIncomingChannel(timeout);
    }

    public boolean isVolumeLevelMinForOutgoingChannel() {
        return controlPanel.isVolumeLevelMinForOutgoingChannel(AJAX_TIMEOUT);
    }

    public LoginPage logOutByShortcut() {
        info("Log out by shortcut.");
        applyShortcut(Shortcut.LogOut);
        return new LoginPage(getPageContext());
    }

    public boolean isVolumeLevelChanging(Duration timeout) {
        return controlPanel.isVolumeLevelChanging(timeout);
    }

    public void coughByShortcut(Duration duration) {
        info(String.format("Cough by shortcut for '%s' seconds.", duration.getSeconds()));
        if (isNotCoughing(Duration.ZERO)) {
            pressAndHoldKey(Key.SPACE, duration);
        }
    }

    public boolean isCoughing() {
        return controlPanel.isCoughing(AJAX_TIMEOUT);
    }

    public boolean isNotCoughing() {
        return isNotCoughing(AJAX_TIMEOUT);
    }

    private boolean isNotCoughing(Duration timeout) {
        return controlPanel.isNotCoughing(timeout);
    }

    public boolean isShortcutsIndicatorDisplayedForIncomingChannel(Duration timeout) {
        return controlPanel.isShortcutsIndicatorDisplayedForIncomingChannel(timeout);
    }

    public boolean isShortcutsIndicatorDisplayedForOutgoingChannel(Duration timeout) {
        return controlPanel.isShortcutsIndicatorDisplayedForOutgoingChannel(timeout);
    }

    public boolean isShortcutsIndicatorNotDisplayedForIncomingChannel(Duration timeout) {
        return controlPanel.isShortcutsIndicatorNotDisplayedForIncomingChannel(timeout);
    }

    public boolean isShortcutsIndicatorNotDisplayedForOutgoingChannel(Duration timeout) {
        return controlPanel.isShortcutsIndicatorNotDisplayedForOutgoingChannel(timeout);
    }

    public void changeIncomingLanguageByShortcut() {
        info("Change Incoming language by shortcut.");
        if (isShortcutsIndicatorNotDisplayedForIncomingChannel(Duration.ZERO)) {
            applyShortcut(Shortcut.ChangeIncomingLanguage);
            isShortcutsIndicatorDisplayedForIncomingChannel(AJAX_TIMEOUT);
        }
    }

    public void changeOutgoingLanguageByShortcut() {
        info("Change Outgoing language by shortcut.");
        if (isShortcutsIndicatorNotDisplayedForOutgoingChannel(Duration.ZERO)) {
            applyShortcut(Shortcut.ChangeOutgoingLanguage);
            isShortcutsIndicatorDisplayedForOutgoingChannel(AJAX_TIMEOUT);
        }
    }

    public boolean isIncomingLanguageActive(Language language, Duration timeout) {
        return controlPanel.isIncomingLanguageActive(language, timeout);
    }

    public boolean isOutgoingLanguageActive(Language language, Duration timeout) {
        return controlPanel.isOutgoingLanguageActive(language, timeout);
    }
}
