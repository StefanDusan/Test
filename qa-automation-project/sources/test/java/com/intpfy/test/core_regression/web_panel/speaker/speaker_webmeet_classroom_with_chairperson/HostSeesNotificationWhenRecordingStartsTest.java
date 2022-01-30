package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.dialogs.moderator.EventRecordingAvailableDW;
import com.intpfy.gui.dialogs.moderator.RecordingSettingsDW;
import com.intpfy.gui.dialogs.speaker.EventBeingRecordedDW;
import com.intpfy.gui.pages.event.AdvancedModeratorPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.run.web.WebApplicationContext;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.moderator.AdvancedModeratorVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class HostSeesNotificationWhenRecordingStartsTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect Pro (Classroom)' event with enabled Recording panel for Moderator.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .withEnableModeratorRecording(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that host sees notification when recording starts",
            description = "Host sees notification when Moderator starts Recording.",
            groups = {
                    TWO_USERS,
                    EVENT,
                    SPEAKER,
                    MODERATOR
            }
    )
    @TestRailCase("1956")
    @TestRailBugId("CORE-3338")
    public void test() {

        // Log in as Moderator with Advanced monitoring.

        String moderatorName = RandomUtil.createRandomModeratorName();
        AdvancedModeratorPage moderatorPage = authorizer.logInAsModeratorWithAdvancedMonitoring(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Log in as Host with Meeting control only.

        WebApplicationContext hostContext = WebContextUtil.switchToNewContext();

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Switch to Moderator and enable Recording for Source session.

        WebContextUtil.switchToDefaultContext();

        EventRecordingAvailableDW eventRecordingAvailableDW = new EventRecordingAvailableDW(moderatorPage);
        eventRecordingAvailableDW.assertIsOpened();

        eventRecordingAvailableDW.confirm();
        eventRecordingAvailableDW.assertNotVisible();

        RecordingSettingsDW recordingSettingsDW = new RecordingSettingsDW(moderatorPage);
        recordingSettingsDW.assertIsOpened();

        recordingSettingsDW.enableRecordingForSourceSession();

        AdvancedModeratorVerifier moderatorVerifier = new AdvancedModeratorVerifier(moderatorPage);
        moderatorVerifier.assertRecordingForSourceSessionEnabled(recordingSettingsDW);

        recordingSettingsDW.close();
        recordingSettingsDW.assertNotVisible();

        // Switch to Host and check that 'Recording notification' displayed.

        WebContextUtil.switchToContext(hostContext);

        EventBeingRecordedDW eventBeingRecordedDW = new EventBeingRecordedDW(hostPage);
        eventBeingRecordedDW.assertIsOpened();

        eventBeingRecordedDW.close();
        eventBeingRecordedDW.assertNotVisible();

        throwErrorIfVerificationsFailed();
    }
}
