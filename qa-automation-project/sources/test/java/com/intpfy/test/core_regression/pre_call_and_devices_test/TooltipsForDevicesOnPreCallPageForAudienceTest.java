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

public class TooltipsForDevicesOnPreCallPageForAudienceTest extends BaseWebTest implements EventTest {

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
            testName = "[Pre-call Test] Check the tooltips for devices on precall page for Audience",
            description = "Tooltips displayed for Audio device on Audience 'Pre call test' page.",
            groups = {
                    ONE_USER,
                    EVENT,
                    PRE_CALL_AND_DEVICES_TEST,
                    AUDIENCE
            }
    )
    @TestRailCase("2329")
    public void test() {

        // Open 'Login' page.

        LoginPage loginPage = authorizer.logOut();
        loginPage.assertIsOpened();

        // Log in to 'Test devices' page with Audience token.

        TestDevicesPage testDevicesPage = loginPage.logInWithPreCallTest(event.getAudienceToken());
        testDevicesPage.assertIsOpened();

        // Wait until Audio device is selected.

        TestDevicesPageVerifier testDevicesPageVerifier = new TestDevicesPageVerifier(testDevicesPage);

        testDevicesPageVerifier.verifyAudioDeviceSelected();

        // Check that correct tooltip displayed for selected Audio Device and for the same Device in dropdown.

        testDevicesPageVerifier.verifySelectedAudioDeviceNameEqualsToTooltip();
        testDevicesPageVerifier.verifySelectedAudioDeviceNameEqualsToTooltipInDropdown();

        throwErrorIfVerificationsFailed();
    }
}
