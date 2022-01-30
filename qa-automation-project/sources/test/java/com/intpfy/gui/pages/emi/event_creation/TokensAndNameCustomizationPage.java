package com.intpfy.gui.pages.emi.event_creation;

import com.intpfy.gui.components.emi.event_creation.TokensAndNameCustomizationComponent;
import com.intpfy.gui.dialogs.common.AccessChangeDW;
import com.intpfy.gui.dialogs.emi.AllowlistDW;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class TokensAndNameCustomizationPage extends BaseEventCreationPage {

    @ElementInfo(name = "Page title", findBy = @FindBy(xpath = "//span[contains(@class, 'step-title') and text()=' Step 3 of 3']"))
    private Element pageTitle;

    private final TokensAndNameCustomizationComponent tokensAndNameCustomization;

    public TokensAndNameCustomizationPage(IPageContext pageContext) {
        super("Add Event (Token) page", pageContext);
        tokensAndNameCustomization = new TokensAndNameCustomizationComponent(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return pageTitle.visible(timeout);
    }

    public String getAudienceToken() {
        return tokensAndNameCustomization.getAudienceToken();
    }

    public void setAudienceToken(String token) {
        info(String.format("Set Audience token '%s'.", token));
        tokensAndNameCustomization.setAudienceToken(splitToken(token));
    }

    public String getInterpreterToken() {
        return tokensAndNameCustomization.getInterpreterToken();
    }

    public void setInterpreterToken(String token) {
        info(String.format("Set Interpreter token '%s'.", token));
        tokensAndNameCustomization.setInterpreterToken(splitToken(token));
    }

    public String getSpeakerToken() {
        return tokensAndNameCustomization.getSpeakerToken();
    }

    public void setSpeakerToken(String token) {
        info(String.format("Set Speaker token '%s'.", token));
        tokensAndNameCustomization.setSpeakerToken(splitToken(token));
    }

    public String getModeratorToken() {
        return tokensAndNameCustomization.getModeratorToken();
    }

    public void setModeratorToken(String token) {
        info(String.format("Set Moderator token '%s'.", token));
        tokensAndNameCustomization.setModeratorToken(splitToken(token));
    }

    public boolean isAudienceTokenEqual(String token, Duration timeout) {
        return tokensAndNameCustomization.isAudienceTokenEqual(splitToken(token), timeout);
    }

    public boolean isInterpreterTokenEqual(String token, Duration timeout) {
        return tokensAndNameCustomization.isInterpreterTokenEqual(splitToken(token), timeout);
    }

    public boolean isSpeakerTokenEqual(String token, Duration timeout) {
        return tokensAndNameCustomization.isSpeakerTokenEqual(splitToken(token), timeout);
    }

    public boolean isModeratorTokenEqual(String token, Duration timeout) {
        return tokensAndNameCustomization.isModeratorTokenEqual(splitToken(token), timeout);
    }

    private String splitToken(String token) {
        String splitSymbol = "-";
        return token.contains(splitSymbol) ? token.split(splitSymbol)[1] : token;
    }

    public AccessChangeDW blockAudienceAccess() {
        info("Block Audience access.");
        return tokensAndNameCustomization.blockAudienceAccess();
    }

    public void allowAudienceAccessToSourceAudio() {
        info("Allow Audience access to Source Audio.");
        tokensAndNameCustomization.allowAudienceAccessToSourceAudio();
    }

    public boolean isAudienceAccessToSourceAudioAllowed(Duration timeout) {
        return tokensAndNameCustomization.isAudienceAccessToSourceAudioAllowed(timeout);
    }

    public AccessChangeDW allowAudienceAccessToSourceVideoOnWeb() {
        info("Allow Audience access to Source Video on Web.");
        return tokensAndNameCustomization.allowAudienceAccessToSourceVideoOnWeb();
    }

    public boolean isAudienceAccessToSourceVideoOnWebAllowed(Duration timeout) {
        return tokensAndNameCustomization.isAudienceAccessToSourceVideoOnWebAllowed(timeout);
    }

    public void enableAudienceFloorFillOnWeb() {
        info("Enable Audience Floor fill on Web.");
        tokensAndNameCustomization.enableAudienceFloorFillOnWeb();
    }

    public void allowAudienceAccessToChat() {
        info("Allow Audience access to Chat.");
        tokensAndNameCustomization.allowAudienceAccessToChat();
    }

    public void enablePreCallTestForAudience() {
        info("Enable Pre call test for Audience.");
        tokensAndNameCustomization.enablePreCallTestForAudience();
    }

    public boolean isPreCallTestEnabledForAudience(Duration timeout) {
        return tokensAndNameCustomization.isPreCallTestEnabledForAudience(timeout);
    }

    public void enablePreCallTestForInterpreterAndSpeaker() {
        info("Enable Pre call test for Interpreter and Speaker.");
        tokensAndNameCustomization.enablePreCallTestForInterpreterAndSpeaker();
    }

    public boolean isPreCallTestEnabledForInterpreterAndSpeaker(Duration timeout) {
        return tokensAndNameCustomization.isPreCallTestEnabledForInterpreterAndSpeaker(timeout);
    }

    public void enablePolling() {
        info("Enable Polling.");
        tokensAndNameCustomization.enablePolling();
    }

    public void enableEventChatForInterpreter() {
        info("Enable Event chat for Interpreter.");
        tokensAndNameCustomization.enableEventChatForInterpreter();
    }

    public boolean isEventChatEnabledForInterpreter(Duration timeout) {
        return tokensAndNameCustomization.isEventChatEnabledForInterpreter(timeout);
    }

    public boolean isEventChatDisabledForInterpreter(Duration timeout) {
        return tokensAndNameCustomization.isEventChatDisabledForInterpreter(timeout);
    }

    public AccessChangeDW disableEventChatForInterpreter() {
        info("Disable Event chat for Interpreter.");
        return tokensAndNameCustomization.disableEventChatForInterpreter();
    }

    public AccessChangeDW disableEventChatForSpeaker() {
        info("Disable Event chat for Speaker.");
        return tokensAndNameCustomization.disableEventChatForSpeaker();
    }

    public boolean isEventChatDisabledForSpeaker(Duration timeout) {
        return tokensAndNameCustomization.isEventChatDisabledForSpeaker(timeout);
    }

    public void enableAutoVolumeForSpeaker() {
        info("Enable Auto-volume for Speaker.");
        tokensAndNameCustomization.enableAutoVolumeForSpeaker();
    }

    public void enableFloorPassThrough() {
        info("Enable Floor pass through.");
        tokensAndNameCustomization.enableFloorPassThrough();
    }

    public void enableCaptionsAccess() {
        info("Enable Captions Access.");
        tokensAndNameCustomization.enableCaptionsAccess();
    }

    public boolean isCaptionsAccessEnabled(Duration timeout) {
        return tokensAndNameCustomization.isCaptionsAccessEnabled(timeout);
    }

    public void enableActiveDirectoryAuthentication() {
        info("Enable Active Directory authentication.");
        tokensAndNameCustomization.enableActiveDirectoryAuthentication();
    }

    public void enableKeycloakAuthentication() {
        info("Enable Keycloak authentication.");
        tokensAndNameCustomization.enableKeycloakAuthentication();
    }

    public void applySecurityGroupSetToAudience(int setIndex) {
        info(String.format("Apply Security Group Set #%s to Audience.", setIndex));
        tokensAndNameCustomization.applySecurityGroupSetToAudience(setIndex);
    }

    public void applySecurityGroupSetToInterpreter(int setIndex) {
        info(String.format("Apply Security Group Set #%s to Interpreter.", setIndex));
        tokensAndNameCustomization.applySecurityGroupSetToInterpreter(setIndex);
    }

    public void applySecurityGroupSetToSpeaker(int setIndex) {
        info(String.format("Apply Security Group Set #%s to Speaker.", setIndex));
        tokensAndNameCustomization.applySecurityGroupSetToSpeaker(setIndex);
    }

    public void applySecurityGroupSetToModerator(int setIndex) {
        info(String.format("Apply Security Group Set #%s to Moderator.", setIndex));
        tokensAndNameCustomization.applySecurityGroupSetToModerator(setIndex);
    }

    public void disableSecurityGroups(int securityGroupSetIndex) {
        info(String.format("Disable security groups in Security Group Set #%s", securityGroupSetIndex));
        tokensAndNameCustomization.disableSecurityGroups(securityGroupSetIndex);
    }

    public void addSecurityGroup(int securityGroupSetIndex, String securityGroup) {
        info(String.format("Add security group '%s' to Security Group Set #%s", securityGroup, securityGroupSetIndex));
        tokensAndNameCustomization.addSecurityGroup(securityGroupSetIndex, securityGroup);
    }

    public AllowlistDW createAllowlist(int securityGroupSetIndex) {
        info(String.format("Create Allowlist in Security Group Set #%s", securityGroupSetIndex));
        return tokensAndNameCustomization.createAllowlist(securityGroupSetIndex);
    }

    public void addSecurityGroupSet() {
        info("Add Security Group Set.");
        tokensAndNameCustomization.addSecurityGroupSet();
    }

    public void enableRecordingPanelForModerator() {
        info("Enable Recording panel for Moderator.");
        tokensAndNameCustomization.enableRecordingPanelForModerator();
    }

    public void enableDocumentSharing() {
        info("Enable Document Sharing.");
        tokensAndNameCustomization.enableDocumentSharing();
    }

    public boolean isDocumentSharingEnabled(Duration timeout) {
        return tokensAndNameCustomization.isDocumentSharingEnabled(timeout);
    }

    public void enableLobbyRoom() {
        info("Enable Lobby Room.");
        tokensAndNameCustomization.enableLobbyRoom();
    }

    public boolean isLobbyRoomEnabled(Duration timeout) {
        return tokensAndNameCustomization.isLobbyRoomEnabled(timeout);
    }

    public DashboardPage save() {
        info("Save event.");
        continueButton.clickWithActions();
        return new DashboardPage(getPageContext());
    }
}
