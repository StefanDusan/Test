package com.intpfy.test.core_regression.keycloak.emi_with_multiple_organizations;

import com.intpfy.authorization.KeycloakAuthorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.KeycloakTest;
import com.intpfy.verifiers.emi.DashboardPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.KeycloakTest.*;
import static com.intpfy.test.TestGroups.*;

public class RefreshButtonDoesNotChangeSelectedOrganizationTest extends BaseWebTest implements KeycloakTest {

    private final KeycloakAuthorizer authorizer = KeycloakAuthorizer.getInstance();

    @Test(
            testName = "Verify that ”refresh” button does not change selected organization",
            description = "Selected Keycloak Organization does not change upon page refresh.",
            groups = {
                    ONE_USER,
                    EMI,
                    KEYCLOAK
            }
    )
    @TestRailCase("1968")
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

        // Select 1-st Keycloak Organization.

        String firstOrganization = getFirstKeycloakOrganization();

        dashboardPage.selectKeycloakOrganization(firstOrganization);
        dashboardPageVerifier.assertKeycloakOrganizationSelected(firstOrganization);

        // Refresh the page and check that selected Keycloak Organization not changed.

        dashboardPage.refresh();
        dashboardPageVerifier.assertKeycloakOrganizationSelected(firstOrganization);

        // Select 2-nd Keycloak Organization.

        String secondOrganization = getSecondKeycloakOrganization();

        dashboardPage.selectKeycloakOrganization(secondOrganization);
        dashboardPageVerifier.assertKeycloakOrganizationSelected(secondOrganization);

        // Refresh the page and check that selected Keycloak Organization not changed.

        dashboardPage.refresh();
        dashboardPageVerifier.assertKeycloakOrganizationSelected(secondOrganization);

        throwErrorIfVerificationsFailed();
    }
}
