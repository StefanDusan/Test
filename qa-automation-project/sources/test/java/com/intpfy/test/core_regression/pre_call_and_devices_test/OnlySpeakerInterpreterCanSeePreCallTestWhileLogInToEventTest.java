package com.intpfy.test.core_regression.pre_call_and_devices_test;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.UsernamePage;
import com.intpfy.gui.pages.devices_test.TestDevicesPage;
import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;
import static com.intpfy.util.RandomUtil.createRandomInterpreterName;
import static com.intpfy.util.RandomUtil.createRandomSpeakerName;

public class OnlySpeakerInterpreterCanSeePreCallTestWhileLogInToEventTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create random event with enabled Pre call test for Interpreter and Speaker.
    private final Event event = Event
            .createRandomBuilder()
            .withAllowSpeakerInterpreterPreCallTest(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "[Pre-call Test] Check that ONLY Speaker/Interpreter can see Pre-Call Test while login to event",
            description = "Only Speaker and Interpreter redirected to 'Test devices' page upon Login.",
            groups = {
                    ONE_USER,
                    EVENT,
                    PRE_CALL_AND_DEVICES_TEST,
                    AUDIENCE,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("2253")
    public void test() {

        // Open 'Login' page.

        LoginPage loginPage = authorizer.logOut();
        loginPage.assertIsOpened();

        // Proceed to 'Username' page with Speaker token.

        UsernamePage usernamePage = loginPage.proceedToUsernamePage(event.getSpeakerToken());
        usernamePage.assertIsOpened();

        // Log in and check that 'Test devices' page opened.

        TestDevicesPage testDevicesPage = usernamePage.logInWithPreCallTest(createRandomSpeakerName());
        testDevicesPage.assertIsOpened();

        // Return to 'Login' page.

        loginPage = testDevicesPage.back();
        loginPage.assertIsOpened();

        // Proceed to 'Username' page with Interpreter token.

        usernamePage = loginPage.proceedToUsernamePage(event.getInterpreterToken());
        usernamePage.assertIsOpened();

        // Log in and check that 'Test devices' page opened.

        testDevicesPage = usernamePage.logInWithPreCallTest(createRandomInterpreterName());
        testDevicesPage.assertIsOpened();

        // Return to 'Login' page.

        loginPage = testDevicesPage.back();
        loginPage.assertIsOpened();

        // Log in as Audience and check that Audience page opened instead of 'Test devices' page.

        AudiencePage audiencePage = loginPage.logInAsAudience(event.getAudienceToken());
        audiencePage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
