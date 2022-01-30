package com.intpfy.gui.dialogs.moderator;

import com.intpfy.gui.components.tables.source_user_session.UsersTable;
import com.intpfy.gui.dialogs.Closeable;
import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.dialogs.logout.LogOutUserDW;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

import static com.intpfy.util.XpathUtil.createContainsTextIgnoreCaseLocator;

public class UsersSessionDW extends BaseComponent implements Closeable {

    @ElementInfo(name = "Search", findBy = @FindBy(css = "input.users-modal__search-input"))
    private Input searchInput;

    private final UsersTable usersTable;

    public UsersSessionDW(IParent parent, Language language) {
        super("Users sessions dialog window", parent,
                By.xpath("//div[@class='modal-content' and " +
                        ".//h4[" + createContainsTextIgnoreCaseLocator(language.name() + " users session") + "]]"));
        usersTable = new UsersTable(this);
    }

    public boolean isUserPresent(String username, Duration timeout) {
        return usersTable.isPresent(username, timeout);
    }

    public boolean isUserNotPresent(String username, Duration timeout) {
        return usersTable.isNotPresent(username, timeout);
    }

    public void searchUser(String username) {
        info(String.format("Search user: '%s'.", username));
        searchInput.setText(username);
    }

    public ChatDW openRemoteSupportHelpChatForUser(String username) {
        info(String.format("Open 'Remote support help' chat for user '%s'.", username));
        return usersTable.openChat(username);
    }

    public LogOutUserDW logOutUser(String username) {
        info(String.format("Log out user: '%s'.", username));
        return usersTable.logOut(username);
    }
}
