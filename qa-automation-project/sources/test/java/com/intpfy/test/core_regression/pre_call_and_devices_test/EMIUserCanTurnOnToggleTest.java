package com.intpfy.test.core_regression.pre_call_and_devices_test;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.gui.pages.emi.EventsPage;
import com.intpfy.gui.pages.emi.event_creation.EditEventPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.util.UserUtil;
import com.intpfy.verifiers.emi.EventsPageVerifier;
import com.intpfy.verifiers.emi.event_creation.EditEventPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class EMIUserCanTurnOnToggleTest extends BaseWebTest implements EventTest {

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
            testName = "[Pre-call Test] Check that in EMI user can turn on toggle",
            description = "EMI user can enable Pre call test for Audience, Interpreter and Speaker.",
            groups = {
                    ONE_USER,
                    EMI,
                    PRE_CALL_AND_DEVICES_TEST
            }
    )
    @TestRailCase("2252")
    public void test() {

        // Log in to EMI as Organization.

        DashboardPage dashboardPage = authorizer.logInToEMI(UserUtil.getEventOrganizationUser());
        dashboardPage.assertIsOpened();

        // Proceed to 'Events' page.

        EventsPage eventsPage = dashboardPage.goToEventsPage();
        eventsPage.assertIsOpened();

        // Edit created earlier Event.

        EditEventPage editEventPage = eventsPage.edit(event);
        editEventPage.assertIsOpened();

        // Enable Pre call test for Audience.

        EditEventPageVerifier editEventPageVerifier = new EditEventPageVerifier(editEventPage);

        editEventPage.enablePreCallTestForAudience();
        editEventPageVerifier.assertPreCallTestEnabledForAudience();

        // Enable Pre call test for Interpreter and Speaker.

        editEventPage.enablePreCallTestForInterpreterAndSpeaker();
        editEventPageVerifier.assertPreCallTestEnabledForInterpreterAndSpeaker();

        // Save Event.

        eventsPage = editEventPage.save();
        eventsPage.assertIsOpened();

        // Check that Event displayed in 'Events' table.

        EventsPageVerifier eventsPageVerifier = new EventsPageVerifier(eventsPage);
        eventsPageVerifier.assertExists(event);

        throwErrorIfVerificationsFailed();
    }
}
