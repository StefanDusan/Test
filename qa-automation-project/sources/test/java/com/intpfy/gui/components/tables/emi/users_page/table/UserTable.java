package com.intpfy.gui.components.tables.emi.users_page.table;

import com.intpfy.gui.components.tables.emi.users_page.row.UserTableRow;
import com.intpfy.gui.pages.emi.user_creation.EditUserPage;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

public class UserTable extends BaseComponent {

    public UserTable(IParent parent) {
        super("User table", parent, By.cssSelector("table.dataTable"));
    }

    public boolean isDisplayed(String email, Duration timeout) {
        return createRow(email).visible(timeout);
    }

    public boolean isNotDisplayed(String email, Duration timeout) {
        return createRow(email).notVisible(timeout);
    }

    public EditUserPage edit(String email) {
        return createRow(email).edit();
    }

    private UserTableRow createRow(String email) {
        return new UserTableRow(email, this);
    }
}
