package com.intpfy.gui.components.chats.file;

import com.intpfy.model.event.Role;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.Set;

public class DocumentContainerComponent extends BaseComponent {

    @ElementInfo(name = "No files", findBy = @FindBy(css = "div.no-files"))
    private Element noFilesElement;

    public DocumentContainerComponent(IParent parent) {
        super("Document container", parent, By.cssSelector("div.document-container"));
    }

    public boolean areNoFilesAvailable(Duration timeout) {
        return noFilesElement.visible(timeout);
    }

    public boolean isDocumentPresent(String documentName, Duration timeout) {
        return createDocumentComponent(documentName).visible(timeout);
    }

    public boolean isDocumentNotPresent(String fileName, Duration timeout) {
        return createDocumentComponent(fileName).notVisible(timeout);
    }

    public boolean areRolesPresent(String documentName, Set<Role> roles) {
        return createDocumentComponent(documentName).areRolesPresent(roles);
    }

    public boolean isScanning(String documentName, Duration timeout) {
        return createDocumentComponent(documentName).isScanning(timeout);
    }

    public boolean isNotScanning(String documentName, Duration timeout) {
        return createDocumentComponent(documentName).isNotScanning(timeout);
    }

    public boolean isDocumentAvailableForDownload(String fileName, Duration timeout) {
        return createDocumentComponent(fileName).isAvailableForDownload(timeout);
    }

    private DocumentComponent createDocumentComponent(String documentName) {
        return new DocumentComponent(this, documentName);
    }
}
