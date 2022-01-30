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

public class WorkOfBackButtonOnDevicesTestPageTest extends BaseWebTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    @Test(
            testName = "[Devices Test] Check the work of 'Back' button on devices test page",
            description = "User can return to 'Login' page by clicking 'Back' button on 'Devices test' page.",
            groups = {
                    ONE_USER,
                    PRE_CALL_AND_DEVICES_TEST
            }
    )
    @TestRailCase("2251")
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

        // Click on 'Back' button and check that 'Login' page opened.

        loginPage = testDevicesPage.back();
        loginPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
