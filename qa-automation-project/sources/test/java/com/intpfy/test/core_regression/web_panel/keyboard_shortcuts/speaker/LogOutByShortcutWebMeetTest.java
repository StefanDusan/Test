package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.speaker;


import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class LogOutByShortcutWebMeetTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect (WebMeet)' event.
    private final Event event = Event
            .createConnectWebMeetBuilder()
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Log out by shortcut (webmeet)",
            description = "Speaker can Log out by shortcut in 'Connect (WebMeet)' event.",
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    SPEAKER
            }
    )
    @TestRailCase("2066")
    public void test() {

        // Log in as Speaker.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Log out by shortcut.

        LoginPage loginPage = speakerPage.logOutByShortcut();
        loginPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}