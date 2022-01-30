package com.intpfy.test.core_regression.keycloak.emi_settings_with_whitelist_and_security_groups;

import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.gui.pages.keycloak.KeycloakPage;
import com.intpfy.model.user.OrganizationModel;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.KeycloakOrganizationTest;
import com.intpfy.user.KeycloakUser;
import com.intpfy.user.OrganizationUser;
import com.intpfy.verifiers.emi.DashboardPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.KeycloakTest.*;
import static com.intpfy.test.TestGroups.*;

public class LogInWithAccountFromSecurityGroupTest extends BaseWebTest implements KeycloakOrganizationTest {

    private OrganizationUser organizationUser;
    private OrganizationModel organizationModel;

    @Override
    public OrganizationUser getOrganizationUser() {

        if (organizationUser == null) {
            organizationUser = takeUser();
        }
        return organizationUser;
    }

    // Create Organization model with enabled Keycloak,
    // Allowlist containing Email approved by Keycloak (but not the same as will be used for Login)
    // and Security Group.
    @Override
    public OrganizationModel getOrganizationModel() {

        if (organizationModel == null) {

            organizationModel = new OrganizationModel.Builder()
                    .withLogin(getOrganizationUser().getEmail())
                    .withKeycloakEnabled(true)
                    .withSamlRequiredGroup(getSecurityGroup())
                    .withAllowlistEmails(getNotPartner().getEmail())
                    .build();
        }
        return organizationModel;
    }

    @Test(
            testName = "Log in with account from security group",
            description = "Keycloak user without Partner role but belonging to Security Group " +
                    "can Log in to EMI if Allowlist, containing Email different from user's, and Security Group added by Organization.",
            groups = {
                    ONE_USER,
                    EMI,
                    KEYCLOAK
            }
    )
    @TestRailCase("1848")
    public void test() {

        // Open 'Login' page on Organization domain.

        LoginPage loginPage = navigateToDomain(getDomain());
        loginPage.assertIsOpened();

        // Proceed to 'Keycloak' page as Admin.

        KeycloakPage keycloakPage = loginPage.proceedToKeycloakPageAsAdmin();
        keycloakPage.assertIsOpened();

        KeycloakUser notPartnerWithSecurityGroup = getNotPartnerWithSecurityGroup();

        DashboardPage dashboardPage = keycloakPage.signIn(notPartnerWithSecurityGroup);
        dashboardPage.assertIsOpened();

        // Check that user Email displayed.

        DashboardPageVerifier dashboardPageVerifier = new DashboardPageVerifier(dashboardPage);
        dashboardPageVerifier.assertEmailEquals(notPartnerWithSecurityGroup.getEmail());

        throwErrorIfVerificationsFailed();
    }
}
