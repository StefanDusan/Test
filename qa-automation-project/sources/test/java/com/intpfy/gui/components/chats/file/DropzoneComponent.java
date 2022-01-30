package com.intpfy.gui.components.chats.file;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class DropzoneComponent extends BaseComponent {

    @ElementInfo(name = "Type your message", findBy = @FindBy(css = "input[type = 'file']"))
    private Input fileInput;

    public DropzoneComponent(IParent parent) {
        super("Dropzone", parent, By.cssSelector("div.custom-dropzone"));
    }

    public void uploadFile(String path) {
        fileInput.sendKeys(path);
    }
}
