package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.dialogs.common.CallSettingsDW;
import com.intpfy.gui.pages.event.AdvancedModeratorPage;
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
import com.intpfy.verifiers.event.moderator.AdvancedModeratorVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class ModeratorCanAdmitSpeakerFromLobbyViaAdmitAllTest extends BaseWebTest implements LobbyTest {

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
            testName = "Verify that Moderator can admit the speaker from lobby to the event via Admit All",
            description = "Moderator can admit the Speaker from the Lobby Room using Admit All.",
            groups = {
                    THREE_USERS,
                    EVENT,
                    SPEAKER,
                    MODERATOR,
                    LOBBY
            }
    )

    @TestRailCase("2223")
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

        // Admit all Speakers in Lobby.

        moderatorPage.admitAllSpeakersInLobbyInSourceSession();

        // Switch to first Speaker, save suggested Call Settings and connect to an Event.

        WebContextUtil.switchToContext(firstSpeakerContext);

        SpeakerPage firstSpeakerPage = new SpeakerPage(BrowserUtil.getActiveWindow());
        firstSpeakerPage.assertIsOpened();

        CallSettingsDW callSettingsDW = new CallSettingsDW(firstSpeakerPage);
        callSettingsDW.assertIsOpened();
        callSettingsDW.save();
        callSettingsDW.assertNotVisible();

        // Switch to second Speaker, save suggested Call Settings and connect to an Event.

        WebContextUtil.switchToContext(secondSpeakerContext);

        SpeakerPage secondSpeakerPage = new SpeakerPage(BrowserUtil.getActiveWindow());
        secondSpeakerPage.assertIsOpened();

        callSettingsDW = new CallSettingsDW(secondSpeakerPage);
        callSettingsDW.assertIsOpened();
        callSettingsDW.save();
        callSettingsDW.assertNotVisible();

        // Switch back to Moderator and verify Lobby count number is not visible.

        WebContextUtil.switchToDefaultContext();

        moderatorVerifier.assertLobbySpeakersCountNotVisibleInSourceSession();

        // Check that both Speakers are in Users in Source session.

        moderatorVerifier.assertUsersIsOfUsersTypeInSourceSession();

        moderatorVerifier.verifyUserPresentInSourceSession(firstSpeakerName);
        moderatorVerifier.verifyUserPresentInSourceSession(secondSpeakerName);

        throwErrorIfVerificationsFailed();
    }
}
