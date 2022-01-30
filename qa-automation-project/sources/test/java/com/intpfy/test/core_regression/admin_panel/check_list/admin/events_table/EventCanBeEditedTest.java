package com.intpfy.test.core_regression.admin_panel.check_list.admin.events_table;

import com.intpfy.gui.dialogs.common.AccessChangeDW;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.gui.pages.emi.EventsPage;
import com.intpfy.gui.pages.emi.event_creation.EditEventPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.EventPeriodsData;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.UserUtil;
import com.intpfy.verifiers.emi.EventsPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.intpfy.test.TestGroups.EMI;
import static com.intpfy.test.TestGroups.ONE_USER;

public class EventCanBeEditedTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create random event.
    private Event event = Event
            .createRandomBuilder()
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that Event can be edited",
            description = "Event can be edited.",
            groups = {
                    ONE_USER,
                    EMI
            }
    )
    @TestRailCase("162")
    public void test() {

        // Log in as Organization.

        DashboardPage dashboardPage = authorizer.logInToEMI(UserUtil.getEventOrganizationUser());
        dashboardPage.assertIsOpened();

        // Proceed to 'Events' page.

        EventsPage eventsPage = dashboardPage.goToEventsPage();
        eventsPage.assertIsOpened();

        // Open 'Edit Event' page.

        EditEventPage editPage = eventsPage.edit(event);
        editPage.assertIsOpened();

        // Create new data to edit Event.

        String eventType = event.getEventType().name();

        String newName = RandomUtil.getRandomNumericStringWithPrefix(eventType);
        String newNameToDisplay = RandomUtil.getRandomNumericStringWithPrefix(eventType);
        Language newLanguage = Language.getRandomLanguage();
        LocalTime newStartTime = editPage.getStartTime().plusHours(1);
        LocalTime newEndTime = editPage.getEndTime().plusHours(1);
        LocalDate newStartDate = editPage.getDate()
                .plus(1, ChronoUnit.DAYS)
                .plus(1, ChronoUnit.MONTHS)
                .plus(1, ChronoUnit.YEARS);
        boolean audienceAccessBlocked = true;

        // Edit Event with new data.

        editPage.setEventName(newName);
        editPage.setNameToDisplay(newNameToDisplay);
        editPage.selectLanguage(newLanguage);
        editPage.selectStartTime(newStartTime);
        editPage.selectEndTime(newEndTime);
        editPage.selectDate(newStartDate);

        // Block Audience access.

        AccessChangeDW accessChangeDW = editPage.blockAudienceAccess();
        accessChangeDW.assertIsOpened();

        accessChangeDW.confirm();

        accessChangeDW.assertNotVisible();

        // Save Event.

        eventsPage = editPage.save();
        eventsPage.assertIsOpened();

        // Create Event model with new data.

        event = new Event.Builder()
                .withName(newName)
                .withDisplayName(newNameToDisplay)
                .withLanguage(newLanguage)
                .withEventPeriodsData(List.of(new EventPeriodsData(newStartDate.atTime(newStartTime), newStartDate.atTime(newEndTime))))
                .withAudienceBlocked(audienceAccessBlocked)
                .build();

        // Check that new Event data displayed in 'Events' table.

        EventsPageVerifier eventsPageVerifier = new EventsPageVerifier(eventsPage);
        eventsPageVerifier.verifyData(event);

        throwErrorIfVerificationsFailed();
    }
}
