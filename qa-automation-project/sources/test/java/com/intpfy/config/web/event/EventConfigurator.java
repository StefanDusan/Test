package com.intpfy.config.web.event;

import com.intpfy.authorization.Authorizer;
import com.intpfy.config.web.BaseConfigurator;
import com.intpfy.gui.dialogs.common.AccessChangeDW;
import com.intpfy.gui.dialogs.emi.AllowlistDW;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.gui.pages.emi.EventsPage;
import com.intpfy.gui.pages.emi.event_creation.GeneralInfoPage;
import com.intpfy.gui.pages.emi.event_creation.ManagerAndModeratorPage;
import com.intpfy.gui.pages.emi.event_creation.TokensAndNameCustomizationPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.*;
import com.intpfy.user.BaseUser;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.emi.event_creation.GeneralInfoPageVerifier;
import com.intpfy.verifiers.emi.event_creation.TokensAndNameCustomizationPageVerifier;
import com.intpfyqa.gui.web.selenium.BaseAutomationPage;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class EventConfigurator extends BaseConfigurator {

    private static final EventConfigurator instance = new EventConfigurator(Authorizer.getInstance());

    EventConfigurator(Authorizer authorizer) {
        super(authorizer);
    }

    public static EventConfigurator getInstance() {
        return instance;
    }

    public DashboardPage create(Event event, BaseUser user) {

        GeneralInfoPage generalPage = openAddEventGeneralPage(user);
        generalPage.assertIsOpened();

        createGeneralPart(generalPage, event);

        ManagerAndModeratorPage managerAndModeratorPage = generalPage.clickContinue();
        managerAndModeratorPage.assertIsOpened();

        TokensAndNameCustomizationPage tokensPage = managerAndModeratorPage.proceedToAddEventTokensPage();
        tokensPage.assertIsOpened();

        createTokensPart(tokensPage, event);

        DashboardPage dashboardPage = tokensPage.save();
        dashboardPage.assertIsOpened();

        return dashboardPage;
    }

    public EventsPage delete(Event event, BaseUser user) {

        BaseAutomationPage currentPage = WebContextUtil.getCurrentPage();
        EventsPage eventsPage;

        if (!(currentPage instanceof EventsPage)) {

            DashboardPage mainPage = openDashboardPage(user);
            mainPage.assertIsOpened();

            eventsPage = mainPage.goToEventsPage();
            eventsPage.assertIsOpened();

        } else {
            eventsPage = (EventsPage) currentPage;
        }

        return eventsPage.delete(event);
    }

    private GeneralInfoPage openAddEventGeneralPage(BaseUser user) {

        DashboardPage mainPage = authorizer.logInToEMI(user);
        mainPage.assertIsOpened();

        return mainPage.goToGeneralInfoPage();
    }

    private DashboardPage openDashboardPage(BaseUser user) {
        return authorizer.logInToEMI(user);
    }

    private void createGeneralPart(GeneralInfoPage generalPage, Event event) {

        generalPage.selectEventType(event.getEventType());

        if (event.isHideParticipants()) {
            generalPage.hideDelegatesStatus();
        }

        setHostPassword(generalPage, event);
        generalPage.setEventName(event.getName());
        selectLanguages(generalPage, event);
        generalPage.selectDate(event.getStartDate().toLocalDate());
        generalPage.selectLocation(event.getLocation());
    }

    private void setHostPassword(GeneralInfoPage generalPage, Event event) {

        if (event.getEventType() == EventType.Classroom) {

            String hostPassword = event.getHostPassword();

            generalPage.setHostPassword(hostPassword);

            GeneralInfoPageVerifier generalPageVerifier = new GeneralInfoPageVerifier(generalPage);
            generalPageVerifier.assertHostPasswordEquals(hostPassword);
        }
    }

    private void selectLanguages(GeneralInfoPage generalPage, Event event) {

        List<Language> languages = event.getLanguages();

        if (languages.size() > 0) {
            generalPage.selectLanguages(languages);
        }
    }

    private void createTokensPart(TokensAndNameCustomizationPage tokensPage, Event event) {

        setTokens(tokensPage, event);

        applyAudienceSettings(tokensPage, event);
        applySpeakerAndInterpreterSettings(tokensPage, event);
        applyCaptionsSettings(tokensPage, event);
        applyEventAccessSettings(tokensPage, event);
        applyOtherSettings(tokensPage, event);
    }

    private void setTokens(TokensAndNameCustomizationPage tokensPage, Event event) {

        TokensAndNameCustomizationPageVerifier tokensPageVerifier = new TokensAndNameCustomizationPageVerifier(tokensPage);

        String audienceToken = event.getAudienceToken();

        tokensPage.setAudienceToken(audienceToken);
        tokensPageVerifier.assertAudienceTokenEquals(audienceToken);

        String interpreterToken = event.getInterpreterToken();

        tokensPage.setInterpreterToken(interpreterToken);
        tokensPageVerifier.assertInterpreterTokenEquals(interpreterToken);

        String speakerToken = event.getSpeakerToken();

        tokensPage.setSpeakerToken(speakerToken);
        tokensPageVerifier.assertSpeakerTokenEquals(speakerToken);

        String moderatorToken = event.getModeratorToken();

        tokensPage.setModeratorToken(moderatorToken);
        tokensPageVerifier.assertModeratorTokenEquals(moderatorToken);
    }

    private void applyAudienceSettings(TokensAndNameCustomizationPage tokensPage, Event event) {

        TokensAndNameCustomizationPageVerifier tokensPageVerifier = new TokensAndNameCustomizationPageVerifier(tokensPage);

        if (event.isAudienceBlocked()) {
            confirmAccessChangeDW(tokensPage.blockAudienceAccess());
        }

        if (event.isAudienceAccessToFloor()) {
            tokensPage.allowAudienceAccessToSourceAudio();
            tokensPageVerifier.assertAudienceAccessToSourceAudioAllowed();
        }

        if (event.isAllowAudienceVideo()) {
            confirmAccessChangeDW(tokensPage.allowAudienceAccessToSourceVideoOnWeb());
            tokensPageVerifier.assertAudienceAccessToSourceVideoOnWebAllowed();
        }

        if (event.isAudienceAccessToAutoVolume()) {
            tokensPage.enableAudienceFloorFillOnWeb();
        }

        if (event.isAudienceAccessToChat()) {
            tokensPage.allowAudienceAccessToChat();
        }

        if (event.isAllowAudiencePreCallTest()) {
            tokensPage.enablePreCallTestForAudience();
            tokensPageVerifier.assertPreCallTestEnabledForAudience();
        }
    }

    private void applySpeakerAndInterpreterSettings(TokensAndNameCustomizationPage page, Event event) {

        TokensAndNameCustomizationPageVerifier verifier = new TokensAndNameCustomizationPageVerifier(page);

        if (event.isAllowSpeakerInterpreterPreCallTest()) {
            page.enablePreCallTestForInterpreterAndSpeaker();
            verifier.assertPreCallTestEnabledForInterpreterAndSpeaker();
        }

        if (event.isEnablePolling()) {
            page.enablePolling();
        }

        if (event.isInterpreterBlockToTypeInChat()) {

            confirmAccessChangeDW(page.disableEventChatForInterpreter());
            verifier.assertEventChatDisabledForInterpreter();

        } else {
            page.enableEventChatForInterpreter();
            verifier.assertEventChatEnabledForInterpreter();
        }

        if (event.isDisableSpeakerTypeEventChat()) {
            confirmAccessChangeDW(page.disableEventChatForSpeaker());
            verifier.assertEventChatDisabledForSpeaker();
        }

        if (event.isFloorToLanguageOnInterpreterSilence()) {
            page.enableAutoVolumeForSpeaker();
        }

        if (event.isPreventSourceDuplicate()) {
            page.enableFloorPassThrough();
        }
    }

    private void applyCaptionsSettings(TokensAndNameCustomizationPage page, Event event) {

        Toggles toggles = event.getToggles();

        if (toggles != null && toggles.isTranscriptAccess()) {

            TokensAndNameCustomizationPageVerifier verifier = new TokensAndNameCustomizationPageVerifier(page);

            page.enableCaptionsAccess();
            verifier.assertCaptionsAccessEnabled();
        }
    }

    private void applyOtherSettings(TokensAndNameCustomizationPage tokensPage, Event event) {

        if (event.isEnableModeratorRecording()) {
            tokensPage.enableRecordingPanelForModerator();
        }

        applyOtherSettingsToggles(tokensPage, event);
    }

    private void applyOtherSettingsToggles(TokensAndNameCustomizationPage page, Event event) {

        Toggles toggles = event.getToggles();

        if (toggles != null) {

            TokensAndNameCustomizationPageVerifier verifier = new TokensAndNameCustomizationPageVerifier(page);

            if (toggles.isDocumentSharing()) {

                page.enableDocumentSharing();
                verifier.assertDocumentSharingEnabled();
            }
            if (toggles.isLobbyEnabled()) {

                page.enableLobbyRoom();
                verifier.assertLobbyRoomEnabled();
            }
        }
    }

    private void applyEventAccessSettings(TokensAndNameCustomizationPage tokensPage, Event event) {

        if (event.isSamlAuthenticationRequired()) {

            tokensPage.enableActiveDirectoryAuthentication();

            configureSecurityGroupSets(tokensPage, event);

        } else if (event.isKeycloakAuthentication()) {

            tokensPage.enableKeycloakAuthentication();

            configureSecurityGroupSets(tokensPage, event);
        }
    }

    private void configureSecurityGroupSets(TokensAndNameCustomizationPage tokensPage, Event event) {

        List<SamlSecurityGroupSet> samlSecurityGroupSets = event.getSamlSecurityGroupSets();

        int setsCount = samlSecurityGroupSets.size();

        for (int setIndex = 1; setIndex <= setsCount; setIndex++) {

            if (setIndex > 1) tokensPage.addSecurityGroupSet();

            SamlSecurityGroupSet securityGroupSet = samlSecurityGroupSets.get(setIndex - 1);

            selectRolesForSecurityGroupSet(tokensPage, securityGroupSet, setIndex);
            configureSecurityGroupsForSecurityGroupSet(tokensPage, securityGroupSet, setIndex);
            createAllowlistsForSecurityGroupSet(tokensPage, securityGroupSet, setIndex);
        }
    }

    private void selectRolesForSecurityGroupSet(TokensAndNameCustomizationPage tokensPage, SamlSecurityGroupSet securityGroupSet, int securityGroupIndex) {

        Set<Role> roles = securityGroupSet.getRoles();

        if (!roles.isEmpty()) {
            applyActiveDirectoryToRoles(tokensPage, roles, securityGroupIndex);
        }
    }

    private void applyActiveDirectoryToRoles(TokensAndNameCustomizationPage tokensPage, Set<Role> roles, int securityGroupSetIndex) {
        roles.forEach(role -> applyActiveDirectoryToRole(tokensPage, role, securityGroupSetIndex));
    }

    private void applyActiveDirectoryToRole(TokensAndNameCustomizationPage tokensPage, Role role, int securityGroupSetIndex) {

        switch (role) {

            case Audience:
                tokensPage.applySecurityGroupSetToAudience(securityGroupSetIndex);
                break;
            case Interpreter:
                tokensPage.applySecurityGroupSetToInterpreter(securityGroupSetIndex);
                break;
            case Speaker:
                tokensPage.applySecurityGroupSetToSpeaker(securityGroupSetIndex);
                break;
            case Moderator:
                tokensPage.applySecurityGroupSetToModerator(securityGroupSetIndex);
                break;
            default:
                throw new IllegalArgumentException(String.format("Can not apply Active Directory to Role '%s'.", role));
        }
    }

    private void configureSecurityGroupsForSecurityGroupSet(TokensAndNameCustomizationPage page, SamlSecurityGroupSet securityGroupSet, int securityGroupIndex) {

        if (securityGroupSet.isNoSecurityGroups()) {
            page.disableSecurityGroups(securityGroupIndex);
        } else {
            Set<String> securityGroups = securityGroupSet.getGroups();
            securityGroups.forEach(s -> page.addSecurityGroup(securityGroupIndex, s));
        }
    }

    private void createAllowlistsForSecurityGroupSet(TokensAndNameCustomizationPage tokensPage, SamlSecurityGroupSet samlSecurityGroupSet, int securityGroupIndex) {

        Set<String> allowlistEmails = samlSecurityGroupSet.getWhitelistEmails();

        if (!allowlistEmails.isEmpty()) {

            AllowlistDW allowlistDW = tokensPage.createAllowlist(securityGroupIndex);
            allowlistDW.assertIsOpened();

            allowlistEmails.forEach(allowlistDW::addEmail);

            allowlistDW.confirm();
            allowlistDW.assertNotVisible();
        }
    }

    private void confirmAccessChangeDW(AccessChangeDW accessChangeDW) {
        if (accessChangeDW.visible()) {
            accessChangeDW.confirm();
            accessChangeDW.assertNotVisible();
        }
    }
}
