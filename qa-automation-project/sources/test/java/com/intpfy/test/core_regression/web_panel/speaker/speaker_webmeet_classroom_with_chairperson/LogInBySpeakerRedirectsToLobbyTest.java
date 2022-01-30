package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.event.LobbyPage;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.Toggles;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.LobbyTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.general.LobbyPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class LogInBySpeakerRedirectsToLobbyTest extends BaseWebTest implements LobbyTest {

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
            testName = "Check login by Speaker (Lobby Room is enabled for the Event)",
            description = "Speaker is redirected to the Lobby Room when logs in.",
            groups = {
                    ONE_USER,
                    EVENT,
                    SPEAKER,
                    LOBBY
            }
    )

    @TestRailCase("2077")
    public void test() {

        // Log in as Speaker and check if Lobby page is opened.

        String speakerName = RandomUtil.createRandomSpeakerName();
        LobbyPage lobbyPage = authorizer.logInToLobbyAsSpeaker(event, speakerName);
        lobbyPage.assertIsOpened();

        // Check that Lobby message is displayed.

        LobbyPageVerifier lobbyPageVerifier = new LobbyPageVerifier(lobbyPage);
        lobbyPageVerifier.assertMessageDisplayed();

        throwErrorIfVerificationsFailed();
    }
}
