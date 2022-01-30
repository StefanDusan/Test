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

import static com.intpfy.gui.components.speaker.QuestionType.MultipleType;
import static com.intpfy.test.TestGroups.*;
import static com.intpfy.util.RandomUtil.getRandomNumericStringWithPrefix;

public class PollingMultipleTest extends BaseWebTest implements EventTest {

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
            testName = "Check Polling (Multiple)",
            description = "Host can organize Multiple Polling and Speaker can Vote.",
            groups = {
                    ONE_USER,
                    EVENT,
                    SPEAKER
            }
    )
    @TestRailCase("1746")
    public void test() {

        // Log in as Host with Meeting control only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Open 'Vote' tab and select question type: 'Multiple Type'.

        hostPage.openVoteTab();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.assertVoteTabOpened();

        hostPage.selectVoteQuestionType(MultipleType);

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

        speakerVerifier.verifyMultipleVoteAnswerAsSpeaker(1, firstAnswer);
        speakerVerifier.verifyMultipleVoteAnswerAsSpeaker(2, secondAnswer);

        // Select both Answers.

        speakerPage.selectMultipleVoteAnswers(firstAnswer, secondAnswer);

        speakerVerifier.assertMultipleVoteAnswerSelected(firstAnswer);
        speakerVerifier.assertMultipleVoteAnswerSelected(secondAnswer);

        // Vote and check that Vote accepted.

        speakerPage.vote();
        speakerVerifier.assertVoteAccepted();

        throwErrorIfVerificationsFailed();
    }
}
