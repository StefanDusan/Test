package com.intpfy.gui.components.chats.file;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class DocumentActionsComponent extends BaseComponent {

    @ElementInfo(name = "Delete", findBy = @FindBy(xpath = ".//i[.//*[@* =  '#svg-remove-msg']]"))
    private Element deleteElement;

    @ElementInfo(name = "Download", findBy = @FindBy(xpath = ".//i[.//*[@* =  '#svg-file-download']]"))
    private Element downloadElement;

    public DocumentActionsComponent(IParent parent) {
        super("Document actions", parent, By.cssSelector("div.document-actions"));
    }

    public boolean isDownloadAvailable(Duration timeout) {
        return downloadElement.visible(timeout);
    }
}
