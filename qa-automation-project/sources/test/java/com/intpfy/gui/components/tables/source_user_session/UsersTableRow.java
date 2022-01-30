package com.intpfy.gui.components.tables.source_user_session;


import com.intpfy.gui.components.tables.BaseRow;
import com.intpfy.gui.dialogs.common.ChatDW;
import com.intpfy.gui.dialogs.logout.LogOutUserDW;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class UsersTableRow extends BaseRow {

    @ElementInfo(name = "RAL", findBy = @FindBy(css = "i.ral-btn"))
    private Button ralButton;

    @ElementInfo(name = "Log out", findBy = @FindBy(css = "i.logout-btn"))
    private Button logOutButton;

    @ElementInfo(name = "Chat out", findBy = @FindBy(css = "i.chat-btn"))
    private Button chatButton;

    public UsersTableRow(String name, IParent parent, By componentLocator) {
        super(name, parent, componentLocator);
    }

    public LogOutUserDW logOut() {
        logOutButton.click();
        return new LogOutUserDW(getPage());
    }

    public ChatDW openChat() {
        chatButton.click();
        return new ChatDW(getPage());
    }
}