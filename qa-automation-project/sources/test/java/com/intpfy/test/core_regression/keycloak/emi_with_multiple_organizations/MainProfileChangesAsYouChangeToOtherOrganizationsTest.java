package com.intpfy.test.core_regression.keycloak.emi_with_multiple_organizations;

import com.intpfy.authorization.KeycloakAuthorizer;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.gui.pages.emi.ProfilePage;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.KeycloakTest;
import com.intpfy.verifiers.emi.DashboardPageVerifier;
import com.intpfy.verifiers.emi.ProfilePageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.KeycloakTest.*;
import static com.intpfy.test.TestGroups.*;

public class MainProfileChangesAsYouChangeToOtherOrganizationsTest extends BaseWebTest implements KeycloakTest {

    private final KeycloakAuthorizer authorizer = KeycloakAuthorizer.getInstance();

    @Test(
            testName = "Main profile changes as you change to other organizations test",
            description = "Keycloak user Profile changes upon changing Keycloak Organization.",
            groups = {
                    ONE_USER,
                    EMI,
                    KEYCLOAK
            }
    )
    @TestRailCase("1963")
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

        // Open 'Profile' page and get Profile data Email.

        ProfilePage profilePage = dashboardPage.goToProfilePage();
        profilePage.assertIsOpened();

        String firstOrganizationProfileDataEmail = profilePage.getProfileDataEmail();

        // Open 'Dashboard' page.

        dashboardPage = profilePage.goToDashboardPage();
        dashboardPage.assertIsOpened();

        // Select 2-nd Keycloak Organization.

        String secondOrganization = getSecondKeycloakOrganization();

        dashboardPage.selectKeycloakOrganization(secondOrganization);
        dashboardPageVerifier.assertKeycloakOrganizationSelected(secondOrganization);

        // Open 'Profile' page.

        profilePage = dashboardPage.goToProfilePage();
        profilePage.assertIsOpened();

        // Check that Profile data Email changed.

        ProfilePageVerifier profilePageVerifier = new ProfilePageVerifier(profilePage);
        profilePageVerifier.assertProfileDataEmailNotEquals(firstOrganizationProfileDataEmail);

        throwErrorIfVerificationsFailed();
    }
}