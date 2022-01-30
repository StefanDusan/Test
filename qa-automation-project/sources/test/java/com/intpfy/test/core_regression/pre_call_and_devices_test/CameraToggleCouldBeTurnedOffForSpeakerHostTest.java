package com.intpfy.test.core_regression.pre_call_and_devices_test;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.UsernamePage;
import com.intpfy.gui.pages.devices_test.TestDevicesPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.verifiers.devices_test.TestDevicesPageVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;
import static com.intpfy.util.RandomUtil.createRandomSpeakerName;

public class CameraToggleCouldBeTurnedOffForSpeakerHostTest extends BaseWebTest implements EventTest {

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
            testName = "[Pre-call Test] Check that camera toggle could be turned off for Speaker/Host",
            description = "Speaker / Host can turn Camera OFF.",
            groups = {
                    ONE_USER,
                    EVENT,
                    PRE_CALL_AND_DEVICES_TEST,
                    SPEAKER
            }
    )
    @TestRailCase("2256")
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

        // Wait until Camera Toggle available.

        TestDevicesPageVerifier testDevicesPageVerifier = new TestDevicesPageVerifier(testDevicesPage);

        testDevicesPageVerifier.assertCameraToggleAvailable();

        // Turn Camera OFF.

        testDevicesPage.turnCameraOff();
        testDevicesPageVerifier.assertCameraOff();

        // Join call.

        SpeakerPage speakerPage = testDevicesPage.joinCallAsSpeaker();
        speakerPage.assertIsOpened();

        // Check that Camera OFF.

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertCameraOff();

        throwErrorIfVerificationsFailed();
    }
}
