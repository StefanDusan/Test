package com.intpfy.test.core_regression.pre_call_and_devices_test;


import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.devices_test.StepByStepPage;
import com.intpfy.gui.pages.devices_test.TestDevicesPage;
import com.intpfy.test.BaseWebTest;
import com.intpfy.verifiers.devices_test.TestDevicesPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.ONE_USER;
import static com.intpfy.test.TestGroups.PRE_CALL_AND_DEVICES_TEST;

public class DevicesTestHasPassedSuccessfullyTest extends BaseWebTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    @Test(
            testName = "[Devices Test] Check the devices test has passed successfully",
            description = "Devices test results displayed on 'Test devices' page.",
            groups = {
                    ONE_USER,
                    PRE_CALL_AND_DEVICES_TEST
            }
    )
    @TestRailCase("2250")
    public void test() {

        // Open 'Login' page.

        LoginPage loginPage = authorizer.logOut();
        loginPage.assertIsOpened();

        // Proceed to 'Step by step' page.

        StepByStepPage stepByStepPage = loginPage.testDevices();
        stepByStepPage.assertIsOpened();

        // Skip tutorial.

        TestDevicesPage testDevicesPage = stepByStepPage.skipTutorial();
        testDevicesPage.assertIsOpened();

        // Check that Connection test started and finished after some time.

        TestDevicesPageVerifier testDevicesPageVerifier = new TestDevicesPageVerifier(testDevicesPage);

        testDevicesPageVerifier.assertConnectionTestGoing();
        testDevicesPageVerifier.assertConnectionTestFinished();

        // Check that Video, Microphone, Speaker and Network test results available.

        testDevicesPageVerifier.assertVideoConnectionTestResultAvailable();
        testDevicesPageVerifier.assertMicrophoneConnectionTestResultAvailable();
        testDevicesPageVerifier.assertSpeakerConnectionTestResultAvailable();
        testDevicesPageVerifier.assertNetworkConnectionTestResultAvailable();

        throwErrorIfVerificationsFailed();
    }
}
