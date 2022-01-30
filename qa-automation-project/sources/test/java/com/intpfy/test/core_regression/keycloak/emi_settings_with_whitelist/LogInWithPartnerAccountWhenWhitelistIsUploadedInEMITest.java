package com.intpfy.test.core_regression.keycloak.emi_settings_with_whitelist;

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

public class LogInWithPartnerAccountWhenWhitelistIsUploadedInEMITest extends BaseWebTest implements KeycloakOrganizationTest {

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
    // and no Security Group.
    @Override
    public OrganizationModel getOrganizationModel() {

        if (organizationModel == null) {

            organizationModel = new OrganizationModel.Builder()
                    .withLogin(getOrganizationUser().getEmail())
                    .withKeycloakEnabled(true)
                    .withSamlRequiredGroup(null)
                    .withAllowlistEmails(getNotPartner().getEmail())
                    .build();
        }
        return organizationModel;
    }

    @Test(
            testName = "Log in with partner account when whitelist is uploaded in EMI",
            description = "Keycloak user with Partner role can Log in to EMI if Allowlist, containing Email different from user's, added by Organization.",
            groups = {
                    ONE_USER,
                    EMI,
                    KEYCLOAK
            }
    )
    @TestRailCase("1837")
    public void test() {

        // Open 'Login' page on Organization domain.

        LoginPage loginPage = navigateToDomain(getDomain());
        loginPage.assertIsOpened();

        // Proceed to 'Keycloak' page as Admin.

        KeycloakPage keycloakPage = loginPage.proceedToKeycloakPageAsAdmin();
        keycloakPage.assertIsOpened();

        // Sign in to EMI as user with Partner role.

        KeycloakUser partner = getPartner();

        DashboardPage dashboardPage = keycloakPage.signIn(partner);
        dashboardPage.assertIsOpened();

        // Check that user Email displayed.

        DashboardPageVerifier dashboardPageVerifier = new DashboardPageVerifier(dashboardPage);
        dashboardPageVerifier.assertEmailEquals(partner.getEmail());

        throwErrorIfVerificationsFailed();
    }
}
