package com.intpfy.gui.dialogs.interpreter;

import com.intpfy.gui.dialogs.Closeable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class HandoverDW extends BaseComponent implements Closeable {

    @ElementInfo(name = "Mic", findBy = @FindBy(css = "a.btn-rec"))
    private Button micButton;

    public HandoverDW(IParent parent) {
        super("Handover dialog window", parent, By.xpath("//div[@class='modal-content' and contains(.,'HANDOVER')]"));
    }

    public void mute() {
        info("Mute.");
        if (isUnmuted(Duration.ZERO)) {
            micButton.click();
        }
    }

    public void unmute() {
        info("Unmute.");
        if (isMuted(Duration.ZERO)) {
            micButton.click();
        }
    }

    public boolean isMuted() {
        return isMuted(WebSettings.AJAX_TIMEOUT);
    }

    private boolean isMuted(Duration timeout) {
        return micButton.waitCssClassContains("_muted", timeout);
    }

    public boolean isUnmuted() {
        return isUnmuted(WebSettings.AJAX_TIMEOUT);
    }

    public boolean isUnmuted(Duration timeout) {
        return micButton.waitCssClassContains("_unmuted", timeout);
    }
}