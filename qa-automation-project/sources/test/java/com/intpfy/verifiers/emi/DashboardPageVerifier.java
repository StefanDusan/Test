package com.intpfy.verifiers.emi;

import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.model.dashboard.GeneralStats;

import static com.intpfy.verifiers.emi.DashboardPageVerificationMessages.*;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;
import static com.intpfyqa.test.Verify.*;

public class DashboardPageVerifier extends BaseEmiVerifier {

    private final DashboardPage page;

    public DashboardPageVerifier(DashboardPage page) {
        super(page);
        this.page = page;
    }

    public void assertMultipleKeycloakOrganizationsAvailable() {
        assertTrue(page.areMultipleKeycloakOrganizationsAvailable(AJAX_TIMEOUT), MULTIPLE_KEYCLOAK_ORGANIZATIONS_AVAILABLE);
    }

    public void assertMultipleKeycloakOrganizationsNotAvailable() {
        assertTrue(page.areMultipleKeycloakOrganizationsNotAvailable(AJAX_TIMEOUT), MULTIPLE_KEYCLOAK_ORGANIZATIONS_NOT_AVAILABLE);
    }

    public void verifyKeycloakOrganizationsCountGreaterThanOne() {
        verifyMoreThanOrEqualsTo(page.getKeycloakOrganizationsCount(), 2, KEYCLOAK_ORGANIZATIONS_COUNT_GREATER_THAN_ONE);
    }

    public void assertKeycloakOrganizationSelected(String organization) {
        assertTrue(page.isKeycloakOrganizationSelected(organization, AJAX_TIMEOUT), String.format(KEYCLOAK_ORGANIZATION_SELECTED, organization));
    }

    public void assertAtLeastOneGeneralStatsParamNotEquals(GeneralStats stats) {

        int eventsCount = stats.getEventsCount();

        if (page.isGeneralStatsEventsCountNotEqual(eventsCount, AJAX_TIMEOUT)) {
            assertTrue(true, String.format(EVENTS_COUNT_NOT_EQUALS, eventsCount));
            return;
        }

        int usersPerEventCount = stats.getUsersPerEventCount();

        if (page.isGeneralStatsUsersPerEventCountNotEqual(usersPerEventCount, AJAX_TIMEOUT)) {
            assertTrue(true, String.format(USERS_PER_EVENT_COUNT_NOT_EQUALS, usersPerEventCount));
            return;
        }

        int usersConnectedCount = stats.getUsersConnectedCount();

        if (page.isGeneralStatsUsersConnectedCountNotEqual(usersConnectedCount, AJAX_TIMEOUT)) {
            assertTrue(true, String.format(USERS_CONNECTED_COUNT_NOT_EQUALS, usersConnectedCount));
            return;
        }

        int languagesCount = stats.getLanguagesCount();

        if (page.isGeneralStatsLanguagesCountNotEqual(languagesCount, AJAX_TIMEOUT)) {
            assertTrue(true, String.format(LANGUAGES_COUNT_NOT_EQUALS, languagesCount));
            return;
        }

        int minutesConnectionPerUserCount = stats.getMinutesConnectionPerUserCount();

        if (page.isGeneralStatsMinutesConnectionPerUserCountNotEqual(minutesConnectionPerUserCount, AJAX_TIMEOUT)) {
            assertTrue(true, String.format(MINUTES_CONNECTION_PER_USER_COUNT_NOT_EQUALS, minutesConnectionPerUserCount));
            return;
        }

        assertFalse(true, String.format(ALL_GENERAL_STATS_PARAMS_EQUAL, stats));
    }
}
