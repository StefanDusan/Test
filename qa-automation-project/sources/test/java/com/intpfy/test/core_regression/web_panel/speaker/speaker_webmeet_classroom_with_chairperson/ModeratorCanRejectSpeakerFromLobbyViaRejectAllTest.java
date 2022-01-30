package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.AdvancedModeratorPage;
import com.intpfy.gui.pages.event.LobbyPage;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.Toggles;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.LobbyTest;
import com.intpfy.util.BrowserUtil;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.moderator.AdvancedModeratorVerifier;
import com.intpfy.verifiers.general.LoginPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class ModeratorCanRejectSpeakerFromLobbyViaRejectAllTest extends BaseWebTest implements LobbyTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Toggles toggles = new Toggles.Builder()
            .withLobbyRoom(true)
            .build();

    // Create 'Connect Pro (Classroom)' event with enabled Lobby Room.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .withToggles(toggles)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Verify that Moderator can Reject the Speaker from lobby by clicking on Reject All",
            description = "Moderator can reject the Speaker from the Lobby Room using Reject All.",
            groups = {
                    THREE_USERS,
                    EVENT,
                    SPEAKER,
                    MODERATOR,
                    LOBBY
            }
    )

    @TestRailCase("2226")
    public void test() {

        // Log in as Moderator with Advanced monitoring.

        String moderatorName = RandomUtil.createRandomModeratorName();
        AdvancedModeratorPage moderatorPage = authorizer.logInAsModeratorWithAdvancedMonitoring(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Log in as first Speaker to Lobby Room.

        WebApplicationContext firstSpeakerContext = WebContextUtil.switchToNewContext();

        String firstSpeakerName = RandomUtil.createRandomSpeakerName();
        LobbyPage firstSpeakerLobbyPage = authorizer.logInToLobbyAsSpeaker(event, firstSpeakerName);
        firstSpeakerLobbyPage.assertIsOpened();

        // Log in as second Speaker to Lobby Room.

        WebApplicationContext secondSpeakerContext = WebContextUtil.switchToNewContext();

        String secondSpeakerName = RandomUtil.createRandomSpeakerName();
        LobbyPage secondSpeakerLobbyPage = authorizer.logInToLobbyAsSpeaker(event, secondSpeakerName);
        secondSpeakerLobbyPage.assertIsOpened();

        // Switch to Moderator then switch Source session Users to Lobby.

        WebContextUtil.switchToDefaultContext();

        moderatorPage.switchSourceSessionUsersToLobby();

        AdvancedModeratorVerifier moderatorVerifier = new AdvancedModeratorVerifier(moderatorPage);

        moderatorVerifier.assertUsersIsOfLobbyTypeInSourceSession();

        // Check that both Speakers are in the Lobby Room.

        moderatorVerifier.assertSpeakerPresentInLobbyInSourceSession(firstSpeakerName);
        moderatorVerifier.assertSpeakerPresentInLobbyInSourceSession(secondSpeakerName);

        // Reject all Speakers in Lobby.

        moderatorPage.rejectAllSpeakersInLobbyInSourceSession();

        // Switch to first Speaker, check if Login page is opened and 'Logged out by Host' message displayed.

        WebContextUtil.switchToContext(firstSpeakerContext);

        LoginPage firstSpeakerLoginPage = new LoginPage(BrowserUtil.getActiveWindow());
        firstSpeakerLoginPage.assertIsOpened();

        LoginPageVerifier firstSpeakerLoginPageVerifier = new LoginPageVerifier(firstSpeakerLoginPage);
        firstSpeakerLoginPageVerifier.assertLoggedOutByHostMessageDisplayed();

        // Switch to second Speaker, check if Login page is opened and 'Logged out by Host' message displayed.

        WebContextUtil.switchToContext(secondSpeakerContext);

        LoginPage secondSpeakerLoginPage = new LoginPage(BrowserUtil.getActiveWindow());
        secondSpeakerLoginPage.assertIsOpened();

        LoginPageVerifier secondSpeakerLoginPageVerifier = new LoginPageVerifier(secondSpeakerLoginPage);
        secondSpeakerLoginPageVerifier.assertLoggedOutByHostMessageDisplayed();

        // Switch back to Moderator and verify that Users tab is back in focus.

        WebContextUtil.switchToDefaultContext();

        moderatorVerifier.assertUsersIsOfUsersTypeInSourceSession();

        // Switch Source session Users to Lobby and verify that Lobby count number is not visible.

        moderatorPage.switchSourceSessionUsersToLobby();

        moderatorVerifier.assertUsersIsOfLobbyTypeInSourceSession();
        moderatorVerifier.assertLobbySpeakersCountNotVisibleInSourceSession();

        // Check that both Speakers are no longer displayed in Lobby.

        moderatorVerifier.assertSpeakerNotPresentInLobbyInSourceSession(firstSpeakerName);
        moderatorVerifier.assertSpeakerNotPresentInLobbyInSourceSession(secondSpeakerName);

        throwErrorIfVerificationsFailed();
    }
}
