package com.intpfy.test.core_regression.keycloak.emi_with_multiple_organizations;

import com.intpfy.authorization.KeycloakAuthorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.KeycloakTest;
import com.intpfy.verifiers.emi.DashboardPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.KeycloakTest.getDomain;
import static com.intpfy.test.KeycloakTest.getPartnerWithMultipleKeycloakOrganizations;
import static com.intpfy.test.TestGroups.*;

public class YouCanSeeOtherOrganizationsWhenYouLogInTest extends BaseWebTest implements KeycloakTest {

    private final KeycloakAuthorizer authorizer = KeycloakAuthorizer.getInstance();

    @Test(
            testName = "Check that you can see other organizations when you log in",
            description = "Keycloak user having multiple Keycloak Organizations can see them in EMI.",
            groups = {
                    ONE_USER,
                    EMI,
                    KEYCLOAK
            }
    )
    @TestRailCase("1959")
    public void test() {

        // Open 'Login' page on Organization domain.

        LoginPage loginPage = navigateToDomain(getDomain());
        loginPage.assertIsOpened();

        // Sign in to EMI as user having multiple Keycloak Organizations.

        DashboardPage dashboardPage = authorizer.logInToEMI(getPartnerWithMultipleKeycloakOrganizations());
        dashboardPage.assertIsOpened();

        // Check that multiple Keycloak Organizations available.

        DashboardPageVerifier dashboardPageVerifier = new DashboardPageVerifier(dashboardPage);

        dashboardPageVerifier.assertMultipleKeycloakOrganizationsAvailable();

        // Check that Keycloak Organizations count > 1.

        dashboardPageVerifier.verifyKeycloakOrganizationsCountGreaterThanOne();

        throwErrorIfVerificationsFailed();
    }
}
