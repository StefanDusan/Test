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

public class HostCanSearchSpeakersInLobbyTest extends BaseWebTest implements LobbyTest {

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
            testName = "Verify that Host can Search for participants in Lobby",
            description = "Host can search for Speakers in Lobby Room.",
            groups = {
                    THREE_USERS,
                    EVENT,
                    SPEAKER,
                    LOBBY
            }
    )

    @TestRailCase("2220")
    public void test() {

        // Log in as Host with Meeting control only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Log in as first Speaker to Lobby.

        WebContextUtil.switchToNewContext();

        String firstSpeakerName = "speaker1";
        LobbyPage firstSpeakerLobbyPage = authorizer.logInToLobbyAsSpeaker(event, firstSpeakerName);
        firstSpeakerLobbyPage.assertIsOpened();

        // Log in as second Speaker to Lobby.

        WebContextUtil.switchToNewContext();

        String secondSpeakerName = "Speaker2";
        LobbyPage secondSpeakerLobbyPage = authorizer.logInToLobbyAsSpeaker(event, secondSpeakerName);
        secondSpeakerLobbyPage.assertIsOpened();

        // Switch back to Host then switch Speakers to Lobby.

        WebContextUtil.switchToDefaultContext();

        hostPage.switchSpeakersToLobby();

        // Check that Speakers is switched to Lobby.

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.assertSpeakersIsOfLobbyType();

        // Check that both Speakers are in the Lobby.

        hostVerifier.assertSpeakerPresentInLobby(firstSpeakerName);
        hostVerifier.assertSpeakerPresentInLobby(secondSpeakerName);

        // Verify that both Speakers are visible in Lobby if searching for capital "S".

        String searchCriteria = "S";
        hostPage.searchSpeakerInLobby(searchCriteria);

        hostVerifier.assertSpeakerPresentInLobby(firstSpeakerName);
        hostVerifier.assertSpeakerPresentInLobby(secondSpeakerName);

        // Verify that only first Speaker is visible in Lobby if searching for digit "1".

        searchCriteria = "1";
        hostPage.searchSpeakerInLobby(searchCriteria);

        hostVerifier.assertSpeakerPresentInLobby(firstSpeakerName);
        hostVerifier.assertSpeakerNotPresentInLobby(secondSpeakerName);

        throwErrorIfVerificationsFailed();
    }
}
