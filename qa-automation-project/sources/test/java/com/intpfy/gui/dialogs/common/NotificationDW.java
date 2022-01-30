package com.intpfy.gui.dialogs.common;

import com.intpfy.gui.dialogs.Confirmable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class NotificationDW extends BaseComponent implements Confirmable {

    @ElementInfo(name = "Notification", findBy = @FindBy(css = "div.notification-body"))
    private Element notificationElement;

    public NotificationDW(IParent parent) {
        super("Notification dialog window", parent, By.xpath("//div[@class='modal-content' and .//h4[text()='NOTIFICATION']]"));
    }

    public String getText() {
        return notificationElement.getText();
    }
}