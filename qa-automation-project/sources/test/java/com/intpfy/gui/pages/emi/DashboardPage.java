package com.intpfy.gui.pages.emi;

import com.intpfy.gui.components.emi.dashboard.GeneralStatsComponent;
import com.intpfy.gui.components.emi.dashboard.KeycloakOrganizationsComponent;
import com.intpfy.model.dashboard.GeneralStats;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class DashboardPage extends BaseEmiPage {

    @ElementInfo(name = "Page title", findBy = @FindBy(xpath = "//h1[text() = 'Dashboard']"))
    private Element pageTitle;

    private final KeycloakOrganizationsComponent keycloakOrganizations;
    private final GeneralStatsComponent generalStats;

    public DashboardPage(IPageContext pageContext) {
        super("Dashboard page", pageContext);
        keycloakOrganizations = new KeycloakOrganizationsComponent(this);
        generalStats = new GeneralStatsComponent(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return pageTitle.visible(timeout);
    }

    public boolean areMultipleKeycloakOrganizationsAvailable(Duration timeout) {
        return keycloakOrganizations.visible(timeout);
    }

    public boolean areMultipleKeycloakOrganizationsNotAvailable(Duration timeout) {
        return keycloakOrganizations.notVisible(timeout);
    }

    public int getKeycloakOrganizationsCount() {
        return keycloakOrganizations.getCount();
    }

    public void selectKeycloakOrganization(String organization) {
        info(String.format("Select Keycloak Organization '%s'.", organization));
        keycloakOrganizations.select(organization);
    }

    public boolean isKeycloakOrganizationSelected(String organization, Duration timeout) {
        return keycloakOrganizations.isSelected(organization, timeout);
    }

    public GeneralStats getGeneralStats() {

        return new GeneralStats.Builder()
                .withEventsCount(generalStats.getEventsCount())
                .withUsersPerEventCount(generalStats.getUsersPerEventCount())
                .withUsersConnectedCount(generalStats.getUsersConnectedCount())
                .withLanguagesCount(generalStats.getLanguagesCount())
                .withMinutesConnectionPerUserCount(generalStats.getMinutesConnectionPerUserCount())
                .build();
    }

    public boolean isGeneralStatsEventsCountNotEqual(int count, Duration timeout) {
        return generalStats.isEventsCountNotEqual(count, timeout);
    }

    public boolean isGeneralStatsUsersPerEventCountNotEqual(int count, Duration timeout) {
        return generalStats.isUsersPerEventCountNotEqual(count, timeout);
    }

    public boolean isGeneralStatsUsersConnectedCountNotEqual(int count, Duration timeout) {
        return generalStats.isUsersConnectedCountNotEqual(count, timeout);
    }

    public boolean isGeneralStatsLanguagesCountNotEqual(int count, Duration timeout) {
        return generalStats.isLanguagesCountNotEqual(count, timeout);
    }

    public boolean isGeneralStatsMinutesConnectionPerUserCountNotEqual(int count, Duration timeout) {
        return generalStats.isMinutesConnectionPerUserCountNotEqual(count, timeout);
    }
}
