package com.intpfy.gui.components.speaker;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfy.gui.complex_elements.selection.ComplexRadioButton;
import com.intpfyqa.gui.web.selenium.elements.util.ElementFromListMatcher;
import com.intpfyqa.gui.web.selenium.elements.util.matchers.ElementFromListMatcherByIndex;
import com.intpfyqa.gui.web.selenium.factories.WebFactoryHelper;
import org.openqa.selenium.By;

import java.time.Duration;

public class VoteOptionsComponent extends BaseComponent {

    public VoteOptionsComponent(IParent parent) {
        super("Vote options", parent, By.cssSelector("div.poll-vote__options"));
    }

    public String getSingleAnswer(int index) {
        ComplexRadioButton answerRadioButton = createAnswerRadioButton(index);
        answerRadioButton.visible();
        return answerRadioButton.getLabel();
    }

    public String getMultipleAnswer(int index) {
        ComplexCheckbox answerCheckbox = createAnswerCheckbox(index);
        answerCheckbox.visible();
        return answerCheckbox.getLabel();
    }

    public void selectSingleAnswer(String answer) {
        ComplexRadioButton answerRadioButton = createAnswerRadioButton(answer);
        answerRadioButton.visible();
        answerRadioButton.select();
    }

    public void selectMultipleAnswer(String answer) {
        ComplexCheckbox answerCheckbox = createAnswerCheckbox(answer);
        answerCheckbox.visible();
        answerCheckbox.select();
    }

    public void selectMultipleAnswers(String... answers) {
        for (String answer : answers) {
            selectMultipleAnswer(answer);
        }
    }

    public boolean isSingleAnswerSelected(String answer, Duration timeout) {
        ComplexRadioButton answerRadioButton = createAnswerRadioButton(answer);
        answerRadioButton.visible(timeout);
        return answerRadioButton.waitIsSelected(timeout);
    }

    public boolean isMultipleAnswerSelected(String answer, Duration timeout) {
        ComplexCheckbox answerCheckbox = createAnswerCheckbox(answer);
        answerCheckbox.visible(timeout);
        return answerCheckbox.waitIsSelected(timeout);
    }

    private ComplexRadioButton createAnswerRadioButton(String answer) {
        String name = String.format("'%s' answer radio button", answer);
        By selector = By.xpath(String.format(".//div[./input[@type = 'radio'] and ./label[text()='%s']]", answer));
        return WebFactoryHelper.getComponentFactory().createComponent(ComplexRadioButton.class, name, this, selector);
    }

    private ComplexRadioButton createAnswerRadioButton(int index) {
        String name = String.format("Answer №%s radio button", index);
        By selector = By.xpath(".//div[./input[@type = 'radio']]");
        ElementFromListMatcher matcher = new ElementFromListMatcherByIndex(index);
        return WebFactoryHelper.getComponentFactory().createComponent(ComplexRadioButton.class, name, this, selector, matcher);
    }

    private ComplexCheckbox createAnswerCheckbox(String answer) {
        String name = String.format("'%s' answer checkbox", answer);
        By selector = By.xpath(String.format(".//div[./input[@type = 'checkbox'] and ./label[text()='%s']]", answer));
        return WebFactoryHelper.getComponentFactory().createComponent(ComplexCheckbox.class, name, this, selector);
    }

    private ComplexCheckbox createAnswerCheckbox(int index) {
        String name = String.format("Answer №%s checkbox", index);
        By selector = By.xpath(".//div[./input[@type = 'checkbox']]");
        ElementFromListMatcher matcher = new ElementFromListMatcherByIndex(index);
        return WebFactoryHelper.getComponentFactory().createComponent(ComplexCheckbox.class, name, this, selector, matcher);
    }
}
