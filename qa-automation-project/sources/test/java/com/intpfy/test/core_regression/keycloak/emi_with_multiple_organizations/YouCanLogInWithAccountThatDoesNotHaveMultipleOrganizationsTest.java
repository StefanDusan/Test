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
import static com.intpfy.test.KeycloakTest.getPartner;
import static com.intpfy.test.TestGroups.*;

public class YouCanLogInWithAccountThatDoesNotHaveMultipleOrganizationsTest extends BaseWebTest implements KeycloakTest {

    private final KeycloakAuthorizer authorizer = KeycloakAuthorizer.getInstance();

    @Test(
            testName = "Verify you can log in with account that does not have multiple organizations",
            description = "Multiple Keycloak Organizations are not available for Keycloak user having only 1 Organization.",
            groups = {
                    ONE_USER,
                    EMI,
                    KEYCLOAK
            }
    )
    @TestRailCase("1970")
    public void test() {

        // Open 'Login' page on Organization domain.

        LoginPage loginPage = navigateToDomain(getDomain());
        loginPage.assertIsOpened();

        // Sign in to EMI as user having only 1 Keycloak Organization.

        DashboardPage dashboardPage = authorizer.logInToEMI(getPartner());
        dashboardPage.assertIsOpened();

        // Check that multiple Keycloak Organizations not available.

        DashboardPageVerifier dashboardPageVerifier = new DashboardPageVerifier(dashboardPage);
        dashboardPageVerifier.assertMultipleKeycloakOrganizationsNotAvailable();

        throwErrorIfVerificationsFailed();
    }
}
