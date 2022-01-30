package com.intpfy.gui.components.tables.event_table.row;

import com.intpfy.gui.dialogs.emi.DeleteEventDW;
import com.intpfy.gui.dialogs.emi.EventArchivesDW;
import com.intpfy.gui.pages.emi.event_creation.EditEventPage;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class ActionsComponent extends BaseComponent {

    @ElementInfo(name = "Edit", findBy = @FindBy(css = "button[uib-tooltip='Edit event']"))
    private Button editButton;

    @ElementInfo(name = "Archives", findBy = @FindBy(css = "button[uib-tooltip='Get event archives']"))
    private Button archivesButton;

    @ElementInfo(name = "Delete", findBy = @FindBy(css = "button[uib-tooltip='Delete event']"))
    private Button deleteButton;

    public ActionsComponent(IParent parent) {
        super("Actions", parent, By.cssSelector("td[title=\"'Actions'\"]"));
    }

    public EditEventPage edit() {
        editButton.click();
        return new EditEventPage(getPage().getPageContext());
    }

    public EventArchivesDW openEventArchivesDW() {
        archivesButton.click();
        return new EventArchivesDW(getPage());
    }

    public DeleteEventDW delete() {
        deleteButton.click();
        return new DeleteEventDW(getPage());
    }
}
