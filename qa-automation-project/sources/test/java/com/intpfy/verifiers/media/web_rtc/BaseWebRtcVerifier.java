package com.intpfy.verifiers.media.web_rtc;

import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfyqa.gui.web.selenium.VerifyPage;
import com.intpfyqa.gui.web.selenium.exceptions.ElementException;

public abstract class BaseWebRtcVerifier implements IWebRtcVerifier {

    protected final WebRtcPage page;
    protected ElementException elementException;

    public BaseWebRtcVerifier(WebRtcPage page) {
        this.page = page;
    }

    protected VerifyPage<WebRtcPage> createMissingElementVerification(String messageHeader) {
        String message = String.format("Element '%s' is present.", elementException.getSource().getLogicalName());
        return VerifyPage.onPage(page, messageHeader).booleanValueShouldBeEqual(b -> false, true, message);
    }
}
