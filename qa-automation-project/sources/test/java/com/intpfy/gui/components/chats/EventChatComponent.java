package com.intpfy.gui.components.chats;

import com.intpfy.gui.components.chats.file.DocumentContainerComponent;
import com.intpfy.gui.components.chats.file.DropzoneComponent;
import com.intpfy.model.event.Role;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.Set;

public class EventChatComponent extends BaseChatComponent {

    @ElementInfo(name = "Chat disabled tooltip", findBy =
    @FindBy(xpath = ".//div[text() ='Event Chat has been disabled by event organiser']"))
    private Element disabledByOrganizerTooltipElement;

    private final EventChatHeaderComponent header;
    private final DocumentContainerComponent documentContainer;
    private final DropzoneComponent dropzone;

    public EventChatComponent(IParent parent) {
        super("Event chat", parent, By.cssSelector("div.chat._event-chat, div.floor-control__chat-area"));
        header = new EventChatHeaderComponent(this);
        documentContainer = new DocumentContainerComponent(this);
        dropzone = new DropzoneComponent(this);
    }

    public boolean isOfChatType(Duration timeout) {
        return footer.visible(timeout) && documentContainer.notVisible(timeout);
    }

    public boolean isOfAnnouncementsType(Duration timeout) {
        return header.isOfAnnouncementsType(timeout);
    }

    public boolean isOfFilesType(Duration timeout) {
        return footer.notVisible(timeout) && documentContainer.visible(timeout);
    }

    public String getUsernameColor(String message) {
        return createChatLineComponent(message).getUsernameColor();
    }

    public void deleteMessage(String message) {
        createChatLineComponent(message).deleteMessage();
    }

    public boolean isMessageHidden(String message, Duration timeout) {
        return createChatLineComponent(message).isMessageHidden(timeout);
    }

    public void restoreMessage(String message) {
        createChatLineComponent(message).restoreMessage();
    }

    public void deleteMessageForever(String message) {
        createChatLineComponent(message).deleteMessageForever();
    }

    public boolean isDisabledByOrganizerTooltipDisplayedOnHover(Duration timeout) {
        moveMouseInFooter();
        return disabledByOrganizerTooltipElement.visible(timeout);
    }

    public boolean isDisabledByOrganizerTooltipNotDisplayedOnHover(Duration timeout) {
        moveMouseInFooter();
        return disabledByOrganizerTooltipElement.notVisible(timeout);
    }

    public void switchToChat() {
        header.switchToChat();
    }

    public void switchToFiles() {
        header.switchToFiles();
    }

    public void uploadFile(String path) {
        dropzone.uploadFile(path);
    }

    public boolean isFilePresent(String fileName, Duration timeout) {
        return documentContainer.isDocumentPresent(fileName, timeout);
    }

    public boolean isFileNotPresent(String fileName, Duration timeout) {
        return documentContainer.isDocumentNotPresent(fileName, timeout);
    }

    public boolean areRolesPresent(String documentName, Set<Role> roles) {
        return documentContainer.areRolesPresent(documentName, roles);
    }

    public boolean isFileScanning(String documentName, Duration timeout) {
        return documentContainer.isScanning(documentName, timeout);
    }

    public boolean isFileNotScanning(String documentName, Duration timeout) {
        return documentContainer.isNotScanning(documentName, timeout);
    }

    public boolean isFileAvailableForDownload(String fileName, Duration timeout) {
        return documentContainer.isDocumentAvailableForDownload(fileName, timeout);
    }
}
