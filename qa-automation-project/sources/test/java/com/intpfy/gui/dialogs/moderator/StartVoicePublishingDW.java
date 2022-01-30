package com.intpfy.gui.dialogs.moderator;

import com.intpfy.gui.dialogs.Cancelable;
import com.intpfy.gui.dialogs.Confirmable;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import static com.intpfy.util.XpathUtil.createContainsTextIgnoreCaseLocator;

public class StartVoicePublishingDW extends BaseComponent implements Cancelable, Confirmable {

    public StartVoicePublishingDW(IParent parent) {
        super("Start voice publishing dialog window", parent,
                By.xpath("//div[@class='modal-content' and " +
                        ".//h4[" + createContainsTextIgnoreCaseLocator("Are you sure you want to start publishing your voice?") + "]]"));
    }
}