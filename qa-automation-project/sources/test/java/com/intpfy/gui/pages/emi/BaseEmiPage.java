package com.intpfy.gui.pages.emi;

import com.intpfy.gui.components.emi.common.LeftMenuComponent;
import com.intpfy.gui.pages.BaseAuthorizedPage;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.emi.event_creation.GeneralInfoPage;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public abstract class BaseEmiPage extends BaseAuthorizedPage {

    @ElementInfo(name = "Email", findBy = @FindBy(css = "span.username"))
    private Element emailElement;

    private final LeftMenuComponent leftMenuComponent;

    protected BaseEmiPage(String name, IPageContext pageContext) {
        super(name, pageContext);
        leftMenuComponent = new LeftMenuComponent(this);
    }

    public boolean isEmailEqual(String email, Duration timeout) {
        return emailElement.waitForTextEquals(email, timeout);
    }

    public DashboardPage goToDashboardPage() {
        info("Go to 'Dashboard' page.");
        return leftMenuComponent.goToMainPage();
    }

    public GeneralInfoPage goToGeneralInfoPage() {
        info("Go to 'General info' page.");
        return leftMenuComponent.goToAddEventPage();
    }

    public EventsPage goToEventsPage() {
        info("Go to 'Events' page.");
        return leftMenuComponent.goToEventsPage();
    }

    public CalendarPage goToCalendarPage() {
        info("Go to 'Calendar' page.");
        return leftMenuComponent.goToCalendarPage();
    }

    public StatisticsPage goToStatisticsPage() {
        info("Go to 'Statistics' page.");
        return leftMenuComponent.goToStatisticsPage();
    }

    public UsersPage goToUsersPage() {
        info("Go to 'Users' page.");
        return leftMenuComponent.goToUsersPage();
    }

    public ProfilePage goToProfilePage() {
        info("Go to 'Profile' page.");
        return leftMenuComponent.goToProfilePage();
    }

    @Override
    public LoginPage logOut() {
        info("Log out.");
        return leftMenuComponent.logOut();
    }
}
