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

import static com.intpfy.test.KeycloakTest.getDomain;
import static com.intpfy.test.KeycloakTest.getNotPartner;
import static com.intpfy.test.TestGroups.*;

public class LogInWithAccountFromWhitelistTest extends BaseWebTest implements KeycloakOrganizationTest {

    private OrganizationUser organizationUser;
    private OrganizationModel organizationModel;

    private KeycloakUser notPartner;
    private String notPartnerEmail;

    @Override
    public OrganizationUser getOrganizationUser() {

        if (organizationUser == null) {
            organizationUser = takeUser();
        }
        return organizationUser;
    }

    // Create Organization model with enabled Keycloak,
    // Allowlist containing Email approved by Keycloak (the same as will be used for Login)
    // and no Security Group.
    @Override
    public OrganizationModel getOrganizationModel() {

        if (organizationModel == null) {

            notPartner = getNotPartner();
            notPartnerEmail = notPartner.getEmail();

            organizationModel = new OrganizationModel.Builder()
                    .withLogin(getOrganizationUser().getEmail())
                    .withKeycloakEnabled(true)
                    .withSamlRequiredGroup(null)
                    .withAllowlistEmails(notPartnerEmail)
                    .build();
        }
        return organizationModel;
    }

    @Test(
            testName = "Log in with account from whitelist",
            description = "Keycloak user without Partner role can Log in to EMI if Allowlist, containing user Email, added by Organization.",
            groups = {
                    ONE_USER,
                    EMI,
                    KEYCLOAK
            }
    )
    @TestRailCase("1838")
    public void test() {

        // Open 'Login' page on Organization domain.

        LoginPage loginPage = navigateToDomain(getDomain());
        loginPage.assertIsOpened();

        // Proceed to 'Keycloak' page as Admin.

        KeycloakPage keycloakPage = loginPage.proceedToKeycloakPageAsAdmin();
        keycloakPage.assertIsOpened();

        // Sign in to EMI as user without Partner role.

        DashboardPage dashboardPage = keycloakPage.signIn(notPartner);
        dashboardPage.assertIsOpened();

        // Check that user Email displayed.

        DashboardPageVerifier dashboardPageVerifier = new DashboardPageVerifier(dashboardPage);
        dashboardPageVerifier.assertEmailEquals(notPartnerEmail);

        throwErrorIfVerificationsFailed();
    }
}
