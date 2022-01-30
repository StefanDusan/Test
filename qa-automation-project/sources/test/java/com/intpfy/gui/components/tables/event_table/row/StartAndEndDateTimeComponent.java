package com.intpfy.gui.components.tables.event_table.row;

import com.intpfy.model.event.Event;
import com.intpfy.model.event.EventPeriodsData;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.annotations.Matcher;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.matchers.ElementFromListMatcherByIndex;
import com.intpfyqa.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.function.Supplier;

public class StartAndEndDateTimeComponent extends BaseComponent {

    @ElementInfo(name = "Start date and time", findBy = @FindBy(tagName = "div"), matcher =
    @Matcher(clazz = ElementFromListMatcherByIndex.class, args = "1"))
    private Element startDateAndTimeElement;

    @ElementInfo(name = "End date and time", findBy = @FindBy(tagName = "div"), matcher =
    @Matcher(clazz = ElementFromListMatcherByIndex.class, args = "2"))
    private Element endDateAndTimeElement;

    public StartAndEndDateTimeComponent(IParent parent) {
        super("Start and end date time", parent, By.cssSelector("td[title=\"'Start and End Time'\"]"));
    }

    public LocalTime getStartTime() {
        String time = startDateAndTimeElement.getText().split(" ")[1];
        return LocalTime.parse(time);
    }

    public LocalTime getEndTime() {
        String time = endDateAndTimeElement.getText().split(" ")[1];
        return LocalTime.parse(time);
    }

    public LocalDate getStartDate() {
        String date = startDateAndTimeElement.getText().split(", ")[0];
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public LocalDate getEndDate() {
        String date = endDateAndTimeElement.getText().split(", ")[0];
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public boolean isStartTimeActual(Event event, Duration timeout) {
        return isActual(this::getStartTime, event.getEventPeriodsData().get(0).getDateStart().toLocalTime(), timeout);
    }

    public boolean isEndTimeActual(Event event, Duration timeout) {
        List<EventPeriodsData> eventPeriodsDataList = event.getEventPeriodsData();
        LocalTime endTime = eventPeriodsDataList.get(eventPeriodsDataList.size() - 1).getDateEnd().toLocalTime();
        return isActual(this::getEndTime, endTime, timeout);
    }

    public boolean isStartDateActual(Event event, Duration timeout) {
        return isActual(this::getStartDate, event.getEventPeriodsData().get(0).getDateStart().toLocalDate(), timeout);
    }

    private boolean isActual(Supplier<Temporal> actualTimeSupplier, Temporal expectedTime, Duration timeout) {

        LocalDateTime endVerificationDateTime = LocalDateTime.now().plus(timeout);

        do {
            if (actualTimeSupplier.get().equals(expectedTime)) {
                return true;
            }
            TestUtils.sleep(150);
        }
        while (LocalDateTime.now().isBefore(endVerificationDateTime));

        return false;
    }
}
