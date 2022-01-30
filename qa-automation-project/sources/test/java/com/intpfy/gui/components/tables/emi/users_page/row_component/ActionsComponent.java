package com.intpfy.gui.components.tables.emi.users_page.row_component;

import com.intpfy.gui.pages.emi.user_creation.EditUserPage;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class ActionsComponent extends BaseComponent {

    @ElementInfo(name = "Edit", findBy = @FindBy(css = "a[title = 'Edit user']"))
    private Button editButton;

    @ElementInfo(name = "View action", findBy = @FindBy(css = "a[title = 'View users action']"))
    private Button viewActionButton;

    @ElementInfo(name = "Suspend", findBy = @FindBy(css = "a[title = 'Suspend user']"))
    private Button suspendButton;

    @ElementInfo(name = "Delete", findBy = @FindBy(css = "a[title = 'Delete user']"))
    private Button deleteButton;

    public ActionsComponent(IParent parent) {
        super("Actions", parent, By.cssSelector("td[title=\"'Actions'\"]"));
    }

    public EditUserPage edit() {
        editButton.click();
        return new EditUserPage(getPage().getPageContext());
    }
}
