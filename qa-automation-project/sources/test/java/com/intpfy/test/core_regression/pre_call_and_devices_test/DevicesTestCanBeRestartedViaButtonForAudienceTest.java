package com.intpfy.test.core_regression.pre_call_and_devices_test;


import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.devices_test.TestDevicesPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.verifiers.devices_test.TestDevicesPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class DevicesTestCanBeRestartedViaButtonForAudienceTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create random event with enabled Pre call test for Audience.
    private final Event event = Event
            .createRandomBuilder()
            .withAllowAudiencePreCallTest(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "[Pre-call Test] Check that device test can be restarted via button for Audience",
            description = "Audience can restart Connection test.",
            groups = {
                    ONE_USER,
                    EVENT,
                    PRE_CALL_AND_DEVICES_TEST,
                    AUDIENCE
            }
    )
    @TestRailCase("2326")
    public void test() {

        // Open 'Login' page.

        LoginPage loginPage = authorizer.logOut();
        loginPage.assertIsOpened();

        // Log in to 'Test devices' page with Audience token.

        TestDevicesPage testDevicesPage = loginPage.logInWithPreCallTest(event.getAudienceToken());
        testDevicesPage.assertIsOpened();

        // Check that Connection test started and finished after some time.

        TestDevicesPageVerifier testDevicesPageVerifier = new TestDevicesPageVerifier(testDevicesPage);

        testDevicesPageVerifier.assertConnectionTestGoing();
        testDevicesPageVerifier.assertConnectionTestFinished();

        // Restart Connection test.

        testDevicesPage.restartConnectionTest();

        // Check that Connection test started and finished after some time.

        testDevicesPageVerifier.assertConnectionTestGoing();
        testDevicesPageVerifier.assertConnectionTestFinished();

        throwErrorIfVerificationsFailed();
    }
}
