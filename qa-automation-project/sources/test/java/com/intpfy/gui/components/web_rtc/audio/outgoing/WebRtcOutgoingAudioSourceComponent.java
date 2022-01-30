package com.intpfy.gui.components.web_rtc.audio.outgoing;


import com.intpfy.gui.components.web_rtc.audio.BaseWebRtcAudioComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.elements.util.matchers.VisibleOnlyMatcher;
import org.openqa.selenium.By;


public class WebRtcOutgoingAudioSourceComponent extends BaseWebRtcAudioComponent {

    public WebRtcOutgoingAudioSourceComponent(IParent parent) {
        super("WebRTC outgoing (source) audio component", parent,
                By.xpath(".//details[./summary[contains(text(), 'RTCAudioSource')]]"), new VisibleOnlyMatcher());
    }
}