package com.intpfy.test.core_regression.web_panel.keyboard_shortcuts.audience;


import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.audience.AudienceVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class EnableDisableActiveSpeakerSettingByShortcutTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect Pro (Classroom)' event with enabled Audience access to Video.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .withAllowAudienceVideo(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Enable/disable the ‘Active speaker’ setting by shortcut",
            description = "Audience can enable / disable 'Active speaker' setting by shortcut.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    AUDIENCE,
                    SPEAKER
            }
    )
    @TestRailCase("2039")
    public void test() {

        // Log in as Host 1 with Audio and Video.

        String firstHostName = RandomUtil.createRandomHostName();
        SpeakerPage firstHostPage = authorizer.logInAsHostWithAudioAndVideo(event, firstHostName);
        firstHostPage.assertIsOpened();

        SpeakerVerifier firstHostVerifier = new SpeakerVerifier(firstHostPage);
        firstHostVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Host 2 with Audio and Video.

        WebContextUtil.switchToNewContext();

        String secondHostName = RandomUtil.createRandomHostName();
        SpeakerPage secondHostPage = authorizer.logInAsHostWithAudioAndVideo(event, secondHostName);
        secondHostPage.assertIsOpened();

        SpeakerVerifier secondHostVerifier = new SpeakerVerifier(secondHostPage);
        secondHostVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Turn Mic OFF to let Host 1 be 'Active Speaker'.

        secondHostPage.turnMicOff();
        secondHostVerifier.assertMuted();

        // Log in as Audience.

        WebContextUtil.switchToNewContext();

        AudiencePage audiencePage = authorizer.logInAsAudience(event);
        audiencePage.assertIsOpened();

        // Select Source channel (connect).

        Language source = Language.Source;

        audiencePage.selectLanguageChannel(source);

        AudienceVerifier audienceVerifier = new AudienceVerifier(audiencePage);

        audienceVerifier.assertConnectedToLanguageChannel(source);

        // Check that video containers for Host 1 and Host 2 visible.

        audienceVerifier.assertVideoContainerVisible(firstHostName);
        audienceVerifier.assertVideoContainerVisible(secondHostName);

        // Check that 'All Speakers' setting enabled.

        audienceVerifier.assertAllSpeakersSettingEnabled();

        // Check that only Host 1 video container active (placed in the center).

        audienceVerifier.assertVideoContainerActive(firstHostName);
        audienceVerifier.assertVideoContainerInactive(secondHostName);

        // Enable 'Active Speaker' setting by shortcut.

        audiencePage.enableActiveSpeakerSettingByShortcut();
        audienceVerifier.assertActiveSpeakerSettingEnabled();

        // Check that only Host 1 video container visible.

        audienceVerifier.assertVideoContainerVisible(firstHostName);
        audienceVerifier.assertVideoContainerNotVisible(secondHostName);

        // Enable 'All Speakers' setting by shortcut.

        audiencePage.enableAllSpeakersSettingByShortcut();
        audienceVerifier.assertAllSpeakersSettingEnabled();

        // Check that video containers for Host 1 and Host 2 visible.

        audienceVerifier.assertVideoContainerVisible(firstHostName);
        audienceVerifier.assertVideoContainerVisible(secondHostName);

        // Check that only Host 1 video container active (placed in the center).

        audienceVerifier.assertVideoContainerActive(firstHostName);
        audienceVerifier.assertVideoContainerInactive(secondHostName);

        throwErrorIfVerificationsFailed();
    }
}
