package com.intpfy.test.core_regression.pre_call_and_devices_test;


import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.devices_test.StepByStepPage;
import com.intpfy.gui.pages.devices_test.TestYourConnectionPage;
import com.intpfy.test.BaseWebTest;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.ONE_USER;
import static com.intpfy.test.TestGroups.PRE_CALL_AND_DEVICES_TEST;

public class WorkOfPageCountersForDevicesTestTest extends BaseWebTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    @Test(
            testName = "[Devices Test] Check the work of page counters for devices test",
            description = "User can open any 'Devices test' tutorial page using page counter (dots).",
            groups = {
                    ONE_USER,
                    PRE_CALL_AND_DEVICES_TEST
            }
    )
    @TestRailCase("2249")
    public void test() {

        // Open 'Login' page.

        LoginPage loginPage = authorizer.logOut();
        loginPage.assertIsOpened();

        // Proceed to 'Step by step' page.

        StepByStepPage stepByStepPage = loginPage.testDevices();
        stepByStepPage.assertIsOpened();

        // Open 'Test your connection' page using page counter.

        TestYourConnectionPage testYourConnectionPage = stepByStepPage.openTestYourConnectionPageByDot();
        testYourConnectionPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
