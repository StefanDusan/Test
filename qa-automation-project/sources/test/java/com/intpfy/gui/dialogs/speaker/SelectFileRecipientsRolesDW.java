package com.intpfy.gui.dialogs.speaker;

import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfy.model.event.Role;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

import static com.intpfy.model.event.Role.*;

public class SelectFileRecipientsRolesDW extends BaseComponent {

    @ElementInfo(name = "Delegates complex checkbox", findBy = @FindBy(xpath = ".//div[./label[contains(text(), 'Delegates')]]"))
    private ComplexCheckbox delegatesCheckbox;

    @ElementInfo(name = "Interpreters complex checkbox", findBy = @FindBy(xpath = ".//div[./label[contains(text(), 'Interpreters')]]"))
    private ComplexCheckbox interpretersCheckbox;

    @ElementInfo(name = "Attendees complex checkbox", findBy = @FindBy(xpath = ".//div[./label[contains(text(), 'Attendees')]]"))
    private ComplexCheckbox attendeesCheckbox;

    @ElementInfo(name = "Cancel", findBy = @FindBy(xpath = ".//button[contains(text(),'CANCEL')]"))
    private Button cancelButton;

    @ElementInfo(name = "Send", findBy = @FindBy(xpath = ".//button[contains(text(), 'SEND')]"))
    private Button sendButton;

    public SelectFileRecipientsRolesDW(IParent parent) {
        super("'Select File recipients roles' dialog window", parent,
                By.xpath("//div[@class = 'modal-content' and contains(., 'RECEIVE THE FILE')]"));
    }

    public void selectRoles(Map<Role, Boolean> roles) {

        configureDelegates(roles);
        configureInterpreters(roles);
        configureAttendees(roles);
    }

    public void send() {
        info("Send.");
        sendButton.click();
    }

    private void configureDelegates(Map<Role, Boolean> roles) {

        if (roles.get(Delegate)) {
            selectDelegates();
        } else {
            deselectDelegates();
        }
    }

    private void configureInterpreters(Map<Role, Boolean> roles) {

        if (roles.get(Interpreter)) {
            selectInterpreters();
        } else {
            deselectInterpreters();
        }
    }

    private void configureAttendees(Map<Role, Boolean> roles) {

        if (roles.get(Attendee)) {
            selectAttendees();
        } else {
            deselectAttendees();
        }
    }

    private void selectDelegates() {
        info("Select Delegates.");
        delegatesCheckbox.select();
    }

    private void deselectDelegates() {
        info("Deselect Delegates.");
        delegatesCheckbox.unselect();
    }

    private void selectInterpreters() {
        info("Select Interpreters.");
        interpretersCheckbox.select();
    }

    private void deselectInterpreters() {
        info("Deselect Interpreters.");
        interpretersCheckbox.unselect();
    }

    private void selectAttendees() {
        info("Select Attendees.");
        attendeesCheckbox.select();
    }

    private void deselectAttendees() {
        info("Deselect Attendees.");
        attendeesCheckbox.unselect();
    }
}
