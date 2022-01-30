package com.intpfy.test.core_regression.smoke_test.chats;

import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.audience.AudienceVerifier;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.event.moderator.ModeratorVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class ChatEnabledOnlyForHostAndModeratorTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect Pro (Classroom)' event with disabled Event chat for I-token and S-token.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .withInterpreterBlockToTypeInChat(true)
            .withDisableSpeakerTypeEventChat(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check chat enabled only for Host and Moderator",
            description = "Chat can be enabled only for Host and Moderator.",
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    EVENT,
                    CHATS,
                    AUDIENCE,
                    INTERPRETER,
                    SPEAKER,
                    MODERATOR
            }
    )
    @TestRailCase("1644")
    public void test() {

        // Log in as Host with Meeting control only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Check that Event chat enabled and that it's of 'Announcement' type.

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.verifyEventChatEnabled();
        hostVerifier.verifyEventChatIsOfAnnouncementsType();

        // Log in as Moderator.

        String moderatorName = RandomUtil.createRandomModeratorName();
        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Check that Event chat enabled for Source session.

        ModeratorVerifier moderatorVerifier = new ModeratorVerifier(moderatorPage);
        moderatorVerifier.verifyEventChatEnabled();

        // Log in as Speaker.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Check that Event chat disabled and that it's of 'Announcement' type.

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);

        speakerVerifier.verifyEventChatDisabled();
        speakerVerifier.verifyEventChatIsOfAnnouncementsType();

        // Log in as Interpreter.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName);
        interpreterPage.assertIsOpened();

        // Check that Event chat disabled and that it's of 'Announcement' type.

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);

        interpreterVerifier.verifyEventChatDisabled();
        interpreterVerifier.verifyEventChatIsOfAnnouncementsType();

        // Log in as Audience.

        AudiencePage audiencePage = authorizer.logInAsAudience(event);
        audiencePage.assertIsOpened();

        // Check that Event chat disabled.

        AudienceVerifier audienceVerifier = new AudienceVerifier(audiencePage);
        audienceVerifier.verifyEventChatDisabled();

        throwErrorIfVerificationsFailed();
    }
}
