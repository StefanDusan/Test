package com.intpfy.gui.pages.emi;

import com.intpfy.gui.components.emi.common.SearchInputComponent;
import com.intpfy.gui.components.tables.emi.users_page.table.UserTable;
import com.intpfy.gui.pages.emi.user_creation.EditUserPage;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class UsersPage extends BaseEmiPage {

    @ElementInfo(name = "Page title", findBy = @FindBy(xpath = "//span[text() = 'List of Users']"))
    private Element pageTitle;

    private final SearchInputComponent searchInput;
    private final UserTable table;

    public UsersPage(IPageContext pageContext) {
        super("Users page", pageContext);
        searchInput = new SearchInputComponent(this, By.xpath("//label[contains(text(), 'Search')]"));
        table = new UserTable(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return pageTitle.visible(timeout);
    }

    public boolean isDisplayed(String email, Duration timeout) {
        return table.isDisplayed(email, timeout);
    }

    public boolean isNotDisplayed(String email, Duration timeout) {
        return table.isNotDisplayed(email, timeout);
    }

    public void searchIfNotDisplayed(String email) {
        if (isNotDisplayed(email, Duration.ZERO)) {
            search(email);
        }
    }

    public EditUserPage edit(String email) {
        return table.edit(email);
    }

    private void search(String email) {
        info(String.format("Search user '%s'.", email));
        searchInput.search(email);
    }
}
