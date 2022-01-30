package com.intpfy.gui.components.speaker;

import com.intpfy.exception.EventTypeNotDefinedException;
import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfy.gui.components.common.VolumeBarComponent;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.function.Predicate;

public class StreamComponent extends BaseComponent {

    @ElementInfo(name = "Mic", findBy = @FindBy(xpath = ".//div[contains(@class, 'wc-mic-icon')]/parent::div"))
    private Button micButton;

    @ElementInfo(name = "Change Mic", findBy = @FindBy(xpath = ".//div[contains(@class, 'wc-mic-icon')]/parent::div/following-sibling::div"))
    private Button changeMicButton;

    @ElementInfo(name = "Camera", findBy = @FindBy(xpath = ".//div[contains(@class, 'wc-cam-icon')]/parent::div"))
    private Button cameraButton;

    @ElementInfo(name = "Change Camera", findBy = @FindBy(xpath = ".//div[contains(@class, 'wc-cam-icon')]/parent::div/following-sibling::div"))
    private Button changeCameraButton;

    @ElementInfo(name = "Screen sharing", findBy = @FindBy(xpath = ".//div[contains(@class, 'wc-screen-icon')]/parent::div"))
    private Button screenSharingButton;

    @ElementInfo(name = "Call", findBy = @FindBy(xpath = ".//div[contains(@class, 'wc-call-icon')]/parent::div"))
    private Button callButton;

    @ElementInfo(name = "Start", findBy = @FindBy(xpath = ".//div[@class = 'stream-btn_ico']/parent::div"))
    private Button startButton;

    @ElementInfo(name = "Unmute Audio complex checkbox", findBy = @FindBy(css = "div._audio-control div.toggle-switch"))
    private ComplexCheckbox unmuteAudioCheckbox;

    @ElementInfo(name = "Enable Camera complex checkbox", findBy = @FindBy(css = "div._video-control div.toggle-switch"))
    private ComplexCheckbox enableCameraCheckbox;

    @ElementInfo(name = "Enable Screen sharing complex checkbox", findBy = @FindBy(css = "div._screen-control div.toggle-switch"))
    private ComplexCheckbox enableScreenSharingCheckbox;

    @ElementInfo(name = "Stop streaming", findBy = @FindBy(css = "div._stop-streaming"))
    private Button stopStreamingButton;

    private final VolumeBarComponent volumeBar;
    private final RequestFloorComponent requestFloor;

    public StreamComponent(IParent parent) {
        super("Stream", parent, By.xpath(".//div[contains(@class, 'stream-indicator')]"));
        volumeBar = new VolumeBarComponent(this);
        requestFloor = new RequestFloorComponent(this);
    }

    public void raiseHand() {
        requestFloor.raiseHand();
    }

    public void lowerHand() {
        requestFloor.lowerHand();
    }

    public boolean isHandRaised(Duration timeout) {
        return requestFloor.isHandRaised(timeout);
    }

    public boolean isHandDown(Duration timeout) {
        return requestFloor.isHandDown(timeout);
    }

    public void connect() {
        executeDependingOnEventType(this::connectInConnectProOrWebMeet, this::connectInEventPro);
    }

    public void disconnect() {
        executeDependingOnEventType(this::disconnectInConnectProOrWebMeet, this::disconnectInEventPro);
    }

    public boolean isConnected(Duration timeout) {
        return testDependingOnEventType(this::isConnectedInConnectProOrWebMeet, this::isConnectedInEventPro, timeout);
    }

    public boolean isDisconnected(Duration timeout) {
        return testDependingOnEventType(this::isDisconnectedInConnectProOrWebMeet, this::isDisconnectedInEventPro, timeout);
    }

    public void turnMicOn() {
        executeDependingOnEventType(this::turnMicOnInConnectProOrWebMeet, this::turnMicOnInEventPro);
    }

    public void turnMicOff() {
        executeDependingOnEventType(this::turnMicOffInConnectProOrWebMeet, this::turnMicOffInEventPro);
    }

    public boolean isMicOn(Duration timeout) {
        return testDependingOnEventType(this::isMicOnInConnectProOrWebMeet, this::isMicOnInEventPro, timeout);
    }

    public boolean isMicOff(Duration timeout) {
        return testDependingOnEventType(this::isMicOffInConnectProOrWebMeet, this::isMicOffInEventPro, timeout);
    }

    public boolean isVolumeLevelChanging(Duration timeout) {
        return volumeBar.isChanging(timeout);
    }

    public boolean isVolumeLevelNotChanging(Duration timeout) {
        return volumeBar.isNotChanging(timeout);
    }

    public void turnCameraOn() {
        executeDependingOnEventType(this::turnCameraOnInConnectProOrWebMeet, this::turnCameraOnInEventPro);
    }

    public void turnCameraOff() {
        executeDependingOnEventType(this::turnCameraOffInConnectProOrWebMeet, this::turnCameraOffInEventPro);
    }

    public boolean isCameraOn(Duration timeout) {
        return testDependingOnEventType(this::isCameraOnInConnectProOrWebMeet, this::isCameraOnInEventPro, timeout);
    }

    public boolean isCameraOff(Duration timeout) {
        return testDependingOnEventType(this::isCameraOffInConnectProOrWebMeet, this::isCameraOffInEventPro, timeout);
    }

    public void enableScreenSharing() {
        executeDependingOnEventType(this::enableScreenSharingInConnectProOrWebMeet, this::enableScreenSharingInEventPro);
    }

    public void disableScreenSharing() {
        executeDependingOnEventType(this::disableScreenSharingInConnectProOrWebMeet, this::disableScreenSharingInEventPro);
    }

    public boolean isScreenSharingEnabled(Duration timeout) {
        return testDependingOnEventType(this::isScreenSharingEnabledInConnectProOrWebMeet, this::isScreenSharingEnabledInEventPro, timeout);
    }

    public boolean isScreenSharingDisabled(Duration timeout) {
        return testDependingOnEventType(this::isScreenSharingDisabledInConnectProOrWebMeet, this::isScreenSharingDisabledInEventPro, timeout);
    }

    // Method should be used only for Speakers which accepted streaming invite from Host
    public void stopStreaming() {
        stopStreamingButton.click();
    }

    public boolean areControlsDisabled(Duration timeout) {
        if (isEventPro()) {
            throw new IllegalStateException("Stream controls are not available in 'Event Pro'.");
        }
        Element controlsElement = getComponentElement().createChild("Stream controls", By.cssSelector("div.stream-indicator__controls"));
        return isOff(controlsElement, timeout);
    }

    public boolean isMuted(Duration timeout) {
        return isMicOff(timeout) && (volumeBar.notVisible(timeout) || volumeBar.isNotChanging(timeout));
    }

    private boolean isConnectProOrWebMeet() {
        return callButton.visible(Duration.ZERO) || stopStreamingButton.visible(Duration.ZERO);
    }

    private boolean isEventPro() {
        return startButton.visible(Duration.ZERO);
    }

    private void connectInConnectProOrWebMeet() {
        callButton.click();
    }

    private void connectInEventPro() {
        startButton.click();
    }

    private void disconnectInConnectProOrWebMeet() {
        callButton.click();
    }

    private void disconnectInEventPro() {
        startButton.click();
    }

    private boolean isConnectedInConnectProOrWebMeet(Duration timeout) {
        return isOn(callButton, timeout);
    }

    private boolean isConnectedInEventPro(Duration timeout) {
        return isOn(startButton, timeout);
    }

    private boolean isDisconnectedInConnectProOrWebMeet(Duration timeout) {
        return isOff(callButton, timeout);
    }

    private boolean isDisconnectedInEventPro(Duration timeout) {
        return isOff(startButton, timeout);
    }

    private void turnMicOnInConnectProOrWebMeet() {
        micButton.click();
    }

    private void turnMicOnInEventPro() {
        unmuteAudioCheckbox.select();
    }

    private void turnMicOffInConnectProOrWebMeet() {
        micButton.click();
    }

    private void turnMicOffInEventPro() {
        unmuteAudioCheckbox.unselect();
    }

    private boolean isMicOnInConnectProOrWebMeet(Duration timeout) {
        return isOn(micButton, timeout) && changeMicButton.visible(timeout);
    }

    private boolean isMicOnInEventPro(Duration timeout) {
        return unmuteAudioCheckbox.waitIsSelected(timeout);
    }

    private boolean isMicOffInConnectProOrWebMeet(Duration timeout) {
        return isOff(micButton, timeout) && changeMicButton.notVisible(timeout);
    }

    private boolean isMicOffInEventPro(Duration timeout) {
        return unmuteAudioCheckbox.waitIsNotSelected(timeout);
    }

    private void turnCameraOnInConnectProOrWebMeet() {
        cameraButton.click();
    }

    private void turnCameraOnInEventPro() {
        enableCameraCheckbox.select();
    }

    private void turnCameraOffInConnectProOrWebMeet() {
        cameraButton.click();
    }

    private void turnCameraOffInEventPro() {
        enableCameraCheckbox.unselect();
    }

    private boolean isCameraOnInConnectProOrWebMeet(Duration timeout) {
        return isOn(cameraButton, timeout) && changeCameraButton.visible(timeout);
    }

    private boolean isCameraOnInEventPro(Duration timeout) {
        return enableCameraCheckbox.waitIsSelected(timeout);
    }

    private boolean isCameraOffInConnectProOrWebMeet(Duration timeout) {
        return isOff(cameraButton, timeout) && changeCameraButton.notVisible(timeout);
    }

    private boolean isCameraOffInEventPro(Duration timeout) {
        return enableCameraCheckbox.waitIsNotSelected(timeout);
    }

    private void enableScreenSharingInConnectProOrWebMeet() {
        screenSharingButton.click();
    }

    public void enableScreenSharingInEventPro() {
        enableScreenSharingCheckbox.select();
    }

    private void disableScreenSharingInConnectProOrWebMeet() {
        screenSharingButton.click();
    }

    public void disableScreenSharingInEventPro() {
        enableScreenSharingCheckbox.unselect();
    }

    public boolean isScreenSharingEnabledInConnectProOrWebMeet(Duration timeout) {
        return isOn(screenSharingButton, timeout);
    }

    private boolean isScreenSharingEnabledInEventPro(Duration timeout) {
        return enableScreenSharingCheckbox.waitIsSelected(timeout);
    }

    public boolean isScreenSharingDisabledInConnectProOrWebMeet(Duration timeout) {
        return isOff(screenSharingButton, timeout);
    }

    private boolean isScreenSharingDisabledInEventPro(Duration timeout) {
        return enableScreenSharingCheckbox.waitIsNotSelected(timeout);
    }

    private void executeDependingOnEventType(Runnable connectProOrWebMeetAction, Runnable eventProAction) {
        if (isConnectProOrWebMeet()) {
            connectProOrWebMeetAction.run();
        } else if (isEventPro()) {
            eventProAction.run();
        } else {
            throw new EventTypeNotDefinedException();
        }
    }

    private boolean testDependingOnEventType(Predicate<Duration> connectProOrWebMeetPredicate, Predicate<Duration> eventProPredicate, Duration timeout) {
        if (isConnectProOrWebMeet()) {
            return connectProOrWebMeetPredicate.test(timeout);
        } else if (isEventPro()) {
            return eventProPredicate.test(timeout);
        }
        throw new EventTypeNotDefinedException();
    }

    private boolean isOn(Element element, Duration timeout) {
        return element.waitCssClassContains("_on", timeout);
    }

    private boolean isOff(Element element, Duration timeout) {
        return element.waitCssClassContains("_off", timeout);
    }
}
