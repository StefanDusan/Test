package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.event.AdvancedModeratorPage;
import com.intpfy.gui.pages.event.LobbyPage;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.Toggles;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.LobbyTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.moderator.AdvancedModeratorVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class LobbyFeatureIsAvailableOnModeratorPanelTest extends BaseWebTest implements LobbyTest {

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
            testName = "Verify that the lobby feature is available on Moderator panel",
            description = "Lobby feature is available for Moderator.",
            groups = {
                    TWO_USERS,
                    EVENT,
                    SPEAKER,
                    MODERATOR,
                    LOBBY
            }
    )

    @TestRailCase("2219")
    public void test() {

        // Log in as Moderator with Advanced monitoring.

        String moderatorName = RandomUtil.createRandomModeratorName();
        AdvancedModeratorPage moderatorPage = authorizer.logInAsModeratorWithAdvancedMonitoring(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Log in as Speaker to Lobby Room.

        WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        LobbyPage lobbyPage = authorizer.logInToLobbyAsSpeaker(event, speakerName);
        lobbyPage.assertIsOpened();

        // Switch to Moderator then switch Source session Users to Lobby.

        WebContextUtil.switchToDefaultContext();

        moderatorPage.switchSourceSessionUsersToLobby();

        AdvancedModeratorVerifier moderatorVerifier = new AdvancedModeratorVerifier(moderatorPage);

        moderatorVerifier.assertUsersIsOfLobbyTypeInSourceSession();

        // Check that Lobby actions are available.

        moderatorVerifier.assertLobbyActionsAvailableInSourceSession();

        // Check that Speaker is the only person in the Lobby Room and its actions available.

        int expectedLobbySpeakersCount = 1;
        moderatorVerifier.assertLobbySpeakersCountEqualsInSourceSession(expectedLobbySpeakersCount);
        moderatorVerifier.assertSpeakerPresentInLobbyInSourceSession(speakerName);
        moderatorVerifier.assertSpeakerLobbyActionsAvailableInSourceSession(speakerName);

        throwErrorIfVerificationsFailed();
    }
}
