package com.intpfy.gui.dialogs.common;

import com.intpfy.gui.dialogs.Closeable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class StreamingAllowedDW extends BaseComponent implements Closeable {

    @ElementInfo(name = "Audio only", findBy = @FindBy(xpath = ".//button[contains(text(), 'Audio Only')]"))
    private Button audioOnlyButton;

    @ElementInfo(name = "Video", findBy = @FindBy(xpath = ".//button[contains(text(), 'Audio and Video')]"))
    private Button videoButton;

    public StreamingAllowedDW(IParent parent) {
        super("'Streaming allowed' dialog window", parent, By.xpath("//div[@class='modal-content' and .//h4[text()='Streaming Allowed']]"));
    }

    public void selectAudioOnly() {
        info("Select 'Audio only'.");
        audioOnlyButton.click();
    }

    public void selectAudioAndVideo() {
        info("Select 'Audio and Video'.");
        videoButton.click();
    }
}
