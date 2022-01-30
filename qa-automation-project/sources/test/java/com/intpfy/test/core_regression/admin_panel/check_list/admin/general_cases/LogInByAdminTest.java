package com.intpfy.test.core_regression.admin_panel.check_list.admin.general_cases;

import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.gui.pages.emi.event_creation.GeneralInfoPage;
import com.intpfy.test.BaseWebTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.UserUtil;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.EMI;
import static com.intpfy.test.TestGroups.ONE_USER;

public class LogInByAdminTest extends BaseWebTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    @Test(
            testName = "Check login by Admin",
            description = "Admin can Log in.",
            groups = {
                    ONE_USER,
                    EMI
            }
    )
    @TestRailCase("344")
    public void test() {

        // Log in as Admin.

        DashboardPage dashboardPage = authorizer.logInToEMI(UserUtil.getAdminUser());
        dashboardPage.assertIsOpened();

        // Open 'Add Event (General Info)' page.

        GeneralInfoPage addEventPage = dashboardPage.goToGeneralInfoPage();
        addEventPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
