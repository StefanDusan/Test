package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.speaker;


import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class OpenSpeakersPrivateChatByShortcutTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect Pro (Classroom)' event.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Open speakers' private chat by shortcut",
            description = "Speaker can open Private chat by shortcut.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    SPEAKER
            }
    )
    @TestRailCase("2015")
    public void test() {

        // Log in as Host with Meeting control only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Log in as Speaker.

        WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Open Private chat by shortcut.

        ChatDW privateChat = speakerPage.openPrivateChatByShortcut();
        privateChat.assertIsOpened();

        // Close chat by shortcut.

        speakerPage.closePrivateChatByShortcut();
        privateChat.assertNotVisible();

        throwErrorIfVerificationsFailed();
    }
}
