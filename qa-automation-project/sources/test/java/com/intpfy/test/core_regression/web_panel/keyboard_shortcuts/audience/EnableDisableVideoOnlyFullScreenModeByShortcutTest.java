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

public class EnableDisableVideoOnlyFullScreenModeByShortcutTest extends BaseWebTest implements EventTest {

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
            testName = "Enable/disable video-only full-screen mode by shortcut",
            description = "Audience can enable / disable 'Video only' Full screen mode by shortcut.",
            groups = {
                    TWO_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    SHORTCUTS,
                    AUDIENCE,
                    SPEAKER
            }
    )
    @TestRailCase("2036")
    public void test() {

        // Log in as Host with Audio and Video.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithAudioAndVideo(event, hostName);
        hostPage.assertIsOpened();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);
        hostVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Log in as Audience.

        WebContextUtil.switchToNewContext();

        AudiencePage audiencePage = authorizer.logInAsAudience(event);
        audiencePage.assertIsOpened();

        // Select Source channel (connect).

        Language source = Language.Source;

        audiencePage.selectLanguageChannel(source);

        AudienceVerifier audienceVerifier = new AudienceVerifier(audiencePage);

        audienceVerifier.assertConnectedToLanguageChannel(source);

        // Enable 'Video only' fullscreen mode by shortcut.

        audiencePage.enableVideoOnlyFullscreenModeByShortcut();
        audienceVerifier.assertVideoOnlyFullscreenModeEnabled();

        // Disable 'Video only' fullscreen mode by shortcut.

        audiencePage.disableVideoOnlyFullscreenModeByShortcut();
        audienceVerifier.assertVideoOnlyFullscreenModeDisabled();

        throwErrorIfVerificationsFailed();
    }
}
