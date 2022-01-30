package com.intpfy.verifiers.devices_test;

import com.intpfy.gui.pages.devices_test.TestDevicesPage;
import com.intpfy.model.Language;

import java.time.Duration;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static com.intpfy.verifiers.devices_test.TestDevicesPageVerificationMessages.*;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;
import static com.intpfyqa.test.Verify.*;

public final class TestDevicesPageVerifier {

    private static final Duration CONNECTION_TEST_START_TIMEOUT = Duration.ofSeconds(7);
    private static final Duration CONNECTION_TEST_DURATION = Duration.ofSeconds(20);

    private final TestDevicesPage page;

    public TestDevicesPageVerifier(TestDevicesPage page) {
        this.page = page;
    }

    public void assertConnectionTestGoing() {
        assertTrue(page.isConnectionTestGoing(CONNECTION_TEST_START_TIMEOUT), CONNECTION_TEST_GOING);
    }

    public void assertConnectionTestFinished() {
        assertTrue(page.isConnectionTestFinished(CONNECTION_TEST_DURATION), CONNECTION_TEST_FINISHED);
    }

    public void assertVideoConnectionTestResultAvailable() {
        assertTrue(page.isVideoConnectionTestResultAvailable(AJAX_TIMEOUT), VIDEO_CONNECTION_TEST_RESULT_AVAILABLE);
    }

    public void assertMicrophoneConnectionTestResultAvailable() {
        assertTrue(page.isMicrophoneConnectionTestResultAvailable(AJAX_TIMEOUT), MICROPHONE_CONNECTION_TEST_RESULT_AVAILABLE);
    }

    public void assertSpeakerConnectionTestResultAvailable() {
        assertTrue(page.isSpeakerConnectionTestResultAvailable(AJAX_TIMEOUT), SPEAKER_CONNECTION_TEST_RESULT_AVAILABLE);
    }

    public void assertNetworkConnectionTestResultAvailable() {
        assertTrue(page.isNetworkConnectionTestResultAvailable(AJAX_TIMEOUT), NETWORK_CONNECTION_TEST_RESULT_AVAILABLE);
    }

    public void assertLanguageSelected(Language language) {
        assertTrue(page.isLanguageSelected(language, AJAX_TIMEOUT), String.format(LANGUAGE_SELECTED, language));
    }

    public void verifySelectedCameraNameEqualsToTooltip() {
        verifySelectedNameEqualsToTooltip(page::getSelectedCameraName, page::getSelectedCameraTooltip, CAMERA);
    }

    public void verifySelectedMicrophoneNameEqualsToTooltip() {
        verifySelectedNameEqualsToTooltip(page::getSelectedMicrophoneName, page::getSelectedMicrophoneTooltip, MICROPHONE);
    }

    public void verifySelectedAudioDeviceNameEqualsToTooltip() {
        verifySelectedNameEqualsToTooltip(page::getSelectedAudioDeviceName, page::getSelectedAudioDeviceTooltip, AUDIO_DEVICE);
    }

    public void verifySelectedCameraNameEqualsToTooltipInDropdown() {
        verifySelectedNameEqualsToTooltipInDropdown(page::getSelectedCameraName, page::getCameraDropdownTooltip, CAMERA);
    }

    public void verifySelectedMicrophoneNameEqualsToTooltipInDropdown() {
        verifySelectedNameEqualsToTooltipInDropdown(page::getSelectedMicrophoneName, page::getMicrophoneDropdownTooltip, MICROPHONE);
    }

    public void verifySelectedAudioDeviceNameEqualsToTooltipInDropdown() {
        verifySelectedNameEqualsToTooltipInDropdown(page::getSelectedAudioDeviceName, page::getAudioDeviceDropdownTooltip, AUDIO_DEVICE);
    }

    private void verifySelectedNameEqualsToTooltip(Supplier<String> selectedNameSupplier, Supplier<String> tooltipSupplier, String deviceName) {

        String selected = selectedNameSupplier.get();
        String tooltip = tooltipSupplier.get();
        String message = String.format(SELECTED_DEVICE_NAME_EQUALS_TO_TOOLTIP, deviceName, selected, tooltip);

        verifyTextIgnoreCase(selected, tooltip, message);
    }

    private void verifySelectedNameEqualsToTooltipInDropdown(Supplier<String> selectedNameSupplier, UnaryOperator<String> dropdownTooltipSupplier, String deviceName) {

        String selected = selectedNameSupplier.get();
        String tooltip = dropdownTooltipSupplier.apply(selected);
        String message = String.format(SELECTED_DEVICE_NAME_EQUALS_TO_TOOLTIP_IN_DROPDOWN, deviceName, selected, tooltip);

        verifyTextIgnoreCase(selected, tooltip, message);
    }

    public void assertCameraToggleAvailable() {
        assertTrue(page.isCameraToggleAvailable(AJAX_TIMEOUT), CAMERA_TOGGLE_AVAILABLE);
    }

    public void verifyCameraToggleAvailable() {
        verifyTrue(page.isCameraToggleAvailable(AJAX_TIMEOUT), CAMERA_TOGGLE_AVAILABLE);
    }

    public void assertMicrophoneToggleAvailable() {
        assertTrue(page.isMicrophoneToggleAvailable(AJAX_TIMEOUT), MICROPHONE_TOGGLE_AVAILABLE);
    }

    public void verifyMicrophoneToggleAvailable() {
        verifyTrue(page.isMicrophoneToggleAvailable(AJAX_TIMEOUT), MICROPHONE_TOGGLE_AVAILABLE);
    }

    public void verifyAudioDeviceSelected() {
        verifyTrue(page.isAudioDeviceSelected(AJAX_TIMEOUT), AUDIO_DEVICE_SELECTED);
    }

    public void assertCameraOff() {
        assertTrue(page.isCameraOff(AJAX_TIMEOUT), CAMERA_OFF);
    }

    public void assertMicrophoneOff() {
        assertTrue(page.isMicrophoneOff(AJAX_TIMEOUT), MICROPHONE_OFF);
    }
}
