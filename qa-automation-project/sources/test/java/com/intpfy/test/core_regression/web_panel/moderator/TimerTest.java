package com.intpfy.test.core_regression.web_panel.moderator;

import com.intpfy.gui.dialogs.moderator.CountdownTimerDW;
import com.intpfy.gui.pages.event.ModeratorPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.moderator.ModeratorVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.intpfy.test.TestGroups.*;

public class TimerTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create random event.
    private final Event event = Event
            .createRandomBuilder()
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check Timer",
            description = "Moderator can set Timer.",
            groups = {
                    ONE_USER,
                    EVENT,
                    MODERATOR
            }
    )
    @TestRailCase("1792")
    public void test() {

        // Log in as Moderator.

        String moderatorName = RandomUtil.createRandomModeratorName();
        ModeratorPage moderatorPage = authorizer.logInAsModerator(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Open Timer dialog window.

        CountdownTimerDW timerDW = moderatorPage.openCountdownTimerDW();
        timerDW.assertIsOpened();

        // Set random Hours from 0 to 9 inclusive.
        // and random Minutes from 1 to 59 inclusive.

        int hours = RandomUtils.nextInt(0, 10);
        int minutes = RandomUtils.nextInt(1, 60);

        timerDW.setTime(hours, minutes);

        // Check that actual Timer Hours and Minutes value displayed in dialog window.

        ModeratorVerifier moderatorVerifier = new ModeratorVerifier(moderatorPage);

        moderatorVerifier.assertTimerHoursAndMinutesValue(timerDW, hours, minutes);

        // Save Timer.

        timerDW.save();
        timerDW.assertNotVisible();

        // Check that actual Timer Hours and Minutes value displayed on page.

        moderatorVerifier.assertTimerHoursAndMinutesValue(hours, minutes - 1);

        // Check that Timer Seconds value decreasing.

        Duration timerSecondsDecreasingTimeout = Duration.ofSeconds(2);
        moderatorVerifier.assertTimerSecondsValueDecreasing(timerSecondsDecreasingTimeout);

        throwErrorIfVerificationsFailed();
    }
}
