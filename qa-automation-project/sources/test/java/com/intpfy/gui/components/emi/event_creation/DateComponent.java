package com.intpfy.gui.components.emi.event_creation;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public class DateComponent extends BaseComponent {

    @ElementInfo(name = "Event Date", findBy = @FindBy(name = "periodDate"))
    private Input dateInput;

    @ElementInfo(name = "Open date picker", findBy = @FindBy(css = "span.input-group-btn > button > i"))
    private Button openButton;

    @ElementInfo(name = "Calendar title", findBy = @FindBy(xpath = ".//button[contains(@id, 'datepicker-')]"))
    private Button calendarTitleButton;

    @ElementInfo(name = "Previous", findBy = @FindBy(css = "button.pull-left.uib-left"))
    private Button previousButton;

    @ElementInfo(name = "Next", findBy = @FindBy(css = "button.pull-right.uib-right"))
    private Button nextButton;

    @ElementInfo(name = "Calendar table", findBy = @FindBy(css = "table[role='grid']"))
    private Element calendarTableElement;

    public DateComponent(IParent parent) {
        super("Date picker", parent, By.xpath(".//input[@name='periodDate']/ancestor::div[1]"));
    }

    public LocalDate get() {
        return LocalDate.parse(dateInput.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public void select(LocalDate date) {

        open();
        waitTillOpened();

        selectYear(date.getYear());
        selectMonth(date.getMonth().name());
        selectDay(date.getDayOfMonth());
    }

    private void open() {
        openButton.clickable(AJAX_TIMEOUT);
        openButton.click();
    }

    private void waitTillOpened() {
        calendarTitleButton.visible(AJAX_TIMEOUT);
    }

    private void selectYear(int year) {
        calendarTitleButton.click();
        int yearInPicker;
        while ((yearInPicker = Integer.parseInt(calendarTitleButton.getText())) != year) {
            if (yearInPicker < year) {
                nextButton.click();
            } else {
                previousButton.click();
            }
        }
    }

    private void selectMonth(String monthName) {
        getMonthElement(monthName).click();
    }

    private Element getMonthElement(String elementText) {
        return getElement(getMonthElements(), elementText);
    }

    private List<Element> getMonthElements() {
        return new ArrayList<>(calendarTableElement.children(By.tagName("td")));
    }

    private void selectDay(int day) {
        getDayElement(String.valueOf(day)).click();
    }

    private Element getDayElement(String elementText) {
        return getElement(getDayElements(), elementText);
    }

    private List<Element> getDayElements() {
        return new ArrayList<>(calendarTableElement.children(By.cssSelector("td span:not(.text-muted)")));
    }

    private Element getElement(List<Element> elements, String elementText) {

        return elements.stream()
                .filter(e -> StringUtils.stripStart(e.getText(), "0").equalsIgnoreCase(elementText))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(elementText));
    }
}
