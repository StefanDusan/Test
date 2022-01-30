package com.intpfy.gui.components.chats.file;

import com.intpfy.model.event.Role;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class DocumentComponent extends BaseComponent {

    private static final String INACTIVE_CLASS = "_inactive";

    @ElementInfo(name = "Roles", findBy = @FindBy(css = "div._access"))
    private Element rolesElement;

    private final DocumentActionsComponent actions;

    public DocumentComponent(IParent parent, String fileName) {
        super("Document container", parent, By.xpath(".//div[@class = 'document-header']/parent::div[.//div[contains(text(), '" + fileName + "')]]"));
        actions = new DocumentActionsComponent(this);
    }

    public boolean isAvailableForDownload(Duration timeout) {
        return actions.isDownloadAvailable(timeout);
    }

    public boolean areRolesPresent(Set<Role> roles) {
        return getRoles().equals(roles);
    }

    public boolean isScanning(Duration timeout) {
        return getComponentElement().waitCssClassContains(INACTIVE_CLASS, timeout);
    }

    public boolean isNotScanning(Duration timeout) {
        return getComponentElement().waitCssClassNotContains(INACTIVE_CLASS, timeout);
    }

    private Set<Role> getRoles() {

        String rolesString = rolesElement.getText();
        String[] rolesArray = rolesString.split(",");

        return Arrays.stream(rolesArray)
                .map(String::trim)
                .map(Role::fromString)
                .collect(Collectors.toSet());
    }
}
