package com.intpfy.gui.components.speaker;

import com.intpfy.gui.complex_elements.PageDropdown;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.Input;
import com.intpfyqa.gui.web.selenium.factories.WebFactoryHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class VoteComponent extends BaseComponent {

    @ElementInfo(name = "Question type dropdown", findBy = @FindBy(css = "div.ng-select-container"))
    private PageDropdown questionTypeDropdown;

    @ElementInfo(name = "Type your question", findBy = @FindBy(css = "input[placeholder='Type your question']"))
    private Input questionInput;

    @ElementInfo(name = "Done", findBy = @FindBy(xpath = ".//button[contains(translate(text(),'DONE', 'done'), 'done')]"))
    private Button doneButton;

    @ElementInfo(name = "Cancel", findBy = @FindBy(xpath = ".//button[contains(translate(text(),'CANCEL', 'cancel'), 'cancel')]"))
    private Button cancelButton;

    @ElementInfo(name = "Accept", findBy = @FindBy(xpath = ".//button[contains(translate(text(),'ACCEPT', 'accept'), 'accept')]"))
    private Button acceptButton;

    @ElementInfo(name = "Stop", findBy = @FindBy(xpath = ".//button[contains(translate(text(),'STOP', 'stop'), 'stop')]"))
    private Button stopButton;

    @ElementInfo(name = "Vote", findBy = @FindBy(xpath = ".//button[contains(translate(text(),'VOTE', 'vote'), 'vote')]"))
    private Button voteButton;

    @ElementInfo(name = "Results", findBy = @FindBy(xpath = ".//button[contains(translate(text(),'RESULTS', 'results'), 'results')]"))
    private Button resultsButton;

    @ElementInfo(name = "Voting in progress", findBy =
    @FindBy(xpath = ".//h4[contains(translate(text(),'VOTING IN PROGRESS', 'voting in progress'), 'voting in progress...')]"))
    private Element votingInProgressElement;

    @ElementInfo(name = "Question", findBy = @FindBy(css = "div.poll-vote__question"))
    private Element questionElement;

    @ElementInfo(name = "Thank you for voting", findBy =
    @FindBy(xpath = ".//div[contains(translate(text(),'THANK YOU FOR VOTING', 'thank you for voting'), 'thank you for voting.')]"))
    private Element thankYouForVotingElement;

    private final VoteOptionsComponent voteOptions;

    public VoteComponent(IParent parent) {
        super("Vote", parent, By.cssSelector("div.poll-container"));
        voteOptions = new VoteOptionsComponent(this);
    }

    public void selectQuestionType(QuestionType type) {
        questionTypeDropdown.selectWithAssertion(type.getType());
    }

    public String getQuestion() {
        questionElement.visible();
        return questionElement.getText();
    }

    public void setQuestion(String question) {
        questionInput.visible();
        questionInput.setText(question);
    }

    public String getAnswerAsHost(int index) {
        String name = "Answer №: " + index;
        By selector = By.xpath(String.format(".//ul[@class='voting-results__choices']//span[text()='%s:']/following-sibling::span", index));
        Element answerElement = WebFactoryHelper.getElementFactory().createElement(Element.class, name, this, selector);
        answerElement.visible();
        return answerElement.getText();
    }

    public String getSingleAnswerAsSpeaker(int index) {
        return voteOptions.getSingleAnswer(index);
    }

    public String getMultipleAnswerAsSpeaker(int index) {
        return voteOptions.getMultipleAnswer(index);
    }

    public void setAnswer(String answer, int index) {
        String name = "Answer №: " + index;
        By selector = By.xpath(String.format(".//div[@class='form-group']/div[contains(@class, 'poll-create__answer-option')][%s]/input", index));
        Input answerInput = WebFactoryHelper.getElementFactory().createElement(Input.class, name, this, selector);
        answerInput.visible();
        answerInput.setText(answer);
    }

    public void selectSingleAnswer(String answer) {
        voteOptions.selectSingleAnswer(answer);
    }

    public void selectMultipleAnswers(String... answers) {
        voteOptions.selectMultipleAnswers(answers);
    }

    public boolean isSingleAnswerSelected(String answer, Duration timeout) {
        return voteOptions.isSingleAnswerSelected(answer, timeout);
    }

    public boolean isMultipleAnswerSelected(String answer, Duration timeout) {
        return voteOptions.isMultipleAnswerSelected(answer, timeout);
    }

    public boolean isInProgress(Duration timeout) {
        return votingInProgressElement.visible(timeout) && stopButton.visible(timeout);
    }

    public boolean isVoteAccepted(Duration timeout) {
        return thankYouForVotingElement.visible(timeout) && resultsButton.visible(timeout);
    }

    public void done() {
        doneButton.visible();
        doneButton.click();
    }

    public void cancel() {
        cancelButton.visible();
        cancelButton.click();
    }

    public void accept() {
        acceptButton.visible();
        acceptButton.click();
    }

    public void stop() {
        stopButton.visible();
        stopButton.click();
    }

    public void vote() {
        voteButton.visible();
        voteButton.click();
    }
}
