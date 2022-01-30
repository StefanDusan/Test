package com.intpfy.verifiers.media.web_rtc;

public interface IWebRtcVerifier {

    void verifyPresent();

    void assertPresent();

    void verifyNotPresent();

    void assertNotPresent();

    void verifyDisconnected();

    void assertDisconnected();
}