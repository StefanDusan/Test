package com.intpfy.authorization;

import com.intpfy.gui.dialogs.common.CallSettingsDW;
import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.gui.dialogs.speaker.HostJoinedMeetingDW;
import com.intpfy.gui.pages.BaseAuthorizedPage;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.UsernamePage;
import com.intpfy.gui.pages.emi.BaseEmiPage;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.gui.pages.event.*;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.EventType;
import com.intpfy.user.BaseUser;
import com.intpfy.util.BrowserUtil;
import com.intpfy.util.UserUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfyqa.Environment;
import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.browser.BrowserWindow;
import com.intpfyqa.gui.web.selenium.exceptions.GUIException;
import com.intpfyqa.logging.ITestLogger;
import com.intpfyqa.logging.LogManager;
import com.intpfyqa.settings.WebSettings;

import java.util.Collections;
import java.util.List;

public class Authorizer {

    private static final Authorizer instance = new Authorizer();

    private static final ITestLogger log = LogManager.getCurrentTestLogger();

    Authorizer() {
    }

    public static Authorizer getInstance() {
        return instance;
    }

    public DashboardPage logInToEMI(BaseUser user) {
        debug(String.format("Log in to EMI with user: %s.", user.getEmail()));
        BaseUser currentUser = UserUtil.getCurrentUser();
        BaseAutomationPage currentPage = WebContextUtil.getCurrentPage();
        DashboardPage dashboardPage;
        if (!(currentPage instanceof BaseEmiPage) || !currentUser.equals(user)) {
            LoginPage loginPage = logOut();
            loginPage.assertIsOpened();
            dashboardPage = openDashboardPage(loginPage, user);
            UserUtil.setCurrentUser(user);
        } else {
            BaseEmiPage adminPage = ((BaseEmiPage) currentPage);
            adminPage.assertIsOpened();
            dashboardPage = adminPage.goToDashboardPage();
        }
        dashboardPage.assertIsOpened();
        return dashboardPage;
    }

    DashboardPage openDashboardPage(LoginPage loginPage, BaseUser user) {
        return loginPage.logInToEMI(user);
    }

    public AudiencePage logInAsAudience(Event event) {
        return logInAsAudience(event, null);
    }

    public AudiencePage logInAsAudienceWithUsername(Event event, String username) {
        return logInAsAudience(event, username);
    }

    private AudiencePage logInAsAudience(Event event, String username) {
        String logPattern = username == null ? "Log in to event as Audience with token: %s." : "Log in to event as Audience with token: %s and username: %s.";
        debug(String.format(logPattern, event.getAudienceToken(), username));
        if (shouldLogOut(AudiencePage.class, username)) {
            return openAudiencePage(event, username);
        }
        return (AudiencePage) WebContextUtil.getCurrentPage();
    }

    private AudiencePage openAudiencePage(Event event, String username) {
        LoginPage loginPage = logOut();
        loginPage.assertIsOpened();
        AudiencePage audiencePage;
        if (username == null) {
            audiencePage = loginPage.logInAsAudience(event.getAudienceToken());
        } else {
            UsernamePage usernamePage = loginPage.proceedToUsernamePage(event.getAudienceToken());
            usernamePage.assertIsOpened();
            audiencePage = usernamePage.logInAsAudience(username);
        }
        return audiencePage;
    }

    public InterpreterPage logInAsInterpreter(Event event, String username) {
        return logInAsInterpreter(event, username, Collections.emptyList());
    }

    public InterpreterPage logInAsInterpreter(Event event, String username, Language main) {
        return logInAsInterpreter(event, username, List.of(main));
    }

    public InterpreterPage logInAsInterpreter(Event event, String username, Language main, Language relay) {
        return logInAsInterpreter(event, username, List.of(main, relay));
    }

    private InterpreterPage logInAsInterpreter(Event event, String username, List<Language> languages) {
        debug(String.format("Log in to event as Interpreter with token: %s and username: %s.", event.getInterpreterToken(), username));
        if (shouldLogOut(InterpreterPage.class, username)) {
            return openInterpreterPage(event, username, languages);
        }
        return (InterpreterPage) WebContextUtil.getCurrentPage();
    }

    private InterpreterPage openInterpreterPage(Event event, String username, List<Language> languages) {
        LoginPage loginPage = logOut();
        loginPage.assertIsOpened();
        UsernamePage usernamePage = loginPage.proceedToUsernamePage(event.getInterpreterToken());
        usernamePage.assertIsOpened();
        LanguageSettingsDW languageSettingsDW = usernamePage.logInAsInterpreter(username);
        languageSettingsDW.assertIsOpened();
        selectLanguages(languageSettingsDW, languages);
        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();
        InterpreterPage interpreterPage = new InterpreterPage(BrowserUtil.getActiveWindow());
        interpreterPage.assertIsOpened();
        if (!languages.isEmpty()) {
            interpreterPage.isOutgoingLanguageChangedMessageDisplayed(languages.get(0), WebSettings.AJAX_TIMEOUT);
        }
        return interpreterPage;
    }

    public SpeakerPage logInAsSpeaker(Event event, String username) {
        return logInAsSpeaker(event, username, false, false, false, false);
    }

    public SpeakerPage logInAsSpeakerWithAudioOnly(Event event, String username) {
        if (event.getEventType() != EventType.WebMeet) {
            throw new IllegalArgumentException("'Audio only' setting available for Speaker only in WebMeet event type.");
        }
        return logInAsSpeaker(event, username, false, false, true, false);
    }

    public SpeakerPage logInAsSpeakerWithAudioAndVideo(Event event, String username) {
        if (event.getEventType() != EventType.WebMeet) {
            throw new IllegalArgumentException("'Video' setting available for Speaker only in WebMeet event type.");
        }
        return logInAsSpeaker(event, username, false, false, false, true);
    }

    public SpeakerPage logInAsHostWithAudioOnly(Event event, String username) {
        return logInAsHost(event, username, false, true, false);
    }

    public SpeakerPage logInAsHostWithAudioAndVideo(Event event, String username) {
        return logInAsHost(event, username, false, false, true);
    }

    public SpeakerPage logInAsHostWithMeetingControlOnly(Event event, String username) {
        return logInAsHost(event, username, true, false, false);
    }

    private SpeakerPage logInAsHost(Event event, String username, boolean meetingControlOnly, boolean audioOnly, boolean video) {
        checkEventTypeForHost(event);
        return logInAsSpeaker(event, username, true, meetingControlOnly, audioOnly, video);
    }

    private void checkEventTypeForHost(Event event) {
        if (event.getEventType() != EventType.Classroom) {
            throw new IllegalArgumentException("Host can log only in 'Connect Pro (Classroom)' event.");
        }
    }

    private SpeakerPage logInAsSpeaker(Event event, String username, boolean isHost, boolean meetingControlOnly, boolean audioOnly, boolean video) {
        debug(String.format("Log in to event as %s with token: %s and username: %s.", isHost ? "Host" : "Speaker", event.getSpeakerToken(), username));
        if (shouldLogOut(SpeakerPage.class, username)) {
            return openSpeakerPage(event, username, isHost, meetingControlOnly, audioOnly, video);
        }
        return (SpeakerPage) WebContextUtil.getCurrentPage();
    }

    private SpeakerPage openSpeakerPage(Event event, String username, boolean isHost, boolean meetingControlOnly, boolean audioOnly, boolean video) {
        LoginPage loginPage = logOut();
        loginPage.assertIsOpened();
        UsernamePage usernamePage = loginPage.proceedToUsernamePage(event.getSpeakerToken());
        usernamePage.assertIsOpened();
        EventType eventType = event.getEventType();
        SpeakerPage speakerPage;
        if (eventType == EventType.EventPro) {
            speakerPage = usernamePage.logInAsSpeakerWithoutCallSettings(username);
        } else {
            CallSettingsDW callSettingsDW = isHost ? usernamePage.logInAsHost(username, event.getHostPassword()) : usernamePage.logInAsSpeaker(username);
            callSettingsDW.assertIsOpened();
            setCallSettings(callSettingsDW, eventType, isHost, meetingControlOnly, audioOnly, video);
            callSettingsDW.assertNotVisible();
            speakerPage = new SpeakerPage(BrowserUtil.getActiveWindow());
            speakerPage.assertIsOpened();
        }
        confirmHostJoinedMeetingDW(speakerPage);
        return speakerPage;
    }

    public ModeratorPage logInAsModerator(Event event, String username) {
        return logInAsModerator(event, username, false);
    }

    public AdvancedModeratorPage logInAsModeratorWithAdvancedMonitoring(Event event, String username) {
        return (AdvancedModeratorPage) logInAsModerator(event, username, true);
    }

    private ModeratorPage logInAsModerator(Event event, String username, boolean useAdvancedMonitoring) {
        String token = event.getModeratorToken();
        debug(String.format("Log in to event as Moderator with token: %s and username: %s.", token, username));
        Class<? extends ModeratorPage> pageClass = useAdvancedMonitoring ? AdvancedModeratorPage.class : ModeratorPage.class;
        if (shouldLogOut(pageClass, username)) {
            return openModeratorPage(event, username, useAdvancedMonitoring);
        }
        return (ModeratorPage) WebContextUtil.getCurrentPage();
    }

    private ModeratorPage openModeratorPage(Event event, String username, boolean useAdvancedMonitoring) {
        LoginPage loginPage = logOut();
        loginPage.assertIsOpened();
        UsernamePage usernamePage = loginPage.proceedToUsernamePage(event.getModeratorToken());
        usernamePage.assertIsOpened();
        return useAdvancedMonitoring ? usernamePage.logInAsModeratorWithAdvancedMonitoring(username) : usernamePage.logInAsModerator(username);
    }

    public LobbyPage logInToLobbyAsSpeaker(Event event, String username) {
        debug(String.format("Log in to Lobby as a Speaker with token: %s and username: %s.", event.getSpeakerToken(), username));
        return openLobbyPage(event, username);
    }

    private LobbyPage openLobbyPage(Event event, String username) {
        LoginPage loginPage = logOut();
        loginPage.assertIsOpened();
        UsernamePage usernamePage = loginPage.proceedToUsernamePage(event.getSpeakerToken());
        usernamePage.assertIsOpened();
        return usernamePage.logInToLobbyAsSpeaker(username);
    }

    private void confirmHostJoinedMeetingDW(SpeakerPage speakerPage) {
        HostJoinedMeetingDW hostJoinedMeetingDW = new HostJoinedMeetingDW(speakerPage);
        if (hostJoinedMeetingDW.visible()) {
            debug("Confirm 'Host joined meeting' dialog window.");
            hostJoinedMeetingDW.confirm();
            hostJoinedMeetingDW.assertNotVisible();
        }
    }

    private void selectLanguages(LanguageSettingsDW languageSettingsDW, List<Language> languages) {
        if (!languages.isEmpty()) {
            debug(String.format("Select languages: '%s'.", languages));
            languageSettingsDW.selectOutgoingLanguage(languages.get(0));
            if (languages.size() > 1) {
                languageSettingsDW.selectRelayLanguage(languages.get(1));
            }
        }
    }

    private void setCallSettings(CallSettingsDW callSettingsDW, EventType eventType, boolean isHost, boolean meetingControlOnly, boolean audioOnly, boolean video) {
        if (eventType == EventType.WebMeet) {
            setCallSettingsForWebMeet(callSettingsDW, audioOnly, video);
        } else if (eventType == EventType.Classroom) {
            if (isHost) {
                setCallSettingsForClassroomAsHost(callSettingsDW, meetingControlOnly, audioOnly, video);
            } else {
                setCallSettingsForClassroomAsSpeaker(callSettingsDW);
            }
        }
    }

    private void setCallSettingsForWebMeet(CallSettingsDW callSettingsDW, boolean audioOnly, boolean video) {
        if (!audioOnly && !video) {
            /*
               WebMeet 'Call settings' dialog window has no 'Save' option.
               In case there are no media streams checks
               and we need only to connect as Speaker
               and be able to perform some actions like
               sending event or private chat messages and so on,
               we use 'Audio only' as default setting.
             */
            audioOnly = true;
        }
        setCallSettings(callSettingsDW, false, false, audioOnly, video);
    }

    private void setCallSettingsForClassroomAsSpeaker(CallSettingsDW callSettingsDW) {
        setCallSettings(callSettingsDW, true, false, false, false);
    }

    private void setCallSettingsForClassroomAsHost(CallSettingsDW callSettingsDW, boolean meetingControlOnly, boolean audioOnly, boolean video) {
        setCallSettings(callSettingsDW, false, meetingControlOnly, audioOnly, video);
    }

    private void setCallSettings(CallSettingsDW callSettingsDW, boolean saveOnly, boolean meetingControlOnly, boolean audioOnly, boolean video) {
        if (saveOnly) {
            callSettingsDW.save();
        } else if (meetingControlOnly) {
            callSettingsDW.selectMeetingControlOnly();
        } else if (audioOnly) {
            callSettingsDW.selectAudioOnly();
        } else if (video) {
            callSettingsDW.selectVideo();
        }
    }

    public LoginPage logOut() {

        BaseAutomationPage currentPage = WebContextUtil.getCurrentPage();
        LoginPage loginPage;

        if (currentPage instanceof BaseAuthorizedPage) {
            loginPage = logOutFromAuthorizedPage((BaseAuthorizedPage) currentPage);

        } else if (browserSessionHasJustStarted()) {
            loginPage = new LoginPage(BrowserUtil.getActiveWindow());

        } else if (currentPage instanceof LoginPage) {
            loginPage = (LoginPage) currentPage;

        } else {
            loginPage = openLoginPageByRestartingBrowserSession();
        }

        loginPage.assertIsOpened();
        return loginPage;
    }

    LoginPage openLoginPageByRestartingBrowserSession() {
        WebContextUtil.restartBrowserSession();
        return new LoginPage(BrowserUtil.getActiveWindow());
    }

    private LoginPage logOutFromAuthorizedPage(BaseAuthorizedPage page) {

        LoginPage loginPage = null;
        boolean exceptionThrown = false;

        try {
            debug("Log out.");
            loginPage = page.logOut();

        } catch (GUIException e) {
            info(e);
            exceptionThrown = true;
        }

        if (exceptionThrown || !loginPage.isOpened(WebSettings.instance().getPageTimeout())) {
            loginPage = openLoginPageByRestartingBrowserSession();
        }

        return loginPage;
    }

    private boolean browserSessionHasJustStarted() {
        return WebContextUtil.getCurrentPage() == null;
    }

    private LoginPage logOutByClearingLocalStorage() {
        debug("Log out by clearing local storage.");
        clearLocalStorage();
        BrowserWindow activeWindow = BrowserUtil.getActiveWindow();
        activeWindow.get(Environment.instance().getAppUrl());
        return new LoginPage(activeWindow);
    }

    private void clearLocalStorage() {
        BrowserUtil.getActiveWindow().executeScript("window.localStorage.clear();");
    }

    private void info(Throwable throwable) {
        log.info("Exception", String.valueOf(throwable));
    }

    private void debug(String message) {
        log.debug("Authorization", message);
    }

    private <T extends BaseEventPage> boolean shouldLogOut(Class<T> pageClass, String username) {

        BaseAutomationPage currentPage = WebContextUtil.getCurrentPage();

        if (pageClass.isInstance(currentPage)) {

            @SuppressWarnings("unchecked")
            T page = (T) currentPage;

            return !isUsernameEqual(page, username);
        }

        return true;
    }

    private boolean isUsernameEqual(BaseEventPage page, String username) {
        return page.getUsername().equals(username);
    }
}
