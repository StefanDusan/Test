package com.intpfy.gui.components.moderator;

import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

import static com.intpfy.util.XpathUtil.createEqualsTextIgnoreCaseLocator;

public class RecordingItemComponent extends BaseComponent {

    @ElementInfo(name = "Enable Recording complex checkbox", findBy = @FindBy(css = "div.modal-recording-item__right div.toggle-switch"))
    private ComplexCheckbox enableCheckbox;

    @ElementInfo(name = "Recording status", findBy = @FindBy(css = "div.modal-recording-item__info > span"))
    private Element statusElement;

    public RecordingItemComponent(IParent parent, String text) {
        super(text + " recording item", parent,
                By.xpath(".//div[@class='modal-recording-item' and .//div[" + createEqualsTextIgnoreCaseLocator(text) + "]]"));
    }

    public RecordingItemComponent(IParent parent, Language language) {
        this(parent, language.name());
    }

    public void enableRecording() {
        if (isRecordingDisabled(Duration.ZERO)) {
            enableCheckbox.select();
        }
    }

    public void disableRecording() {
        if (isRecordingEnabled(Duration.ZERO)) {
            enableCheckbox.unselect();
        }
    }

    public boolean isRecordingEnabled(Duration timeout) {
        return enableCheckbox.waitIsSelected(timeout) && statusElement.waitForTextNotContains("off", timeout);
    }

    public boolean isRecordingDisabled(Duration timeout) {
        return enableCheckbox.waitIsNotSelected(timeout) && statusElement.waitForTextContains("off", timeout);
    }
}
