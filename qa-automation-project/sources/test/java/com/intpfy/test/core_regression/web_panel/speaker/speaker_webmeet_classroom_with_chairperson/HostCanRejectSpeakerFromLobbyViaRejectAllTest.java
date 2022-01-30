package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.LobbyPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.Toggles;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.LobbyTest;
import com.intpfy.util.BrowserUtil;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfy.verifiers.general.LoginPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class HostCanRejectSpeakerFromLobbyViaRejectAllTest extends BaseWebTest implements LobbyTest {

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
            testName = "Verify that Host can Reject the Speaker from lobby by clicking on Reject All",
            description = "Host can reject the Speaker from the Lobby Room via Reject All.",
            groups = {
                    TWO_USERS,
                    EVENT,
                    SPEAKER,
                    LOBBY
            }
    )

    @TestRailCase("2162")
    public void test() {

        // Log in as Host with Meeting control only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Log in as Speaker to Lobby.

        WebApplicationContext speakerContext = WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        LobbyPage lobbyPage = authorizer.logInToLobbyAsSpeaker(event, speakerName);
        lobbyPage.assertIsOpened();

        // Switch back to Host then switch Speakers to Lobby.

        WebContextUtil.switchToDefaultContext();

        hostPage.switchSpeakersToLobby();

        // Check that Speakers is switched to Lobby.

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.assertSpeakersIsOfLobbyType();

        // Check that Speaker is in the Lobby Room.

        hostVerifier.assertSpeakerPresentInLobby(speakerName);

        // Reject all Speakers in Lobby.

        hostPage.rejectAllSpeakersInLobby();

        // Switch to Speaker, check that he is redirected back to Login page with proper message.

        WebContextUtil.switchToContext(speakerContext);

        LoginPage speakerLoginPage = new LoginPage(BrowserUtil.getActiveWindow());
        speakerLoginPage.assertIsOpened();

        LoginPageVerifier speakerLoginPageVerifier = new LoginPageVerifier(speakerLoginPage);
        speakerLoginPageVerifier.assertLoggedOutByHostMessageDisplayed();

        // Switch back to Host then switch Speakers to Lobby.

        WebContextUtil.switchToDefaultContext();

        hostPage.switchSpeakersToLobby();
        hostVerifier.assertSpeakersIsOfLobbyType();

        // Check that Speaker is not in Lobby.

        hostVerifier.assertSpeakerNotPresentInLobby(speakerName);

        // Check that Speakers count in Lobby is equal 0.

        hostVerifier.assertLobbySpeakersCountEqualsZero();

        throwErrorIfVerificationsFailed();
    }
}
