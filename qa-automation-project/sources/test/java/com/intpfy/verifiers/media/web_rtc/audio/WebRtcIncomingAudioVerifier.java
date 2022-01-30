package com.intpfy.verifiers.media.web_rtc.audio;

import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.verifiers.media.web_rtc.BaseWebRtcVerifier;
import com.intpfyqa.gui.web.selenium.VerifyPage;
import com.intpfyqa.gui.web.selenium.exceptions.ElementException;
import com.intpfyqa.utils.TestUtils;
import org.openqa.selenium.NoSuchElementException;

import java.time.LocalDateTime;

import static com.intpfyqa.settings.WebRtcSettings.MEASUREMENT_TIMEOUT;

public class WebRtcIncomingAudioVerifier extends BaseWebRtcVerifier {

    private boolean totalAudioEnergyChanging;
    private boolean audioLevelChanging;
    private boolean audioLevelGreaterThanZero;
    private boolean audioLevelEqualsZero;
    private boolean packetsReceivedChanging;
    private boolean bytesReceivedChanging;

    public WebRtcIncomingAudioVerifier(WebRtcPage page) {
        super(page);
    }

    @Override
    public void verifyPresent() {
        getPresentVerification().completeVerify();
    }

    @Override
    public void assertPresent() {
        getPresentVerification().completeAssert();
    }

    private VerifyPage<WebRtcPage> getPresentVerification() {
        initValuesForPresentVerification();

        String messageHeader = "Incoming audio present.";
        if (elementException != null) {
            return createMissingElementVerification(messageHeader);
        }
        return VerifyPage.onPage(page, messageHeader)
                .booleanValueShouldBeEqual(f -> totalAudioEnergyChanging, true,
                        "'Total audio energy' value changing.")
                .booleanValueShouldBeEqual(f -> audioLevelGreaterThanZero, true,
                        "'Audio level' value is greater than 0.")
                .booleanValueShouldBeEqual(f -> packetsReceivedChanging, true,
                        "'Packets received' value changing.")
                .booleanValueShouldBeEqual(f -> bytesReceivedChanging, true,
                        "'Bytes received' value changing.");
    }

    private void initValuesForPresentVerification() {
        setStartValuesForPresentVerification();

        try {

            double totalAudioEnergy = page.getIncomingAudioTotalEnergy();
            double packetsReceived = page.getIncomingAudioPacketsReceived();
            double bytesReceived = page.getIncomingAudioBytesReceived();

            LocalDateTime endTime = LocalDateTime.now().plus(MEASUREMENT_TIMEOUT);

            while (LocalDateTime.now().isBefore(endTime)) {
                if (!audioLevelGreaterThanZero) audioLevelGreaterThanZero = page.getIncomingAudioLevel() > 0;
                TestUtils.sleep(150);
                if (!totalAudioEnergyChanging) totalAudioEnergyChanging = totalAudioEnergy != page.getIncomingAudioTotalEnergy();
                if (!packetsReceivedChanging) packetsReceivedChanging = packetsReceived != page.getIncomingAudioPacketsReceived();
                if (!bytesReceivedChanging) bytesReceivedChanging = bytesReceived != page.getIncomingAudioBytesReceived();

                if (shouldStopInitValuesForPresentVerification()) {
                    break;
                }
            }
        } catch (ElementException e) {
            if (e.getRootCause() instanceof NoSuchElementException) {
                elementException = e;
            } else {
                throw e;
            }
        }

    }

    private void setStartValuesForPresentVerification() {
        totalAudioEnergyChanging = false;
        packetsReceivedChanging = false;
        bytesReceivedChanging = false;
        audioLevelGreaterThanZero = false;
    }

    private boolean shouldStopInitValuesForPresentVerification() {
        return totalAudioEnergyChanging && audioLevelGreaterThanZero &&
                packetsReceivedChanging && bytesReceivedChanging;
    }

    @Override
    public void verifyNotPresent() {
        getNotPresentVerification().completeVerify();
    }

    @Override
    public void assertNotPresent() {
        getNotPresentVerification().completeAssert();
    }

    private VerifyPage<WebRtcPage> getNotPresentVerification() {
        initValuesForNotPresentVerification();

        String messageHeader = "Incoming audio not present.";
        if (elementException != null) {
            return createMissingElementVerification(messageHeader);
        }
        return VerifyPage.onPage(page, messageHeader)
                .booleanValueShouldBeEqual(f -> totalAudioEnergyChanging, false,
                        "'Total audio energy' value changing.")
                .booleanValueShouldBeEqual(f -> audioLevelEqualsZero, true,
                        "'Audio level' value is equal to 0.");
    }

    private void initValuesForNotPresentVerification() {
        setStartValuesForNotPresentVerification();

        double totalAudioEnergy = 0;

        try {

            LocalDateTime endTime = LocalDateTime.now().plus(MEASUREMENT_TIMEOUT);

            while (LocalDateTime.now().isBefore(endTime)) {
                if (totalAudioEnergyChanging) totalAudioEnergy = page.getIncomingAudioTotalEnergy();
                TestUtils.sleep(1000);
                if (totalAudioEnergyChanging) totalAudioEnergyChanging = totalAudioEnergy != page.getIncomingAudioTotalEnergy();
                if (!audioLevelEqualsZero) audioLevelEqualsZero = page.getIncomingAudioLevel() == 0;

                if (shouldStopInitValuesForNotPresentVerification()) {
                    break;
                }
            }
        } catch (ElementException e) {
            if (e.getRootCause() instanceof NoSuchElementException) {
                elementException = e;
            } else {
                throw e;
            }
        }
    }

    private void setStartValuesForNotPresentVerification() {
        totalAudioEnergyChanging = true;
        audioLevelEqualsZero = false;
    }

    private boolean shouldStopInitValuesForNotPresentVerification() {
        return !totalAudioEnergyChanging && audioLevelEqualsZero;
    }

    @Override
    public void verifyDisconnected() {
        getDisconnectedVerification().completeVerify();
    }

    @Override
    public void assertDisconnected() {
        getDisconnectedVerification().completeAssert();
    }

    private VerifyPage<WebRtcPage> getDisconnectedVerification() {
        initValuesForDisconnectedVerification();

        String messageHeader = "Incoming audio disconnected.";
        if (elementException != null) {
            return createMissingElementVerification(messageHeader);
        }
        return VerifyPage.onPage(page, messageHeader)
                .booleanValueShouldBeEqual(f -> totalAudioEnergyChanging, false,
                        "'Total audio energy' value changing.")
                .booleanValueShouldBeEqual(f -> audioLevelChanging, false,
                        "'Audio level' value changing.")
                .booleanValueShouldBeEqual(f -> packetsReceivedChanging, false,
                        "'Packets received' value changing.")
                .booleanValueShouldBeEqual(f -> bytesReceivedChanging, false,
                        "'Bytes received' value changing.");
    }

    private void initValuesForDisconnectedVerification() {
        setStartValuesForDisconnectedVerification();

        double totalAudioEnergy = 0;
        double audioLevel = 0;
        double packetsReceived = 0;
        double bytesReceived = 0;

        try {

            LocalDateTime endTime = LocalDateTime.now().plus(MEASUREMENT_TIMEOUT);

            while (LocalDateTime.now().isBefore(endTime)) {
                if (totalAudioEnergyChanging) totalAudioEnergy = page.getIncomingAudioTotalEnergy();
                if (audioLevelChanging) audioLevel = page.getIncomingAudioLevel();
                if (packetsReceivedChanging) packetsReceived = page.getIncomingAudioPacketsReceived();
                if (bytesReceivedChanging) bytesReceived = page.getIncomingAudioBytesReceived();
                TestUtils.sleep(1000);
                if (totalAudioEnergyChanging) totalAudioEnergyChanging = totalAudioEnergy != page.getIncomingAudioTotalEnergy();
                if (audioLevelChanging) audioLevelChanging = audioLevel != page.getIncomingAudioLevel();
                if (packetsReceivedChanging) packetsReceivedChanging = packetsReceived != page.getIncomingAudioPacketsReceived();
                if (bytesReceivedChanging) bytesReceivedChanging = bytesReceived != page.getIncomingAudioBytesReceived();

                if (shouldStopInitValuesForDisconnectedVerification()) {
                    break;
                }
            }
        } catch (ElementException e) {
            if (e.getRootCause() instanceof NoSuchElementException) {
                elementException = e;
            } else {
                throw e;
            }
        }
    }

    private void setStartValuesForDisconnectedVerification() {
        totalAudioEnergyChanging = true;
        audioLevelChanging = true;
        packetsReceivedChanging = true;
        bytesReceivedChanging = true;
    }

    private boolean shouldStopInitValuesForDisconnectedVerification() {
        return !totalAudioEnergyChanging && !audioLevelChanging &&
                !packetsReceivedChanging && !bytesReceivedChanging;
    }
}
