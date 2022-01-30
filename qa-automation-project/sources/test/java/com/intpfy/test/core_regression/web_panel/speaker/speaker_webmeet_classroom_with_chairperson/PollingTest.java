package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;


import com.intpfy.gui.dialogs.speaker.StartVotingDW;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.authorization.Authorizer;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import static com.intpfy.gui.components.speaker.QuestionType.SingleType;
import static com.intpfy.test.TestGroups.*;
import static com.intpfy.util.RandomUtil.getRandomNumericStringWithPrefix;

public class PollingTest extends BaseWebTest implements EventTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    // Create 'Connect Pro (Classroom)' event with enabled Polling.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .withEnablePolling(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check Polling (Single)",
            description = "Host can organize Single Polling and Speaker can Vote.",
            groups = {
                    ONE_USER,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("1562")
    public void test() {

        // Log in as Host with Meeting control only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Open 'Vote' tab and select question type: 'Single Type'.

        hostPage.openVoteTab();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.assertVoteTabOpened();

        hostPage.selectVoteQuestionType(SingleType);

        // Set Vote Question and 2 Answers.

        String question = getRandomNumericStringWithPrefix("Question_");

        String firstAnswer = getRandomNumericStringWithPrefix("Answer_1_");
        String secondAnswer = getRandomNumericStringWithPrefix("Answer_2_");

        hostPage.setVoteQuestion(question);

        hostPage.setVoteAnswer(firstAnswer, 1);
        hostPage.setVoteAnswer(secondAnswer, 2);

        // Start Vote.

        StartVotingDW startVotingDW = hostPage.startVote();
        startVotingDW.assertIsOpened();

        startVotingDW.start();
        startVotingDW.assertNotVisible();

        // Check that Vote in progress and Question and Answers displayed.

        hostVerifier.verifyVoteInProgress();

        hostVerifier.verifyVoteQuestion(question);

        hostVerifier.verifyVoteAnswerAsHost(1, firstAnswer);
        hostVerifier.verifyVoteAnswerAsHost(2, secondAnswer);

        // Log out and Log in as Speaker.

        LoginPage loginPage = hostPage.logOut();
        loginPage.assertIsOpened();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Open 'Vote' tab.

        speakerPage.openVoteTab();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);

        speakerVerifier.assertVoteTabOpened();

        // Accept Vote and check that Question and Answers displayed.

        speakerPage.acceptVote();

        speakerVerifier.verifyVoteQuestion(question);

        speakerVerifier.verifySingleVoteAnswerAsSpeaker(1, firstAnswer);
        speakerVerifier.verifySingleVoteAnswerAsSpeaker(2, secondAnswer);

        // Select Answer 1.

        speakerPage.selectSingleVoteAnswer(firstAnswer);
        speakerVerifier.assertSingleVoteAnswerSelected(firstAnswer);

        // Vote and check that Vote accepted.

        speakerPage.vote();
        speakerVerifier.assertVoteAccepted();

        throwErrorIfVerificationsFailed();
    }
}
