package com.intpfy.test.core_regression.pre_call_and_devices_test;


import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.devices_test.TestDevicesPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class WorkOfBackButtonOnDevicesTestPageForAudienceTest extends BaseWebTest implements EventTest {

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
            testName = "[Pre-call Test] Check the work of 'Back' button on devices test page for Audience",
            description = "Audience can return to 'Login' page by clicking 'Back' button on 'Devices test' page.",
            groups = {
                    ONE_USER,
                    EVENT,
                    PRE_CALL_AND_DEVICES_TEST,
                    AUDIENCE
            }
    )
    @TestRailCase("2328")
    public void test() {

        // Open 'Login' page.

        LoginPage loginPage = authorizer.logOut();
        loginPage.assertIsOpened();

        // Log in to 'Test devices' page with Audience token.

        TestDevicesPage testDevicesPage = loginPage.logInWithPreCallTest(event.getAudienceToken());
        testDevicesPage.assertIsOpened();

        // Click on 'Back' button and check that 'Login' page opened.

        loginPage = testDevicesPage.back();
        loginPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
