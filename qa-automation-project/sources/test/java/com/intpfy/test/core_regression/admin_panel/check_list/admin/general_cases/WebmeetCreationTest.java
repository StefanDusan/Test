package com.intpfy.test.core_regression.admin_panel.check_list.admin.general_cases;

import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.gui.pages.emi.EventsPage;
import com.intpfy.gui.pages.emi.event_creation.GeneralInfoPage;
import com.intpfy.gui.pages.emi.event_creation.ManagerAndModeratorPage;
import com.intpfy.gui.pages.emi.event_creation.TokensAndNameCustomizationPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.UserUtil;
import com.intpfy.verifiers.emi.EventsPageVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.intpfy.test.TestGroups.EMI;
import static com.intpfy.test.TestGroups.ONE_USER;

public class WebmeetCreationTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect (WebMeet)' event model.
    private final Event event = Event
            .createConnectWebMeetBuilder()
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Override
    protected void customBeforeMethod(ITestContext context, Method method, Object[] params) {
        // To not create new Event.
    }

    @Test(
            testName = "Check Webmeet event creation",
            description = "'Connect (WebMeet)' event can be created.",
            groups = {
                    ONE_USER,
                    EMI
            }
    )
    @TestRailCase("1718")
    public void test() {

        // Log in as Organization.

        DashboardPage mainPage = authorizer.logInToEMI(UserUtil.getEventOrganizationUser());
        mainPage.assertIsOpened();

        // Open 'Add Event (General Info)' page.

        GeneralInfoPage generalInfoPage = mainPage.goToGeneralInfoPage();
        generalInfoPage.assertIsOpened();

        // Set necessary Event data.

        generalInfoPage.selectEventType(event.getEventType());
        generalInfoPage.setEventName(event.getName());
        generalInfoPage.selectDate(event.getStartDate().toLocalDate());
        generalInfoPage.selectLocation(event.getLocation());

        // Proceed to 'Add Event (Manager and Moderator)' page.

        ManagerAndModeratorPage addManagerPage = generalInfoPage.clickContinue();
        addManagerPage.assertIsOpened();

        // Proceed to 'Add Event (Tokens and name customization)' page.

        TokensAndNameCustomizationPage tokensAndNameCustomizationPage = addManagerPage.proceedToAddEventTokensPage();
        tokensAndNameCustomizationPage.assertIsOpened();

        // Save Event.

        mainPage = tokensAndNameCustomizationPage.save();
        mainPage.assertIsOpened();

        // Open 'Events' page.

        EventsPage eventsPage = mainPage.goToEventsPage();
        eventsPage.assertIsOpened();

        // Check that Event displayed in 'Events' table.

        EventsPageVerifier eventsPageVerifier = new EventsPageVerifier(eventsPage);
        eventsPageVerifier.assertExists(event);

        throwErrorIfVerificationsFailed();
    }
}
