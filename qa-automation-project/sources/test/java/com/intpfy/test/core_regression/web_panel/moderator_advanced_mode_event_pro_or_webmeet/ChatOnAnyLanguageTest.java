package com.intpfy.test.core_regression.web_panel.moderator_advanced_mode_event_pro_or_webmeet;

import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.pages.event.AdvancedModeratorPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.moderator.AdvancedModeratorVerifier;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.test.TestGroups.*;

public class ChatOnAnyLanguageTest extends BaseWebTest implements EventTest {

    private static final String PARTNER_CHAT_SESSION_TITLE_PATTERN = "%s chat session";

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Language language = Language.getRandomLanguage();

    // Create random event with 1 Language.
    private final Event event = Event
            .createRandomBuilder()
            .withLanguage(language)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check the chat on any language",
            description = "Moderator can hide / show Partner Chat and open it in dialog window.",
            groups = {
                    ONE_USER,
                    EVENT,
                    CHATS,
                    MODERATOR
            }
    )
    @TestRailCase("1551")
    @TestRailBugId("CORE-5101")
    public void test() {

        // Log in as Moderator with Advanced monitoring.

        String moderatorName = RandomUtil.createRandomModeratorName();
        AdvancedModeratorPage moderatorPage = authorizer.logInAsModeratorWithAdvancedMonitoring(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Hide Partner chat.

        moderatorPage.hidePartnerChat(language);

        AdvancedModeratorVerifier moderatorVerifier = new AdvancedModeratorVerifier(moderatorPage);

        moderatorVerifier.assertPartnerChatHidden(language);

        // Show Partner chat.

        moderatorPage.showPartnerChat(language);
        moderatorVerifier.assertPartnerChatShown(language);

        // Open Partner chat in dialog window.

        ChatDW partnerChatDW = moderatorPage.openPartnerChat(language);
        partnerChatDW.assertIsOpened();

        // Check dialog window title.

        moderatorVerifier.assertPartnerChatSessionTitle(partnerChatDW, String.format(PARTNER_CHAT_SESSION_TITLE_PATTERN, language));

        partnerChatDW.close();
        partnerChatDW.assertNotVisible();

        throwErrorIfVerificationsFailed();
    }
}
