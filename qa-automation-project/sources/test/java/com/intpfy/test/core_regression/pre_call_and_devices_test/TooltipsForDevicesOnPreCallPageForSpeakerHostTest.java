package com.intpfy.test.core_regression.pre_call_and_devices_test;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.UsernamePage;
import com.intpfy.gui.pages.devices_test.TestDevicesPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.verifiers.devices_test.TestDevicesPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;
import static com.intpfy.util.RandomUtil.createRandomSpeakerName;

public class TooltipsForDevicesOnPreCallPageForSpeakerHostTest extends BaseWebTest implements EventTest {

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
            testName = "[Pre-call Test] Check the tooltips for devices on precall page for Speaker/Host",
            description = "Tooltips displayed for Camera, Microphone and Audio device on Speaker / Host 'Pre call test' page.",
            groups = {
                    ONE_USER,
                    EVENT,
                    PRE_CALL_AND_DEVICES_TEST,
                    SPEAKER
            }
    )
    @TestRailCase("2255")
    public void test() {

        // Open 'Login' page.

        LoginPage loginPage = authorizer.logOut();
        loginPage.assertIsOpened();

        // Proceed to 'Username' page with Speaker token.

        UsernamePage usernamePage = loginPage.proceedToUsernamePage(event.getSpeakerToken());
        usernamePage.assertIsOpened();

        // Log in to 'Test devices' page.

        TestDevicesPage testDevicesPage = usernamePage.logInWithPreCallTest(createRandomSpeakerName());
        testDevicesPage.assertIsOpened();

        // Wait until Camera Toggle is available.

        TestDevicesPageVerifier testDevicesPageVerifier = new TestDevicesPageVerifier(testDevicesPage);

        testDevicesPageVerifier.verifyCameraToggleAvailable();

        // Check that correct tooltip displayed for selected Camera and for the same Camera in dropdown.

        testDevicesPageVerifier.verifySelectedCameraNameEqualsToTooltip();
        testDevicesPageVerifier.verifySelectedCameraNameEqualsToTooltipInDropdown();

        // Wait until Microphone Toggle is available.

        testDevicesPageVerifier.verifyMicrophoneToggleAvailable();

        // Check that correct tooltip displayed for selected Microphone and for the same Microphone in dropdown.

        testDevicesPageVerifier.verifySelectedMicrophoneNameEqualsToTooltip();
        testDevicesPageVerifier.verifySelectedMicrophoneNameEqualsToTooltipInDropdown();

        // Wait until Audio device is selected.

        testDevicesPageVerifier.verifyAudioDeviceSelected();

        // Check that correct tooltip displayed for selected Audio Device and for the same Device in dropdown.

        testDevicesPageVerifier.verifySelectedAudioDeviceNameEqualsToTooltip();
        testDevicesPageVerifier.verifySelectedAudioDeviceNameEqualsToTooltipInDropdown();

        throwErrorIfVerificationsFailed();
    }
}
