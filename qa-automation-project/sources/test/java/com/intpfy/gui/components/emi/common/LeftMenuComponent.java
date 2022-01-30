package com.intpfy.gui.components.emi.common;

import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.emi.*;
import com.intpfy.gui.pages.emi.event_creation.GeneralInfoPage;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class LeftMenuComponent extends BaseComponent {

    @ElementInfo(name = "Dashboard", findBy = @FindBy(xpath = ".//li[.//span[text() = 'Dashboard']]"))
    private Button dashboardButton;

    @ElementInfo(name = "New event", findBy = @FindBy(xpath = ".//li[.//span[text() = 'New Event']]"))
    private Button newEventButton;

    @ElementInfo(name = "Events", findBy = @FindBy(xpath = ".//li[.//span[text() = 'Events']]"))
    private Button eventsButton;

    @ElementInfo(name = "Calendar", findBy = @FindBy(xpath = ".//li[.//span[text() = 'Calendar']]"))
    private Button calendarButton;

    @ElementInfo(name = "Statistics", findBy = @FindBy(xpath = ".//li[.//span[text() = 'Statistics']]"))
    private Button statisticsButton;

    @ElementInfo(name = "Users", findBy = @FindBy(xpath = ".//li[.//span[text() = 'Users']]"))
    private Button usersButton;

    @ElementInfo(name = "Profile", findBy = @FindBy(xpath = ".//li[.//span[text() = 'Profile']]"))
    private Button profileButton;

    @ElementInfo(name = "Log out", findBy = @FindBy(xpath = ".//li[.//span[text() = 'Logout']]"))
    private Button logOutButton;

    public LeftMenuComponent(IParent parent) {
        super("Left menu", parent, By.cssSelector("ul.page-sidebar-menu"));
    }

    public DashboardPage goToMainPage() {
        clickIfNotSelected(dashboardButton);
        return new DashboardPage(getPage().getPageContext());
    }

    public GeneralInfoPage goToAddEventPage() {
        clickIfNotSelected(newEventButton);
        return new GeneralInfoPage(getPage().getPageContext());
    }

    public EventsPage goToEventsPage() {
        clickIfNotSelected(eventsButton);
        return new EventsPage(getPage().getPageContext());
    }

    public CalendarPage goToCalendarPage() {
        clickIfNotSelected(calendarButton);
        return new CalendarPage(getPage().getPageContext());
    }

    public StatisticsPage goToStatisticsPage() {
        clickIfNotSelected(statisticsButton);
        return new StatisticsPage(getPage().getPageContext());
    }

    public UsersPage goToUsersPage() {
        clickIfNotSelected(usersButton);
        return new UsersPage(getPage().getPageContext());
    }

    public ProfilePage goToProfilePage() {
        clickIfNotSelected(profileButton);
        return new ProfilePage(getPage().getPageContext());
    }

    private void clickIfNotSelected(Button button) {
        if (!isSelected(button, Duration.ZERO)) {
            button.scrollIntoView();
            button.click();
            isSelected(button, WebSettings.AJAX_TIMEOUT);
        }
    }

    private boolean isSelected(Button button, Duration timeout) {
        return button.waitCssClassContains("active", timeout);
    }

    public LoginPage logOut() {
        logOutButton.click();
        return new LoginPage(getPage().getPageContext());
    }
}
