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

public class ModeratorCanRejectSpeakerFromLobbyTest extends BaseWebTest implements LobbyTest {

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
            testName = "Verify that Moderator can reject the speaker from lobby to the event",
            description = "Moderator can reject the Speaker from the Lobby Room.",
            groups = {
                    THREE_USERS,
                    EVENT,
                    SPEAKER,
                    MODERATOR,
                    LOBBY
            }
    )

    @TestRailCase("2224")
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

        // Check that both Speakers are in the Lobby Room and actions are available for the first one.

        moderatorVerifier.assertSpeakerPresentInLobbyInSourceSession(firstSpeakerName);
        moderatorVerifier.assertSpeakerPresentInLobbyInSourceSession(secondSpeakerName);

        moderatorVerifier.assertSpeakerLobbyActionsAvailableInSourceSession(firstSpeakerName);

        // Reject first Speaker.

        moderatorPage.rejectSpeakerInLobbyInSourceSession(firstSpeakerName);

        // Switch to first Speaker, check if Login page is opened and 'Logged out by Host' message displayed.

        WebContextUtil.switchToContext(firstSpeakerContext);

        LoginPage speakerLoginPage = new LoginPage(BrowserUtil.getActiveWindow());
        speakerLoginPage.assertIsOpened();

        LoginPageVerifier speakerLoginPageVerifier = new LoginPageVerifier(speakerLoginPage);
        speakerLoginPageVerifier.assertLoggedOutByHostMessageDisplayed();

        // Switch back to Moderator and verify that second Speaker is the only person in the Lobby Room.

        WebContextUtil.switchToDefaultContext();

        int expectedLobbySpeakersCount = 1;
        moderatorVerifier.assertLobbySpeakersCountEqualsInSourceSession(expectedLobbySpeakersCount);
        moderatorVerifier.assertSpeakerNotPresentInLobbyInSourceSession(firstSpeakerName);

        moderatorVerifier.assertSpeakerPresentInLobbyInSourceSession(secondSpeakerName);

        throwErrorIfVerificationsFailed();
    }
}
