package com.intpfy.test.core_regression.pre_call_and_devices_test;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.UsernamePage;
import com.intpfy.gui.pages.devices_test.TestDevicesPage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;
import static com.intpfy.util.RandomUtil.createRandomInterpreterName;
import static com.intpfy.util.RandomUtil.createRandomSpeakerName;

public class OnlyAudienceCanSeePreCallTestWhileLogInToEventTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Event Pro' event with enabled Pre call test for Audience.
    private final Event event = Event
            .createEventProBuilder()
            .withAllowAudiencePreCallTest(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "[Pre-call Test] Check that only Audience can see Pre-call Test while login to event",
            description = "Only Audience redirected to 'Test devices' page upon Login.",
            groups = {
                    ONE_USER,
                    EVENT,
                    PRE_CALL_AND_DEVICES_TEST,
                    AUDIENCE,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("2262")
    public void test() {

        // Open 'Login' page.

        LoginPage loginPage = authorizer.logOut();
        loginPage.assertIsOpened();

        // Log in with Audience token and check that 'Test devices' page opened.

        TestDevicesPage testDevicesPage = loginPage.logInWithPreCallTest(event.getAudienceToken());
        testDevicesPage.assertIsOpened();

        // Return to 'Login' page.

        loginPage = testDevicesPage.back();
        loginPage.assertIsOpened();

        // Proceed to 'Username' page with Interpreter token.

        UsernamePage usernamePage = loginPage.proceedToUsernamePage(event.getInterpreterToken());
        usernamePage.assertIsOpened();

        // Log in and check that Interpreter page opened.

        LanguageSettingsDW languageSettingsDW = usernamePage.logInAsInterpreter(createRandomInterpreterName());
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        InterpreterPage interpreterPage = (InterpreterPage) languageSettingsDW.getPage();
        interpreterPage.assertIsOpened();

        // Log out.

        loginPage = interpreterPage.logOut();
        loginPage.assertIsOpened();

        // Proceed to 'Username' page with Speaker token.

        usernamePage = loginPage.proceedToUsernamePage(event.getSpeakerToken());
        usernamePage.assertIsOpened();

        // Log in and check that Speaker page opened.

        SpeakerPage speakerPage = usernamePage.logInAsSpeakerWithoutCallSettings(createRandomSpeakerName());
        speakerPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
