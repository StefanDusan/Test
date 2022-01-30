package com.intpfy.test.core_regression.pre_call_and_devices_test;


import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.devices_test.*;
import com.intpfy.test.BaseWebTest;
import com.intpfy.verifiers.devices_test.DevicesTestVerifier;
import com.intpfy.verifiers.devices_test.FinishedPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.ONE_USER;
import static com.intpfy.test.TestGroups.PRE_CALL_AND_DEVICES_TEST;

public class TutorialForTheDevicesTestTest extends BaseWebTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    @Test(
            testName = "[Devices Test] Check the tutorial for the devices test",
            description = "User can proceed through 'Devices test' tutorial.",
            groups = {
                    ONE_USER,
                    PRE_CALL_AND_DEVICES_TEST
            }
    )
    @TestRailCase("2247")
    public void test() {

        // Open 'Login' page.

        LoginPage loginPage = authorizer.logOut();
        loginPage.assertIsOpened();

        // Proceed to 'Step by step' page.

        StepByStepPage stepByStepPage = loginPage.testDevices();
        stepByStepPage.assertIsOpened();

        // Check that 'Next' and 'Skip tutorial' buttons and 'Dots controls' visible.

        DevicesTestVerifier stepByStepPageVerifier = new DevicesTestVerifier(stepByStepPage);

        stepByStepPageVerifier.assertNextButtonVisible();
        stepByStepPageVerifier.assertSkipTutorialButtonVisible();
        stepByStepPageVerifier.assertDotsControlsVisible();

        // Proceed to 'Test your camera' page.

        TestYourCameraPage testYourCameraPage = stepByStepPage.next();
        testYourCameraPage.assertIsOpened();

        // Proceed to 'Test your microphone' page.

        TestYourMicrophonePage testYourMicrophonePage = testYourCameraPage.next();
        testYourMicrophonePage.assertIsOpened();

        // Proceed to 'Rehearse your voice (record)' page.

        RehearseYourVoiceRecordPage rehearseYourVoiceRecordPage = testYourMicrophonePage.next();
        rehearseYourVoiceRecordPage.assertIsOpened();

        // Proceed to 'Rehearse your voice (play)' page.

        RehearseYourVoicePlayPage rehearseYourVoicePlayPage = rehearseYourVoiceRecordPage.next();
        rehearseYourVoicePlayPage.assertIsOpened();

        // Proceed to 'Test your connection' page.

        TestYourConnectionPage testYourConnectionPage = rehearseYourVoicePlayPage.next();
        testYourConnectionPage.assertIsOpened();

        // Proceed to 'Finished' page.

        FinishedPage finishedPage = testYourConnectionPage.next();
        finishedPage.assertIsOpened();

        // Play Video.

        finishedPage.playVideo();

        FinishedPageVerifier finishedPageVerifier = new FinishedPageVerifier(finishedPage);
        finishedPageVerifier.assertVideoPlaying();

        // Proceed to 'Test devices' page.

        TestDevicesPage testDevicesPage = finishedPage.next();
        testDevicesPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
