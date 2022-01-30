package com.intpfy.gui.components.tables.emi.users_page.row;

import com.intpfy.gui.components.tables.emi.users_page.row_component.ActionsComponent;
import com.intpfy.gui.pages.emi.user_creation.EditUserPage;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class UserTableRow extends BaseComponent {

    private final ActionsComponent actions;

    public UserTableRow(String email, IParent parent) {
        super(String.format("Event table row for user '%s'", email), parent,
                By.xpath(".//tr[./td[@data-title-text = 'Email' and text() = '" + email + "']]"));
        actions = new ActionsComponent(this);
    }

    public EditUserPage edit() {
        return actions.edit();
    }
}
