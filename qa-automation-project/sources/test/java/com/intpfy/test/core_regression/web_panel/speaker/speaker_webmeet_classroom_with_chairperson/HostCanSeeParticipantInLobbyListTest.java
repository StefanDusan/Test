package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.event.LobbyPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.Toggles;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.LobbyTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class HostCanSeeParticipantInLobbyListTest extends BaseWebTest implements LobbyTest {

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
            testName = "Verify that Host can see the Speaker in the Lobby Room",
            description = "Host can see the Speaker in the Lobby Room.",
            groups = {
                    TWO_USERS,
                    EVENT,
                    SPEAKER,
                    LOBBY
            }
    )

    @TestRailCase("2078")
    public void test() {

        // Log in as Host with Meeting control only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Log in as Speaker to Lobby.

        WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        LobbyPage lobbyPage = authorizer.logInToLobbyAsSpeaker(event, speakerName);
        lobbyPage.assertIsOpened();

        // Switch back to Host then switch Speakers to Lobby.

        WebContextUtil.switchToDefaultContext();
        hostPage.switchSpeakersToLobby();

        // Check that Speakers is switched to Lobby.

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(hostPage);

        speakerVerifier.assertSpeakersIsOfLobbyType();

        // Check that Speaker is the only person in the Lobby Room and its actions available.

        int expectedCountOfUsersInLobby = 1;
        speakerVerifier.assertLobbySpeakersCountEquals(expectedCountOfUsersInLobby);
        speakerVerifier.assertSpeakerPresentInLobby(speakerName);
        speakerVerifier.assertSpeakerLobbyActionsAvailable(speakerName);

        throwErrorIfVerificationsFailed();
    }
}
