package com.intpfy.gui.components.emi.event_creation;

import com.intpfy.gui.components.emi.event_creation.settings.*;
import com.intpfy.gui.dialogs.common.AccessChangeDW;
import com.intpfy.gui.dialogs.emi.AllowlistDW;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class TokensAndNameCustomizationComponent extends BaseComponent {

    @ElementInfo(name = "Name to display", findBy = @FindBy(name = "displayName"))
    private Input nameToDisplayInput;

    private final TokensComponent tokens;
    private final AudienceSettingsComponent audienceSettings;
    private final SpeakerAndInterpreterSettingsComponent speakerAndInterpreterSettings;
    private final CaptionsSettingsComponent captionsSettings;
    private final EventAccessSettingsComponent eventAccessSettings;
    private final OtherSettingsComponent otherSettings;

    public TokensAndNameCustomizationComponent(IParent parent) {
        super("Tokens and name customization", parent, By.id("tab3"));
        tokens = new TokensComponent(this);
        audienceSettings = new AudienceSettingsComponent(this);
        speakerAndInterpreterSettings = new SpeakerAndInterpreterSettingsComponent(this);
        captionsSettings = new CaptionsSettingsComponent(this);
        eventAccessSettings = new EventAccessSettingsComponent(this);
        otherSettings = new OtherSettingsComponent(this);
    }

    public void setNameToDisplay(String displayName) {
        nameToDisplayInput.setText(displayName);
    }

    public String getAudienceToken() {
        return tokens.getAudienceToken();
    }

    public void setAudienceToken(String token) {
        tokens.setAudienceToken(token);
    }

    public String getInterpreterToken() {
        return tokens.getInterpreterToken();
    }

    public void setInterpreterToken(String token) {
        tokens.setInterpreterToken(token);
    }

    public String getSpeakerToken() {
        return tokens.getSpeakerToken();
    }

    public void setSpeakerToken(String token) {
        tokens.setSpeakerToken(token);
    }

    public String getModeratorToken() {
        return tokens.getModeratorToken();
    }

    public void setModeratorToken(String token) {
        tokens.setModeratorToken(token);
    }

    public boolean isAudienceTokenEqual(String token, Duration timeout) {
        return tokens.isAudienceTokenEqual(token, timeout);
    }

    public boolean isInterpreterTokenEqual(String token, Duration timeout) {
        return tokens.isInterpreterTokenEqual(token, timeout);
    }

    public boolean isSpeakerTokenEqual(String token, Duration timeout) {
        return tokens.isSpeakerTokenEqual(token, timeout);
    }

    public boolean isModeratorTokenEqual(String token, Duration timeout) {
        return tokens.isModeratorTokenEqual(token, timeout);
    }

    public AccessChangeDW blockAudienceAccess() {
        return audienceSettings.blockAccess();
    }

    public void allowAudienceAccessToSourceAudio() {
        audienceSettings.allowAccessToSourceAudio();
    }

    public boolean isAudienceAccessToSourceAudioAllowed(Duration timeout) {
        return audienceSettings.isAccessToSourceAudioAllowed(timeout);
    }

    public AccessChangeDW allowAudienceAccessToSourceVideoOnWeb() {
        return audienceSettings.allowAccessToSourceVideoOnWeb();
    }

    public boolean isAudienceAccessToSourceVideoOnWebAllowed(Duration timeout) {
        return audienceSettings.isAccessToSourceVideoOnWebAllowed(timeout);
    }

    public void enableAudienceFloorFillOnWeb() {
        audienceSettings.enableFloorFillOnWeb();
    }

    public void allowAudienceAccessToChat() {
        audienceSettings.allowAccessToChat();
    }

    public void enablePreCallTestForAudience() {
        audienceSettings.enablePreCallTest();
    }

    public boolean isPreCallTestEnabledForAudience(Duration timeout) {
        return audienceSettings.isPreCallTestEnabled(timeout);
    }

    public void enablePreCallTestForInterpreterAndSpeaker() {
        speakerAndInterpreterSettings.enablePreCallTest();
    }

    public boolean isPreCallTestEnabledForInterpreterAndSpeaker(Duration timeout) {
        return speakerAndInterpreterSettings.isPreCallTestEnabled(timeout);
    }

    public void enablePolling() {
        speakerAndInterpreterSettings.enablePolling();
    }

    public void enableEventChatForInterpreter() {
        speakerAndInterpreterSettings.enableEventChatForInterpreter();
    }

    public AccessChangeDW disableEventChatForInterpreter() {
        return speakerAndInterpreterSettings.disableEventChatForInterpreter();
    }

    public boolean isEventChatEnabledForInterpreter(Duration timeout) {
        return speakerAndInterpreterSettings.isEventChatEnabledForInterpreter(timeout);
    }

    public boolean isEventChatDisabledForInterpreter(Duration timeout) {
        return speakerAndInterpreterSettings.isEventChatDisabledForInterpreter(timeout);
    }

    public AccessChangeDW disableEventChatForSpeaker() {
        return speakerAndInterpreterSettings.disableEventChatForSpeaker();
    }

    public boolean isEventChatDisabledForSpeaker(Duration timeout) {
        return speakerAndInterpreterSettings.isEventChatDisabledForSpeaker(timeout);
    }

    public void enableAutoVolumeForSpeaker() {
        speakerAndInterpreterSettings.enableAutoVolumeForSpeaker();
    }

    public void enableFloorPassThrough() {
        speakerAndInterpreterSettings.enableFloorPassThrough();
    }

    public void enableCaptionsAccess() {
        captionsSettings.enableAccess();
    }

    public boolean isCaptionsAccessEnabled(Duration timeout) {
        return captionsSettings.isAccessEnabled(timeout);
    }

    public void enableActiveDirectoryAuthentication() {
        eventAccessSettings.enableActiveDirectoryAuthentication();
    }

    public void enableKeycloakAuthentication() {
        eventAccessSettings.enableKeycloakAuthentication();
    }

    public void applySecurityGroupSetToAudience(int setIndex) {
        eventAccessSettings.applySecurityGroupSetToAudience(setIndex);
    }

    public void applySecurityGroupSetToInterpreter(int setIndex) {
        eventAccessSettings.applySecurityGroupSetToInterpreter(setIndex);
    }

    public void applySecurityGroupSetToSpeaker(int setIndex) {
        eventAccessSettings.applySecurityGroupSetToSpeaker(setIndex);
    }

    public void applySecurityGroupSetToModerator(int setIndex) {
        eventAccessSettings.applySecurityGroupSetToModerator(setIndex);
    }

    public void disableSecurityGroups(int securityGroupSetIndex) {
        eventAccessSettings.disableSecurityGroups(securityGroupSetIndex);
    }

    public void addSecurityGroup(int securityGroupSetIndex, String securityGroup) {
        eventAccessSettings.addSecurityGroup(securityGroupSetIndex, securityGroup);
    }

    public AllowlistDW createAllowlist(int securityGroupSetIndex) {
        return eventAccessSettings.createAllowlist(securityGroupSetIndex);
    }

    public void addSecurityGroupSet() {
        eventAccessSettings.addSecurityGroupSet();
    }

    public void enableRecordingPanelForModerator() {
        otherSettings.enableRecordingPanelForModerator();
    }

    public void enableDocumentSharing() {
        otherSettings.enableDocumentSharing();
    }

    public boolean isDocumentSharingEnabled(Duration timeout) {
        return otherSettings.isDocumentSharingEnabled(timeout);
    }

    public void enableLobbyRoom() {
        otherSettings.enableLobbyRoom();
    }

    public boolean isLobbyRoomEnabled(Duration timeout) {
        return otherSettings.isLobbyRoomEnabled(timeout);
    }
}
