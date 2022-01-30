package com.intpfy.test.core_regression.pre_call_and_devices_test;


import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.devices_test.StepByStepPage;
import com.intpfy.gui.pages.devices_test.TestDevicesPage;
import com.intpfy.test.BaseWebTest;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.ONE_USER;
import static com.intpfy.test.TestGroups.PRE_CALL_AND_DEVICES_TEST;

public class WorkOfSkipTutorialButtonForDevicesTestTest extends BaseWebTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    @Test(
            testName = "[Devices Test] Check the work of 'Skip tutorial' button for devices test",
            description = "User can proceed directly to 'Test devices' page by Skipping tutorial.",
            groups = {
                    ONE_USER,
                    PRE_CALL_AND_DEVICES_TEST
            }
    )
    @TestRailCase("2248")
    public void test() {

        // Open 'Login' page.

        LoginPage loginPage = authorizer.logOut();
        loginPage.assertIsOpened();

        // Proceed to 'Step by step' page.

        StepByStepPage stepByStepPage = loginPage.testDevices();
        stepByStepPage.assertIsOpened();

        // Skip tutorial and check that 'Test devices' page opened.

        TestDevicesPage testDevicesPage = stepByStepPage.skipTutorial();
        testDevicesPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
