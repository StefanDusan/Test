package com.intpfy.gui.components.emi.dashboard;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.time.LocalDateTime;

public class StatsContainerComponent extends BaseComponent {

    @ElementInfo(name = "Number", findBy = @FindBy(css = "div.number span"))
    private Element numberElement;

    public StatsContainerComponent(IParent parent, StatsType type) {
        super("General Stats", parent, By.xpath(".//div[@class = 'dashboard-stat-container' and contains(., '" + type + "')]"));
    }

    public int getCount() {
        return Integer.parseInt(formatNumber(numberElement.getText()));
    }

    public boolean isCountNotEqual(int count, Duration timeout) {

        LocalDateTime endDateTime = LocalDateTime.now().plus(timeout);

        do {
            if (getCount() != count) {
                return true;
            }
            TestUtils.sleep(250);

        } while (LocalDateTime.now().isBefore(endDateTime));

        return false;
    }

    private String formatNumber(String number) {

        String zero = "0";

        String result = number
                .trim()
                .replace("--:--", zero);

        return result.isEmpty() ? zero : result;
    }
}
