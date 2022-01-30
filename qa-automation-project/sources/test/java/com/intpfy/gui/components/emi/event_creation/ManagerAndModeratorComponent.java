package com.intpfy.gui.components.emi.event_creation;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

public class ManagerAndModeratorComponent extends BaseComponent {

    private final SelectManagerComponent selectManager;
    private final SelectModeratorComponent selectModerator;

    public ManagerAndModeratorComponent(IParent parent) {
        super("Manager and Moderator", parent, By.id("tab2"));
        selectManager = new SelectManagerComponent(this);
        selectModerator = new SelectModeratorComponent(this);
    }
}
