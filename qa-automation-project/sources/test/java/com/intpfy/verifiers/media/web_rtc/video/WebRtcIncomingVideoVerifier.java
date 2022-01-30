package com.intpfy.verifiers.media.web_rtc.video;

import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.verifiers.media.web_rtc.BaseWebRtcVerifier;
import com.intpfyqa.gui.web.selenium.VerifyPage;
import com.intpfyqa.gui.web.selenium.exceptions.ElementException;
import com.intpfyqa.utils.TestUtils;
import org.openqa.selenium.NoSuchElementException;

import java.time.LocalDateTime;

import static com.intpfyqa.settings.WebRtcSettings.MEASUREMENT_TIMEOUT;

public class WebRtcIncomingVideoVerifier extends BaseWebRtcVerifier {

    private boolean qpSumChanging;
    private boolean packetsReceivedChanging;
    private boolean framesReceivedChanging;
    private boolean framesDecodedChanging;

    public WebRtcIncomingVideoVerifier(WebRtcPage page) {
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

        String messageHeader = "Incoming video present.";
        if (elementException != null) {
            return createMissingElementVerification(messageHeader);
        }
        return VerifyPage.onPage(page, messageHeader)
                .booleanValueShouldBeEqual(f -> qpSumChanging, true,
                        "'Qp sum' value changing.")
                .booleanValueShouldBeEqual(f -> packetsReceivedChanging, true,
                        "'Packets received' value changing.")
                .booleanValueShouldBeEqual(f -> framesReceivedChanging, true,
                        "'Frames received' value changing.")
                .booleanValueShouldBeEqual(f -> framesDecodedChanging, true,
                        "'Frames decoded' value changing.");
    }

    private void initValuesForPresentVerification() {
        setStartValuesForPresentVerification();

        try {

            double qpSum = page.getIncomingVideoQpSum();
            double packetsReceived = page.getIncomingVideoPacketsReceived();
            double framesReceived = page.getIncomingVideoFramesReceived();
            double framesDecoded = page.getIncomingVideoFramesDecoded();

            LocalDateTime endTime = LocalDateTime.now().plus(MEASUREMENT_TIMEOUT);

            while (LocalDateTime.now().isBefore(endTime)) {
                TestUtils.sleep(150);
                if (!qpSumChanging) qpSumChanging = qpSum != page.getIncomingVideoQpSum();
                if (!packetsReceivedChanging) packetsReceivedChanging = packetsReceived != page.getIncomingVideoPacketsReceived();
                if (!framesReceivedChanging) framesReceivedChanging = framesReceived != page.getIncomingVideoFramesReceived();
                if (!framesDecodedChanging) framesDecodedChanging = framesDecoded != page.getIncomingVideoFramesDecoded();

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
        qpSumChanging = false;
        packetsReceivedChanging = false;
        framesReceivedChanging = false;
        framesDecodedChanging = false;
    }

    private boolean shouldStopInitValuesForPresentVerification() {
        return qpSumChanging && packetsReceivedChanging &&
                framesReceivedChanging && framesDecodedChanging;
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

        String messageHeader = "Incoming video not present.";
        if (elementException != null) {
            return createMissingElementVerification(messageHeader);
        }
        return VerifyPage.onPage(page, messageHeader)
                .booleanValueShouldBeEqual(f -> qpSumChanging, false,
                        "'Qp sum' value changing.")
                .booleanValueShouldBeEqual(f -> packetsReceivedChanging, false,
                        "'Packets received' value changing.")
                .booleanValueShouldBeEqual(f -> framesReceivedChanging, false,
                        "'Frames received' value changing.")
                .booleanValueShouldBeEqual(f -> framesDecodedChanging, false,
                        "'Frames decoded' value changing.");
    }

    private void initValuesForNotPresentVerification() {
        setStartValuesForNotPresentVerification();

        double qpSum = 0;
        double packetsReceived = 0;
        double framesReceived = 0;
        double framesDecoded = 0;

        try {

            LocalDateTime endTime = LocalDateTime.now().plus(MEASUREMENT_TIMEOUT);

            while (LocalDateTime.now().isBefore(endTime)) {
                if (qpSumChanging) qpSum = page.getIncomingVideoQpSum();
                if (packetsReceivedChanging) packetsReceived = page.getIncomingVideoPacketsReceived();
                if (framesReceivedChanging) framesReceived = page.getIncomingVideoFramesReceived();
                if (framesDecodedChanging) framesDecoded = page.getIncomingVideoFramesDecoded();
                TestUtils.sleep(1000);
                if (qpSumChanging) qpSumChanging = qpSum != page.getIncomingVideoQpSum();
                if (packetsReceivedChanging) packetsReceivedChanging = packetsReceived != page.getIncomingVideoPacketsReceived();
                if (framesReceivedChanging) framesReceivedChanging = framesReceived != page.getIncomingVideoFramesReceived();
                if (framesDecodedChanging) framesDecodedChanging = framesDecoded != page.getIncomingVideoFramesDecoded();

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
        qpSumChanging = true;
        packetsReceivedChanging = true;
        framesReceivedChanging = true;
        framesDecodedChanging = true;
    }

    private boolean shouldStopInitValuesForNotPresentVerification() {
        return !qpSumChanging && !packetsReceivedChanging &&
                !framesReceivedChanging && !framesDecodedChanging;
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

        String messageHeader = "Incoming video disconnected.";
        if (elementException != null) {
            return createMissingElementVerification(messageHeader);
        }
        return VerifyPage.onPage(page, messageHeader)
                .booleanValueShouldBeEqual(f -> qpSumChanging, false,
                        "'Qp sum' value changing.")
                .booleanValueShouldBeEqual(f -> packetsReceivedChanging, false,
                        "'Packets received' value changing.")
                .booleanValueShouldBeEqual(f -> framesReceivedChanging, false,
                        "'Frames received' value changing.")
                .booleanValueShouldBeEqual(f -> framesDecodedChanging, false,
                        "'Frames encoded' value changing.");
    }

    private void initValuesForDisconnectedVerification() {
        setStartValuesForDisconnectedVerification();

        double qpSum = 0;
        double packetsReceived = 0;
        double framesReceived = 0;
        double framesDecoded = 0;

        try {

            LocalDateTime endTime = LocalDateTime.now().plus(MEASUREMENT_TIMEOUT);

            while (LocalDateTime.now().isBefore(endTime)) {
                if (qpSumChanging) qpSum = page.getIncomingVideoQpSum();
                if (packetsReceivedChanging) packetsReceived = page.getIncomingVideoPacketsReceived();
                if (framesReceivedChanging) framesReceived = page.getIncomingVideoFramesReceived();
                if (framesDecodedChanging) framesDecoded = page.getIncomingVideoFramesDecoded();
                TestUtils.sleep(1000);
                if (qpSumChanging) qpSumChanging = qpSum != page.getIncomingVideoQpSum();
                if (packetsReceivedChanging) packetsReceivedChanging = packetsReceived != page.getIncomingVideoPacketsReceived();
                if (framesReceivedChanging) framesReceivedChanging = framesReceived != page.getIncomingVideoFramesReceived();
                if (framesDecodedChanging) framesDecodedChanging = framesDecoded != page.getIncomingVideoFramesDecoded();

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
        qpSumChanging = true;
        packetsReceivedChanging = true;
        framesReceivedChanging = true;
        framesDecodedChanging = true;
    }

    private boolean shouldStopInitValuesForDisconnectedVerification() {
        return !qpSumChanging && !packetsReceivedChanging &&
                !framesReceivedChanging && !framesDecodedChanging;
    }
}
