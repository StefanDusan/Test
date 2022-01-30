package com.intpfy.test.core_regression.smoke_test._2fa_and_ad;

import com.intpfy.gui.pages.ActiveDirectoryPage;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.model.user.OrganizationModel;
import com.intpfy.test.ActiveDirectoryTest;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.OrganizationTest;
import com.intpfy.user.OrganizationUser;
import com.intpfy.util.RandomUtil;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.ActiveDirectoryTest.*;
import static com.intpfy.test.TestGroups.*;

public class ADUserWithRightPermissionsCanAccessAdminPanelTest extends BaseWebTest implements OrganizationTest, ActiveDirectoryTest {

    private OrganizationUser organizationUser;
    private OrganizationModel organizationModel;

    @Override
    public OrganizationUser getOrganizationUser() {

        if (organizationUser == null) {
            organizationUser = takeUser();
        }
        return organizationUser;
    }

    // Create Organization model with enabled AD authentication,
    // correct Security Group
    // and no Allowlist.
    @Override
    public OrganizationModel getOrganizationModel() {

        if (organizationModel == null) {

            organizationModel = new OrganizationModel.Builder()
                    .withLogin(getOrganizationUser().getEmail())
                    .withDomain(RandomUtil.createRandomDomainName())
                    .withSamlIdProviderCode(getIdentifier())
                    .withSamlRequiredGroup(getCorrectSecurityGroup())
                    .build();
        }
        return organizationModel;
    }

    @Test(
            testName = "Check AD user(partner) with right permissions can access admin panel",
            description = "AD user with correct Security Group can Log in to EMI.",
            groups = {
                    ONE_USER,
                    EMI,
                    ACTIVE_DIRECTORY
            }
    )
    @TestRailCase("827")
    public void test() {

        // Open 'Login' page on Organization domain.

        LoginPage loginPage = navigateToDomain(organizationModel.getDomain());
        loginPage.assertIsOpened();

        // Proceed to 'Active Directory' page as Admin.

        ActiveDirectoryPage activeDirectoryPage = loginPage.proceedToActiveDirectoryPageAsAdmin();
        activeDirectoryPage.assertIsOpened();

        // Log in to EMI as Organization.

        DashboardPage dashboardPage = activeDirectoryPage.logInAsOrganization(getEmailForLogin(), getPassword());
        dashboardPage.assertIsOpened();

        throwErrorIfVerificationsFailed();
    }
}
