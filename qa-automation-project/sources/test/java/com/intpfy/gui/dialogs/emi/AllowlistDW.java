package com.intpfy.gui.dialogs.emi;

import com.intpfy.gui.dialogs.Cancelable;
import com.intpfy.gui.dialogs.Closeable;
import com.intpfy.gui.dialogs.Confirmable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.TextArea;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class AllowlistDW extends BaseComponent implements Confirmable, Closeable, Cancelable {

    @ElementInfo(name = "Close", findBy = @FindBy(css = "i.icon-close"))
    private Button closeButton;

    @ElementInfo(name = "Apply", findBy = @FindBy(xpath = ".//a[contains(@class, 'btn') and text() = 'Apply']"))
    private Button applyButton;

    @ElementInfo(name = "Allowlist", findBy = @FindBy(css = "textarea.form-control"))
    private TextArea textArea;

    public AllowlistDW(IParent parent) {
        super("Allowlist dialog window", parent, By.cssSelector("div.whitelist-modal"));
    }

    @Override
    public Button getCloseButton() {
        return closeButton;
    }

    @Override
    public Button getConfirmButton() {
        return applyButton;
    }

    public void addEmail(String email) {
        info(String.format("Add email to Allowlist '%s'.", email));
        textArea.setText(email);
    }
}