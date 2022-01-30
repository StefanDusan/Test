package com.intpfy.gui.components.tables.source_user_session;

import com.intpfy.gui.components.tables.BaseTable;
import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.dialogs.logout.LogOutUserDW;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;


public class UsersTable extends BaseTable {

    public UsersTable(IParent parent) {
        super("Users table", parent, By.cssSelector("table"));
    }

    public boolean isPresent(String username, Duration timeout) {
        return createRow(username).visible(timeout);
    }

    public boolean isNotPresent(String username, Duration timeout) {
        return createRow(username).notVisible(timeout);
    }

    public ChatDW openChat(String username) {
        return createRow(username).openChat();
    }

    public LogOutUserDW logOut(String username) {
        return createRow(username).logOut();
    }

    private UsersTableRow createRow(String username) {
        String name = String.format("User '%s' row", username);
        By locator = By.xpath(".//tr[.//div[contains(@class, 'username') and contains(text(), '" + username + "')]]");
        return new UsersTableRow(name, this, locator);
    }
}
