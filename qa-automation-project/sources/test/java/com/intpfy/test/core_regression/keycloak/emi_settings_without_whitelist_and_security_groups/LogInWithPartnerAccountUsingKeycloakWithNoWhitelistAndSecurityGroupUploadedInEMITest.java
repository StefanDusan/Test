package com.intpfy.test.core_regression.keycloak.emi_settings_without_whitelist_and_security_groups;

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

import static com.intpfy.test.KeycloakTest.getDomain;
import static com.intpfy.test.KeycloakTest.getPartner;
import static com.intpfy.test.TestGroups.*;

public class LogInWithPartnerAccountUsingKeycloakWithNoWhitelistAndSecurityGroupUploadedInEMITest
        extends BaseWebTest implements KeycloakOrganizationTest {

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
    // no Allowlist and empty Security Group.
    @Override
    public OrganizationModel getOrganizationModel() {

        if (organizationModel == null) {

            organizationModel = new OrganizationModel.Builder()
                    .withLogin(getOrganizationUser().getEmail())
                    .withKeycloakEnabled(true)
                    .withSamlRequiredGroup(null)
                    .build();
        }
        return organizationModel;
    }

    @Test(
            testName = "Log in with partner account using keycloak with no whitelist and security group uploaded in EMI",
            description = "Keycloak user with Partner role can Log in to EMI if no Allowlist and Security Group added by Organization.",
            groups = {
                    ONE_USER,
                    EMI,
                    KEYCLOAK
            }
    )
    @TestRailCase("1834")
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
