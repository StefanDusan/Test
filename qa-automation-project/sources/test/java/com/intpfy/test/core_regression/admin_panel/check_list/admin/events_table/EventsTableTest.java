package com.intpfy.test.core_regression.admin_panel.check_list.admin.events_table;

import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.gui.pages.emi.EventsPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.UserUtil;
import com.intpfy.verifiers.emi.EventsPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.EMI;
import static com.intpfy.test.TestGroups.ONE_USER;

public class EventsTableTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create random event.
    private final Event event = Event
            .createRandomBuilder()
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check events table",
            description = "All 'Event' table columns displayed.",
            groups = {
                    ONE_USER,
                    EMI
            }
    )
    @TestRailCase("1500")
    public void test() {

        // Log in as Admin.

        DashboardPage dashboardPage = authorizer.logInToEMI(UserUtil.getAdminUser());
        dashboardPage.assertIsOpened();

        // Open 'Events' page.

        EventsPage eventsPage = dashboardPage.goToEventsPage();
        eventsPage.assertIsOpened();

        // Check that all 'Events' table columns displayed.

        EventsPageVerifier eventsPageVerifier = new EventsPageVerifier(eventsPage);

        eventsPageVerifier.assertExists(event);
        eventsPageVerifier.verifyDefaultTableColumns();

        throwErrorIfVerificationsFailed();
    }
}
