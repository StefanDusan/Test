package com.intpfy.gui.pages.event;

import com.intpfy.gui.components.chats.EventChatComponent;
import com.intpfy.gui.components.speaker.QuestionType;
import com.intpfy.gui.components.speaker.SpeakerControlPanelComponent;
import com.intpfy.gui.components.speaker.VoteComponent;
import com.intpfy.gui.components.speaker.speakers.SpeakersComponent;
import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.gui.dialogs.common.RestartingConnectionDW;
import com.intpfy.gui.dialogs.logout.LogOutUserDW;
import com.intpfy.gui.dialogs.moderator.SlowDownDW;
import com.intpfy.gui.dialogs.speaker.*;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.model.Key;
import com.intpfy.model.Language;
import com.intpfy.model.Shortcut;
import com.intpfy.model.event.Role;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;

import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class SpeakerPage extends BaseEventPage {

    @ElementInfo(name = "Private chat", findBy = @FindBy(css = "div[uib-tooltip='Private Chat']"))
    private Button privateChatButton;

    @ElementInfo(name = "New private message notification", findBy =
    @FindBy(css = "div.private-chat-control._have-private-msg span"))
    private Element privateMessageNotificationElement;

    @ElementInfo(name = "Remote support help", findBy = @FindBy(css = "div.private-chat-control_moderator"))
    private Button remoteSupportHelpButton;

    @ElementInfo(name = "Vote tab", findBy = @FindBy(css = "div.vote-tab"))
    private Button voteTabButton;

    private final SpeakerControlPanelComponent controlPanel;
    private final EventChatComponent eventChat;
    private final SpeakersComponent speakers;
    private final VoteComponent voteComponent;

    public SpeakerPage(IPageContext pageContext) {
        super("Speaker page", pageContext);
        controlPanel = new SpeakerControlPanelComponent(this);
        eventChat = new EventChatComponent(this);
        speakers = new SpeakersComponent(this);
        voteComponent = new VoteComponent(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return roleElement.waitForTextContains("speaker", timeout);
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

    public void deleteEventChatMessage(String message) {
        info(String.format("Delete message '%s' in Event chat.", message));
        eventChat.deleteMessage(message);
    }

    public boolean isEventChatMessageHidden(String message, Duration timeout) {
        return eventChat.isMessageHidden(message, timeout);
    }

    public void restoreEventChatMessage(String message) {
        info(String.format("Restore message '%s' in Event chat.", message));
        eventChat.restoreMessage(message);
    }

    public void deleteEventChatMessageForever(String message) {
        info(String.format("Delete message '%s' in Event chat forever.", message));
        eventChat.deleteMessageForever(message);
    }

    public SelectFileRecipientsRolesDW uploadFile(String path) {
        info(String.format("Upload File '%s'.", path));
        eventChat.uploadFile(path);
        return new SelectFileRecipientsRolesDW(this);
    }

    public boolean areFileRolesEqual(String documentName, Set<Role> roles) {
        return eventChat.areRolesPresent(documentName, roles);
    }

    public ChatDW openPrivateChat() {
        info("Open Private chat.");
        if (privateChatCanBeOpened()) {
            privateChatButton.click();
        }
        return new ChatDW(this);
    }

    private boolean privateChatCanBeOpened() {
        return privateChatButton.clickable(AJAX_TIMEOUT);
    }

    public ChatDW openRemoteSupportHelpChat() {
        info("Open Remote support help chat.");
        if (remoteSupportHelpChatCanBeOpened()) {
            remoteSupportHelpButton.click();
        }
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

    public FullscreenInterfaceSpeakerPage enableFullscreenInterface() {
        info("Enable fullscreen interface.");
        videoPanel.enableFullscreenInterface();
        return new FullscreenInterfaceSpeakerPage(getPageContext());
    }

    public boolean unreadPrivateMessagesPresent(Duration timeout) {
        return privateMessageNotificationElement.visible(timeout);
    }

    public boolean unreadPrivateMessagesNotPresent(Duration timeout) {
        return privateMessageNotificationElement.notVisible(timeout);
    }

    public void logOutSpeakerFromParticipants(String speakerName) {

        info(String.format("Log out Speaker '%s' from Participants.", speakerName));

        LogOutUserDW logOutUserDW = speakers.logOutFromParticipants(speakerName);
        logOutUserDW.assertIsOpened();

        logOutUserDW.confirm();
        logOutUserDW.assertNotVisible();
    }

    public void raiseHand() {
        info("Raise hand.");
        controlPanel.raiseHand();
    }

    public void lowerHand() {
        info("Lower hand.");
        controlPanel.lowerHand();
    }

    public void raiseHandByShortcut() {
        info("Raise hand by shortcut.");
        raiseLowerHandByShortcut();
    }

    public void lowerHandByShortcut() {
        info("Lower hand by shortcut.");
        raiseLowerHandByShortcut();
    }

    private void raiseLowerHandByShortcut() {
        applyShortcut(Shortcut.RaiseLowerHand);
    }

    public boolean isHandRaised(Duration timeout) {
        return controlPanel.isHandRaised(timeout);
    }

    public boolean isHandDown(Duration timeout) {
        return controlPanel.isHandDown(timeout);
    }

    public RestartingConnectionDW restartAllLines() {
        info("Restart all lines.");
        header.restartAllLines();
        return new RestartingConnectionDW(this);
    }

    public boolean isSpeakerHandRaisedInParticipants(String speaker, Duration timeout) {
        return speakers.isHandRaisedInParticipants(speaker, timeout);
    }

    public boolean isSpeakerHandDownInParticipants(String speaker, Duration timeout) {
        return speakers.isHandDownInParticipants(speaker, timeout);
    }

    public boolean isSlowDownDWDisplayed(Duration timeout) {
        SlowDownDW slowDownDW = createSlowDownDW();
        return slowDownDW.visible(timeout);
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

    public void confirmHostJoinedMeetingDW() {
        HostJoinedMeetingDW hostJoinedMeetingDW = new HostJoinedMeetingDW(this);
        if (hostJoinedMeetingDW.visible()) {
            info("Confirm 'Host joined meeting' dialog window.");
            hostJoinedMeetingDW.confirm();
            hostJoinedMeetingDW.assertNotVisible();
        }
    }

    public void confirmHostLeftMeetingDW() {
        HostLeftMeetingDW hostLeftMeetingDW = new HostLeftMeetingDW(this);
        if (hostLeftMeetingDW.visible()) {
            info("Confirm 'Host left meeting' dialog window.");
            hostLeftMeetingDW.confirm();
            hostLeftMeetingDW.assertNotVisible();
        }
    }

    public boolean isSpeakerPresentInParticipants(String speakerName, Duration timeout) {
        return speakers.isPresentInParticipants(speakerName, timeout);
    }

    public boolean isSpeakerNotPresentInParticipants(String speakerName, Duration timeout) {
        return speakers.isNotPresentInParticipants(speakerName, timeout);
    }

    public boolean speakerHasHostRoleInParticipants(String speakerName, Duration timeout) {
        return speakers.hasHostRoleInParticipants(speakerName, timeout);
    }

    public boolean isSpeakersVisible(Duration timeout) {
        return speakers.visible(timeout);
    }

    public boolean isSpeakersNotVisible(Duration timeout) {
        return speakers.notVisible(timeout);
    }

    public void openVoteTab() {
        info("Open 'Vote' tab.");
        voteTabButton.click();
    }

    public boolean isVoteTabOpened(Duration timeout) {
        return voteComponent.visible(timeout);
    }

    public void selectVoteQuestionType(QuestionType type) {
        info(String.format("Select vote question type: '%s'.", type.name()));
        voteComponent.selectQuestionType(type);
    }

    public String getVoteQuestion() {
        return voteComponent.getQuestion();
    }

    public void setVoteQuestion(String question) {
        info(String.format("Set vote question: '%s'.", question));
        voteComponent.setQuestion(question);
    }

    public String getVoteAnswerAsHost(int answerIndex) {
        return voteComponent.getAnswerAsHost(answerIndex);
    }

    public void setVoteAnswer(String answer, int answerIndex) {
        info(String.format("Set vote answer: '%s' with index '%s'.", answer, answerIndex));
        voteComponent.setAnswer(answer, answerIndex);
    }

    public String getSingleVoteAnswerAsSpeaker(int index) {
        return voteComponent.getSingleAnswerAsSpeaker(index);
    }

    public String getMultipleVoteAnswerAsSpeaker(int index) {
        return voteComponent.getMultipleAnswerAsSpeaker(index);
    }

    public void selectSingleVoteAnswer(String answer) {
        info(String.format("Select vote answer: '%s'.", answer));
        voteComponent.selectSingleAnswer(answer);
    }

    public void selectMultipleVoteAnswers(String... answers) {
        info("Select vote answers: " + Arrays.toString(answers));
        voteComponent.selectMultipleAnswers(answers);
    }

    public StartVotingDW startVote() {
        info("Start vote.");
        voteComponent.done();
        return new StartVotingDW(this);
    }

    public void cancelVote() {
        info("Cancel vote.");
        voteComponent.cancel();
    }

    public void acceptVote() {
        info("Accept vote.");
        voteComponent.accept();
    }

    public boolean isVoteInProgress(Duration timeout) {
        return voteComponent.isInProgress(timeout);
    }

    public boolean isVoteAccepted(Duration timeout) {
        return voteComponent.isVoteAccepted(timeout);
    }

    public boolean isSingleVoteAnswerSelected(String answer, Duration timeout) {
        return voteComponent.isSingleAnswerSelected(answer, timeout);
    }

    public boolean isMultipleVoteAnswerSelected(String answer, Duration timeout) {
        return voteComponent.isMultipleAnswerSelected(answer, timeout);
    }

    public void vote() {
        info("Vote.");
        voteComponent.vote();
    }

    public void enableAutoVolume() {
        info("Enable auto volume.");
        controlPanel.enableAutoVolume();
    }

    public void disableAutoVolume() {
        info("Disable auto volume.");
        controlPanel.disableAutoVolume();
    }

    public boolean isAutoVolumeAvailable(Duration timeout) {
        return controlPanel.isAutoVolumeAvailable(timeout);
    }

    public boolean isAutoVolumeUnavailable(Duration timeout) {
        return controlPanel.isAutoVolumeUnavailable(timeout);
    }

    public boolean isAutoVolumeEnabled(Duration timeout) {
        return controlPanel.isAutoVolumeEnabled(timeout);
    }

    public boolean isAutoVolumeDisabled(Duration timeout) {
        return controlPanel.isAutoVolumeDisabled(timeout);
    }

    public void muteSourceChannel() {
        info("Mute Source channel.");
        controlPanel.muteSourceChannel();
    }

    public void muteLanguageChannel() {
        info("Mute Language channel.");
        controlPanel.muteLanguageChannel();
    }

    public void muteSourceAndLanguageChannelsByShortcut() {
        info("Mute Source and Languages channels by shortcut.");
        muteUnmuteSourceAndLanguageChannelsByShortcut();
    }

    public void unmuteSourceChannel() {
        info("Unmute Source channel.");
        controlPanel.unmuteSourceChannel();
    }

    public void unmuteLanguageChannel() {
        info("Unmute Language channel.");
        controlPanel.unmuteLanguageChannel();
    }

    public void unmuteSourceAndLanguageChannelsByShortcut() {
        info("Unmute Source and Languages channels by shortcut.");
        muteUnmuteSourceAndLanguageChannelsByShortcut();
    }

    public boolean isSourceChannelMuted(Duration timeout) {
        return controlPanel.isSourceChannelMuted(timeout);
    }

    public boolean isSourceChannelUnmuted(Duration timeout) {
        return controlPanel.isSourceChannelUnmuted(timeout);
    }

    public boolean isLanguageChannelMuted(Duration timeout) {
        return controlPanel.isLanguageChannelMuted(timeout);
    }

    public boolean isLanguageChannelUnmuted(Duration timeout) {
        return controlPanel.isLanguageChannelUnmuted(timeout);
    }

    public boolean isSourceChannelVolumeLevelChanging(Duration timeout) {
        return controlPanel.isSourceChannelVolumeLevelChanging(timeout);
    }

    public boolean isSourceChannelVolumeLevelNotChanging(Duration timeout) {
        return controlPanel.isSourceChannelVolumeLevelNotChanging(timeout);
    }

    public boolean isLanguageChannelVolumeLevelChanging(Duration timeout) {
        return controlPanel.isLanguageChannelVolumeLevelChanging(timeout);
    }

    public boolean isLanguageChannelVolumeLevelNotChanging(Duration timeout) {
        return controlPanel.isLanguageChannelVolumeLevelNotChanging(timeout);
    }

    public boolean isInterpretingLanguageSelected(Language language, Duration timeout) {
        return controlPanel.isInterpretingLanguageSelected(language, timeout);
    }

    public boolean isNoInterpretingLanguageSelected(Duration timeout) {
        return controlPanel.isNoInterpretingLanguageSelected(timeout);
    }

    public LanguageSettingsDW selectInterpretingLanguage() {
        info("Select Interpreting language.");
        return controlPanel.selectInterpretingLanguage();
    }

    public LanguageSettingsDW openLanguageSettings() {
        info("Open Language settings.");
        return header.openLanguageSettings();
    }

    public void connect() {
        info("Connect to stream.");
        controlPanel.connect();
    }

    public void connectByShortcut() {
        info("Connect to stream by shortcut.");
        if (isDisconnected(Duration.ZERO)) {
            applyShortcut(Shortcut.ConnectDisconnect);
        }
    }

    public boolean isConnected(Duration timeout) {
        return controlPanel.isConnected(timeout);
    }

    public void disconnect() {
        info("Disconnect from stream.");
        controlPanel.disconnect();
    }

    public void disconnectByShortcut(Shortcut shortcut) {
        info(String.format("Disconnect from stream by shortcut '%s'.", shortcut));
        applyShortcut(shortcut);
    }

    public ChatDW openPrivateChatByShortcut() {
        info("Open Private Chat by shortcut.");
        ChatDW privateChat = new ChatDW(this);
        if (privateChat.notVisible(Duration.ZERO)) {
            openClosePrivateChatByShortcut();
            privateChat.visible();
        }
        return privateChat;
    }

    public void closePrivateChatByShortcut() {
        info("Close Private Chat by shortcut.");
        ChatDW privateChat = new ChatDW(this);
        if (privateChat.visible(Duration.ZERO)) {
            applyShortcut(Shortcut.Escape);
            privateChat.notVisible();
        }
    }

    private void openClosePrivateChatByShortcut() {
        applyShortcut(Shortcut.OpenClosePrivateChat);
    }

    public boolean isDisconnected(Duration timeout) {
        return controlPanel.isDisconnected(timeout);
    }

    public boolean areStreamControlsDisabled(Duration timeout) {
        return controlPanel.areStreamControlsDisabled(timeout);
    }

    // Method should be used only for Speakers which accepted streaming invite from Host
    public void stopStreaming() {
        info("Stop streaming.");
        controlPanel.stopStreaming();
    }

    public void turnMicOn() {
        info("Turn Mic ON.");
        controlPanel.turnMicOn();
    }

    public void turnMicOnByShortcut() {
        info("Turn Mic ON by shortcut");
        if (isMicOff(Duration.ZERO)) {
            switchMicOnOffByShortcut();
        }
    }

    public void turnMicOnByShortcut(Duration duration) {
        info(String.format("Turn Mic ON by shortcut for '%s' seconds.", duration.getSeconds()));
        if (isMicOff(Duration.ZERO)) {
            pressAndHoldKey(Key.SPACE, duration);
        }
    }

    public void turnMicOff() {
        info("Turn Mic OFF.");
        controlPanel.turnMicOff();
    }

    public void turnMicOffByShortcut() {
        info("Turn Mic OFF by shortcut");
        if (isMicOn(Duration.ZERO)) {
            switchMicOnOffByShortcut();
        }
    }

    private void switchMicOnOffByShortcut() {
        applyShortcut(Shortcut.SwitchMicOnOff);
    }

    public boolean isMicOn(Duration timeout) {
        return controlPanel.isMicOn(timeout);
    }

    public boolean isMicOff(Duration timeout) {
        return controlPanel.isMicOff(timeout);
    }

    public boolean isMuted(Duration timeout) {
        return controlPanel.isMuted(timeout);
    }

    public void turnCameraOn() {
        info("Turn Camera ON.");
        controlPanel.turnCameraOn();
    }

    public void turnCameraOnByShortcut() {
        info("Turn Camera ON by shortcut");
        if (isCameraOff(Duration.ZERO)) {
            switchCameraOnOffByShortcut();
        }
    }

    public void turnCameraOff() {
        info("Turn Camera OFF.");
        controlPanel.turnCameraOff();
    }

    public void turnCameraOffByShortcut() {
        info("Turn Camera OFF by shortcut");
        if (isCameraOn(Duration.ZERO)) {
            switchCameraOnOffByShortcut();
        }
    }

    private void switchCameraOnOffByShortcut() {
        applyShortcut(Shortcut.SwitchCameraOnOff);
    }

    public boolean isCameraOn(Duration timeout) {
        return controlPanel.isCameraOn(timeout);
    }

    public boolean isCameraOff(Duration timeout) {
        return controlPanel.isCameraOff(timeout);
    }

    public void enableScreenSharing() {
        info("Enable screen sharing.");
        controlPanel.enableScreenSharing();
    }

    public boolean isScreenSharingEnabled(Duration timeout) {
        return controlPanel.isScreenSharingEnabled(timeout);
    }

    public void disableScreenSharing() {
        info("Disable screen sharing.");
        controlPanel.disableScreenSharing();
    }

    public boolean isScreenSharingDisabled(Duration timeout) {
        return controlPanel.isScreenSharingDisabled(timeout);
    }

    public void allowSpeakerToStreamInParticipants(String speakerName) {
        info(String.format("Allow Speaker '%s' to stream in Participants.", speakerName));
        speakers.allowToStreamInParticipants(speakerName);
    }

    public boolean isSpeakerAskedToStreamInParticipants(String speakerName, Duration timeout) {
        return speakers.isAskedToStreamInParticipants(speakerName, timeout);
    }

    public boolean isSpeakerStreamingInParticipants(String speakerName, Duration timeout) {
        return speakers.isStreamingInParticipants(speakerName, timeout);
    }

    public boolean isSpeakerNotStreamingInParticipants(String speakerName, Duration timeout) {
        return speakers.isNotStreamingInParticipants(speakerName, timeout);
    }

    public DisableStreamingDW disallowSpeakerToStreamInParticipants(String speakerName) {
        info(String.format("Disallow Speaker '%s' to stream in Participants.", speakerName));
        return speakers.disallowToStreamInParticipants(speakerName);
    }

    public boolean isVolumeLevelChanging(Duration timeout) {
        return controlPanel.isVolumeLevelChanging(timeout);
    }

    public boolean isVolumeLevelNotChanging(Duration timeout) {
        return controlPanel.isVolumeLevelNotChanging(timeout);
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

    public boolean isPublisherVideoContainerVisible(Duration timeout) {
        return videoPanel.isPublisherVideoContainerVisible(timeout);
    }

    public boolean isPublisherVideoContainerNotVisible(Duration timeout) {
        return videoPanel.isPublisherVideoContainerNotVisible(timeout);
    }

    public boolean isPublisherVideoContainerAudioOnly(Duration timeout) {
        return videoPanel.isPublisherVideoContainerAudioOnly(timeout);
    }

    public boolean isPublisherVideoContainerNotAudioOnly(Duration timeout) {
        return videoPanel.isPublisherVideoContainerNotAudioOnly(timeout);
    }

    public boolean isPublisherScreenContainerVisible(Duration timeout) {
        return videoPanel.isPublisherScreenContainerVisible(timeout);
    }

    public boolean isPublisherScreenContainerNotVisible(Duration timeout) {
        return videoPanel.isPublisherScreenContainerNotVisible(timeout);
    }

    public void setMaxVolumeLevelForSourceChannel() {
        info("Set Max volume level for Source channel.");
        controlPanel.setMaxVolumeLevelForSourceChannel();
    }

    public void setMaxVolumeLevelForSourceChannelByShortcut() {
        info("Set Max volume level for Source channel by shortcut.");
        Predicate<Void> volumeLevelNotMax = Predicate.not(p -> isVolumeLevelMaxForSourceChannel(Duration.ZERO));
        if (volumeLevelNotMax.test(null)) {
            Duration maxTimeToSetMaxOrMinVolumeLevel = getMaxTimeToSetMaxOrMinVolumeLevel();
            pressAndHoldKey(Key.S, maxTimeToSetMaxOrMinVolumeLevel);
            pressKeysHoldingAltTillConditionIsTrue(Keys.ARROW_UP, volumeLevelNotMax, maxTimeToSetMaxOrMinVolumeLevel);
        }
    }

    public void setMaxVolumeLevelForLanguageChannel() {
        info("Set Max volume level for Language channel.");
        controlPanel.setMaxVolumeLevelForLanguageChannel();
    }

    public void setMaxVolumeLevelForLanguageChannelByShortcut() {
        info("Set Max volume level for Language channel by shortcut.");
        Predicate<Void> volumeLevelNotMax = Predicate.not(p -> isVolumeLevelMaxForLanguageChannel(Duration.ZERO));
        if (volumeLevelNotMax.test(null)) {
            pressKeysHoldingAltTillConditionIsTrue(Keys.ARROW_UP, volumeLevelNotMax, getMaxTimeToSetMaxOrMinVolumeLevel());
        }
    }

    public void setMinVolumeLevelForSourceChannel() {
        info("Set Min volume level for Source channel.");
        controlPanel.setMinVolumeLevelForSourceChannel();
    }

    public void setMinVolumeLevelForSourceChannelByShortcut() {
        info("Set Min volume level for Source channel by shortcut.");
        Predicate<Void> volumeLevelNotMin = Predicate.not(p -> isVolumeLevelMinForSourceChannel(Duration.ZERO));
        if (volumeLevelNotMin.test(null)) {
            Duration maxTimeToSetMaxOrMinVolumeLevel = getMaxTimeToSetMaxOrMinVolumeLevel();
            pressAndHoldKey(Key.S, maxTimeToSetMaxOrMinVolumeLevel);
            pressKeysHoldingAltTillConditionIsTrue(Keys.ARROW_DOWN, volumeLevelNotMin, getMaxTimeToSetMaxOrMinVolumeLevel());
        }
    }

    public void setMinVolumeLevelForLanguageChannel() {
        info("Set Min volume level for Language channel.");
        controlPanel.setMinVolumeLevelForLanguageChannel();
    }

    public void setMinVolumeLevelForLanguageChannelByShortcut() {
        info("Set Min volume level for Language channel by shortcut.");
        Predicate<Void> volumeLevelNotMin = Predicate.not(p -> isVolumeLevelMinForLanguageChannel(Duration.ZERO));
        if (volumeLevelNotMin.test(null)) {
            pressKeysHoldingAltTillConditionIsTrue(Keys.ARROW_DOWN, volumeLevelNotMin, getMaxTimeToSetMaxOrMinVolumeLevel());
        }
    }

    public boolean isVolumeLevelMaxForSourceChannel(Duration timeout) {
        return controlPanel.isVolumeLevelMaxForSourceChannel(timeout);
    }

    public boolean isVolumeLevelMaxForLanguageChannel(Duration timeout) {
        return controlPanel.isVolumeLevelMaxForLanguageChannel(timeout);
    }

    public boolean isVolumeLevelMinForSourceChannel(Duration timeout) {
        return controlPanel.isVolumeLevelMinForSourceChannel(timeout);
    }

    public boolean isVolumeLevelMinForLanguageChannel(Duration timeout) {
        return controlPanel.isVolumeLevelMinForLanguageChannel(timeout);
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

    private void switchVideoOnlyFullscreenModeOnOffByShortcut() {
        applyShortcut(Shortcut.SwitchVideoOnlyFullscreenModeOnOff);
    }

    public boolean isVideoOnlyFullscreenModeEnabled(Duration timeout) {
        return videoPanel.isFullscreenInterfaceEnabled(timeout) &&
                new FullscreenInterfaceSpeakerPage(getPageContext()).isNotOpened(timeout);
    }

    public boolean isVideoOnlyFullscreenModeDisabled(Duration timeout) {
        return videoPanel.isFullscreenInterfaceDisabled(timeout) &&
                new FullscreenInterfaceSpeakerPage(getPageContext()).isNotOpened(timeout);
    }

    public void acceptFloorWithAudioOnly() {
        info("Accept Floor with Audio only by shortcut.");
        applyShortcut(Shortcut.AcceptFloorWithAudioOnly);
    }

    public void acceptFloorWithAudioAndVideoByShortcut() {
        info("Accept Floor with Audio and Video by shortcut.");
        applyShortcut(Shortcut.AcceptFloorWithAudioAndVideo);
    }

    public void rejectFloorByShortcut() {
        info("Reject Floor by shortcut.");
        applyShortcut(Shortcut.RejectFloor);
    }

    public LoginPage logOutByShortcut() {
        info("Log out by shortcut.");
        applyShortcut(Shortcut.LogOut);
        return new LoginPage(getPageContext());
    }

    public boolean isSpeakersOfLobbyType(Duration timeout) {
        return speakers.isOfLobbyType(timeout);
    }

    public boolean isSpeakersOfParticipantsType(Duration timeout) {
        return speakers.isOfParticipantsType(timeout);
    }

    public boolean areLobbyActionsAvailable(Duration timeout) {
        return speakers.areLobbyActionsAvailable(timeout);
    }

    public boolean areSpeakerLobbyActionsAvailable(String speakerName, Duration timeout) {
        return speakers.areLobbyActionsAvailable(speakerName, timeout);
    }

    public void switchSpeakersToLobby() {
        info("Switch Speakers to Lobby.");
        speakers.switchToLobby();
    }

    public void switchSpeakersToParticipants() {
        info("Switch Speakers to Participants.");
        speakers.switchToParticipants();
    }

    public boolean isLobbySpeakersCountEqual(int count, Duration timeout) {
        return speakers.isLobbySpeakersCountEqual(count, timeout);
    }

    public boolean isSpeakerPresentInLobby(String speakerName, Duration timeout) {
        return speakers.isPresentInLobby(speakerName, timeout);
    }

    public boolean isSpeakerNotPresentInLobby(String speakerName, Duration timeout) {
        return speakers.isNotPresentInLobby(speakerName, timeout);
    }

    public void admitSpeakerInLobby(String speakerName) {
        info(String.format("Admit Speaker '%s' in Lobby.", speakerName));
        speakers.admitInLobby(speakerName);
    }

    public void rejectSpeakerInLobby(String speakerName) {
        info(String.format("Reject Speaker '%s' in Lobby.", speakerName));
        speakers.rejectInLobby(speakerName);
    }

    public void admitAllSpeakersInLobby() {
        info("Admit all Speakers in Lobby.");
        speakers.admitAllInLobby();
    }

    public void rejectAllSpeakersInLobby() {
        info("Reject all Speakers in Lobby.");
        speakers.rejectAllInLobby();
    }

    public void searchSpeakerInLobby(String speakerName) {
        info(String.format("Search for Speaker '%s' in Lobby.", speakerName));
        speakers.searchInLobby(speakerName);
    }

    private void muteUnmuteSourceAndLanguageChannelsByShortcut() {
        applyShortcut(Shortcut.MuteUnmuteSourceAndLanguageChannels);
    }
}
