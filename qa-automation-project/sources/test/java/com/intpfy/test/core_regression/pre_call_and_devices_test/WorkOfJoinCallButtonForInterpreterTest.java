package com.intpfy.test.core_regression.pre_call_and_devices_test;


import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.UsernamePage;
import com.intpfy.gui.pages.devices_test.TestDevicesPage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.verifiers.devices_test.TestDevicesPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;
import static com.intpfy.util.RandomUtil.createRandomInterpreterName;

public class WorkOfJoinCallButtonForInterpreterTest extends BaseWebTest implements EventTest {

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
            testName = "[Pre-call Test] Check the work of 'Join Call' button for Interpreter",
            description = "Interpreter can Join Event after Connection test passed.",
            groups = {
                    ONE_USER,
                    EVENT,
                    PRE_CALL_AND_DEVICES_TEST,
                    INTERPRETER
            }
    )
    @TestRailCase("2263")
    public void test() {

        // Open 'Login' page.

        LoginPage loginPage = authorizer.logOut();
        loginPage.assertIsOpened();

        // Proceed to 'Username' page with Interpreter token.

        UsernamePage usernamePage = loginPage.proceedToUsernamePage(event.getInterpreterToken());
        usernamePage.assertIsOpened();

        // Log in to 'Test devices' page.

        TestDevicesPage testDevicesPage = usernamePage.logInWithPreCallTest(createRandomInterpreterName());
        testDevicesPage.assertIsOpened();

        // Check that Connection test started and finished after some time.

        TestDevicesPageVerifier testDevicesPageVerifier = new TestDevicesPageVerifier(testDevicesPage);

        testDevicesPageVerifier.assertConnectionTestGoing();
        testDevicesPageVerifier.assertConnectionTestFinished();

        // Join call and check that Interpreter page opened.

        LanguageSettingsDW languageSettingsDW = testDevicesPage.joinCallAsInterpreter();
        languageSettingsDW.assertIsOpened();

        languageSettingsDW.save();
        languageSettingsDW.assertNotVisible();

        InterpreterPage interpreterPage = (InterpreterPage) languageSettingsDW.getPage();
        interpreterPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
