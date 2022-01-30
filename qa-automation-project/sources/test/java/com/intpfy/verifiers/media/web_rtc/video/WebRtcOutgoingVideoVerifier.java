package com.intpfy.verifiers.media.web_rtc.video;

import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.verifiers.media.web_rtc.BaseWebRtcVerifier;
import com.intpfyqa.gui.web.selenium.VerifyPage;
import com.intpfyqa.gui.web.selenium.exceptions.ElementException;
import com.intpfyqa.utils.TestUtils;
import org.openqa.selenium.NoSuchElementException;

import java.time.LocalDateTime;

import static com.intpfyqa.settings.WebRtcSettings.MEASUREMENT_TIMEOUT;

public class WebRtcOutgoingVideoVerifier extends BaseWebRtcVerifier {

    // 'qpSum' parameter is not used because it's not displayed if video stream is not going.
    // In such case this parameter break verifications and test execution.
    private boolean packetsSentChanging;
    private boolean framesSentChanging;
    private boolean framesEncodedChanging;

    public WebRtcOutgoingVideoVerifier(WebRtcPage page) {
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

        String messageHeader = "Outgoing video present.";
        if (elementException != null) {
            return createMissingElementVerification(messageHeader);
        }
        return VerifyPage.onPage(page, messageHeader)
                .booleanValueShouldBeEqual(f -> packetsSentChanging, true,
                        "'Packets sent' value changing.")
                .booleanValueShouldBeEqual(f -> framesSentChanging, true,
                        "'Frames sent' value changing.")
                .booleanValueShouldBeEqual(f -> framesEncodedChanging, true,
                        "'Frames encoded' value changing.");
    }

    private void initValuesForPresentVerification() {
        setStartValuesForPresentVerification();

        try {

            double packetsSent = page.getOutgoingVideoPacketsSent();
            double framesSent = page.getOutgoingVideoFramesSent();
            double framesEncoded = page.getOutgoingVideoFramesEncoded();

            LocalDateTime endTime = LocalDateTime.now().plus(MEASUREMENT_TIMEOUT);

            while (LocalDateTime.now().isBefore(endTime)) {
                TestUtils.sleep(150);
                if (!packetsSentChanging) packetsSentChanging = packetsSent != page.getOutgoingVideoPacketsSent();
                if (!framesSentChanging) framesSentChanging = framesSent != page.getOutgoingVideoFramesSent();
                if (!framesEncodedChanging) framesEncodedChanging = framesEncoded != page.getOutgoingVideoFramesEncoded();

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
        packetsSentChanging = false;
        framesSentChanging = false;
        framesEncodedChanging = false;
    }

    private boolean shouldStopInitValuesForPresentVerification() {
        return packetsSentChanging && framesSentChanging &&
                framesEncodedChanging;
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

        String messageHeader = "Outgoing video not present.";
        if (elementException != null) {
            return createMissingElementVerification(messageHeader);
        }
        return VerifyPage.onPage(page, messageHeader)
                .booleanValueShouldBeEqual(f -> packetsSentChanging, false,
                        "'Packets sent' value changing.")
                .booleanValueShouldBeEqual(f -> framesSentChanging, false,
                        "'Frames sent' value changing.")
                .booleanValueShouldBeEqual(f -> framesEncodedChanging, false,
                        "'Frames encoded' value changing.");
    }

    private void initValuesForNotPresentVerification() {
        setStartValuesForNotPresentVerification();

        double packetsSent = 0;
        double framesSent = 0;
        double framesEncoded = 0;

        try {

            LocalDateTime endTime = LocalDateTime.now().plus(MEASUREMENT_TIMEOUT);

            while (LocalDateTime.now().isBefore(endTime)) {
                if (packetsSentChanging) packetsSent = page.getOutgoingVideoPacketsSent();
                if (framesSentChanging) framesSent = page.getOutgoingVideoFramesSent();
                if (framesEncodedChanging) framesEncoded = page.getOutgoingVideoFramesEncoded();
                TestUtils.sleep(1000);
                if (packetsSentChanging) packetsSentChanging = packetsSent != page.getOutgoingVideoPacketsSent();
                if (framesSentChanging) framesSentChanging = framesSent != page.getOutgoingVideoFramesSent();
                if (framesEncodedChanging) framesEncodedChanging = framesEncoded != page.getOutgoingVideoFramesEncoded();

                if (shouldStopInitValuesForNotPresentVerification())   {
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
        packetsSentChanging = true;
        framesSentChanging = true;
        framesEncodedChanging = true;
    }

    private boolean shouldStopInitValuesForNotPresentVerification() {
        return !packetsSentChanging && !framesSentChanging &&
                !framesEncodedChanging;
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

        String messageHeader = "Outgoing video disconnected.";
        if (elementException != null) {
            return createMissingElementVerification(messageHeader);
        }
        return VerifyPage.onPage(page, messageHeader)
                .booleanValueShouldBeEqual(f -> packetsSentChanging, false,
                        "'Packets sent' value changing.")
                .booleanValueShouldBeEqual(f -> framesSentChanging, false,
                        "'Frames sent' value changing.")
                .booleanValueShouldBeEqual(f -> framesEncodedChanging, false,
                        "'Frames encoded' value changing.");
    }

    private void initValuesForDisconnectedVerification() {
        setStartValuesForDisconnectedVerification();

        double packetsSent = 0;
        double framesSent = 0;
        double framesEncoded = 0;

        try {

            LocalDateTime endTime = LocalDateTime.now().plus(MEASUREMENT_TIMEOUT);

            while (LocalDateTime.now().isBefore(endTime)) {
                if (packetsSentChanging) packetsSent = page.getOutgoingVideoPacketsSent();
                if (framesSentChanging) framesSent = page.getOutgoingVideoFramesSent();
                if (framesEncodedChanging) framesEncoded = page.getOutgoingVideoFramesEncoded();
                TestUtils.sleep(1000);
                if (packetsSentChanging) packetsSentChanging = packetsSent != page.getOutgoingVideoPacketsSent();
                if (framesSentChanging) framesSentChanging = framesSent != page.getOutgoingVideoFramesSent();
                if (framesEncodedChanging) framesEncodedChanging = framesEncoded != page.getOutgoingVideoFramesEncoded();

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
        packetsSentChanging = true;
        framesSentChanging = true;
        framesEncodedChanging = true;
    }

    private boolean shouldStopInitValuesForDisconnectedVerification() {
        return !packetsSentChanging && !framesSentChanging &&
                !framesEncodedChanging;

    }
}
