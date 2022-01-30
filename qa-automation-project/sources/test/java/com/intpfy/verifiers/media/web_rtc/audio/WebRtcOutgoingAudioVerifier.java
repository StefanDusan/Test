package com.intpfy.verifiers.media.web_rtc.audio;

import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.verifiers.media.web_rtc.BaseWebRtcVerifier;
import com.intpfyqa.gui.web.selenium.VerifyPage;
import com.intpfyqa.gui.web.selenium.exceptions.ElementException;
import com.intpfyqa.utils.TestUtils;
import org.openqa.selenium.NoSuchElementException;

import java.time.LocalDateTime;

import static com.intpfyqa.settings.WebRtcSettings.MEASUREMENT_TIMEOUT;

public class WebRtcOutgoingAudioVerifier extends BaseWebRtcVerifier {

    private boolean totalAudioEnergyChanging;
    private boolean audioLevelChanging;
    private boolean audioLevelGreaterThanZero;
    private boolean audioLevelEqualsZero;
    private boolean packetsSentChanging;
    private boolean bytesSentChanging;

    public WebRtcOutgoingAudioVerifier(WebRtcPage page) {
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

        String messageHeader = "Outgoing audio present.";
        if (elementException != null) {
            return createMissingElementVerification(messageHeader);
        }
        return VerifyPage.onPage(page, messageHeader)
                .booleanValueShouldBeEqual(f -> totalAudioEnergyChanging, true,
                        "'Total audio energy' value changing.")
                .booleanValueShouldBeEqual(f -> audioLevelGreaterThanZero, true,
                        "'Audio level' value is greater than 0.")
                .booleanValueShouldBeEqual(f -> packetsSentChanging, true,
                        "'Packets sent' value changing.")
                .booleanValueShouldBeEqual(f -> bytesSentChanging, true,
                        "'Bytes sent' value changing.");
    }

    private void initValuesForPresentVerification() {
        setStartValuesForPresentVerification();

        try {

            double totalAudioEnergy = page.getOutgoingAudioTotalEnergy();
            double packetsSent = page.getOutgoingAudioPacketsSent();
            double bytesSent = page.getOutgoingAudioBytesSent();

            LocalDateTime endTime = LocalDateTime.now().plus(MEASUREMENT_TIMEOUT);

            while (LocalDateTime.now().isBefore(endTime)) {
                if (!audioLevelGreaterThanZero) audioLevelGreaterThanZero = page.getOutgoingAudioLevel() > 0;
                TestUtils.sleep(150);
                if (!totalAudioEnergyChanging) totalAudioEnergyChanging = totalAudioEnergy != page.getOutgoingAudioTotalEnergy();
                if (!packetsSentChanging) packetsSentChanging = packetsSent != page.getOutgoingAudioPacketsSent();
                if (!bytesSentChanging) bytesSentChanging = bytesSent != page.getOutgoingAudioBytesSent();

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
        audioLevelGreaterThanZero = false;
        packetsSentChanging = false;
        bytesSentChanging = false;
    }

    private boolean shouldStopInitValuesForPresentVerification() {
        return totalAudioEnergyChanging && audioLevelGreaterThanZero &&
                packetsSentChanging && bytesSentChanging;
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

        String messageHeader = "Outgoing audio not present.";
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
                if (totalAudioEnergyChanging) totalAudioEnergy = page.getOutgoingAudioTotalEnergy();
                TestUtils.sleep(1000);
                if (totalAudioEnergyChanging) totalAudioEnergyChanging = totalAudioEnergy != page.getOutgoingAudioTotalEnergy();
                if (!audioLevelEqualsZero) audioLevelEqualsZero = page.getOutgoingAudioLevel() == 0;

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

        String messageHeader = "Outgoing audio present.";
        if (elementException != null) {
            return createMissingElementVerification(messageHeader);
        }
        return VerifyPage.onPage(page, messageHeader)
                .booleanValueShouldBeEqual(f -> totalAudioEnergyChanging, false,
                        "'Total audio energy' value changing.")
                .booleanValueShouldBeEqual(f -> audioLevelChanging, false,
                        "'Audio level' value changing.")
                .booleanValueShouldBeEqual(f -> packetsSentChanging, false,
                        "'Packets sent' value changing.")
                .booleanValueShouldBeEqual(f -> bytesSentChanging, false,
                        "'Bytes sent' value changing.");
    }

    private void initValuesForDisconnectedVerification() {
        setStartValuesForDisconnectedVerification();

        double totalAudioEnergy = 0;
        double audioLevel = 0;
        double packetsSent = 0;
        double bytesSent = 0;

        try {

            LocalDateTime endTime = LocalDateTime.now().plus(MEASUREMENT_TIMEOUT);

            while (LocalDateTime.now().isBefore(endTime)) {
                if (totalAudioEnergyChanging) totalAudioEnergy = page.getOutgoingAudioTotalEnergy();
                if (audioLevelChanging) audioLevel = page.getOutgoingAudioLevel();
                if (packetsSentChanging) packetsSent = page.getOutgoingAudioPacketsSent();
                if (bytesSentChanging) bytesSent = page.getOutgoingAudioBytesSent();
                TestUtils.sleep(1000);
                if (totalAudioEnergyChanging) totalAudioEnergyChanging = totalAudioEnergy != page.getOutgoingAudioTotalEnergy();
                if (audioLevelChanging) audioLevelChanging = audioLevel != page.getOutgoingAudioLevel();
                if (packetsSentChanging) packetsSentChanging = packetsSent != page.getOutgoingAudioPacketsSent();
                if (bytesSentChanging) bytesSentChanging = bytesSent != page.getOutgoingAudioBytesSent();

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
        packetsSentChanging = true;
        bytesSentChanging = true;
    }

    private boolean shouldStopInitValuesForDisconnectedVerification() {
        return !totalAudioEnergyChanging && !audioLevelChanging &&
                !packetsSentChanging && !bytesSentChanging;
    }
}
