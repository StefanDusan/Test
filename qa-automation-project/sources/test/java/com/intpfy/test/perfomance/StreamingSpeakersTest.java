package com.intpfy.test.perfomance;

import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.utils.TestUtils;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.intpfy.test.TestGroups.*;

public class StreamingSpeakersTest extends BaseWebTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private static final Duration TEST_DURATION = Duration.of(3, ChronoUnit.MINUTES);
    private static final int SPEAKERS_COUNT = 4;
    private static final String SPEAKER_TOKEN = "S-SRaER9Ut";
    private static final String HOST_PASSWORD = "ePwEha";

    @Test(
            testName = "Several Speakers stream for some time",
            description = "Several Speakers stream for some time.",
            enabled = false,
            groups = {
                    ONE_USER,
                    MEDIA_DEVICES_REQUIRED,
                    WEB_RTC,
                    EVENT,
                    SPEAKER
            }
    )
    public void test() {

        Event event = Event.createConnectProClassroomBuilder()
                .withFloorToken(SPEAKER_TOKEN)
                .withChairpersonToken(HOST_PASSWORD)
                .build();

        String speakerName;
        SpeakerPage speakerPage;
        SpeakerVerifier speakerVerifier;

        for (int i = 0; i < SPEAKERS_COUNT; i++) {
            if (i > 0) WebContextUtil.switchToNewContext();
            speakerName = RandomUtil.createRandomHostName();
            speakerPage = authorizer.logInAsHostWithAudioAndVideo(event, speakerName);
            speakerPage.assertIsOpened();
            if (i > 0) speakerPage.confirmHostJoinedMeetingDW();
            speakerVerifier = new SpeakerVerifier(speakerPage);
            speakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();
        }

        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.plus(TEST_DURATION);

        while (LocalDateTime.now().isBefore(endDateTime)) {
            TestUtils.sleep(60000);
        }

        throwErrorIfVerificationsFailed();
    }
}
