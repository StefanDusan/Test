package com.intpfy.gui.components.web_rtc.audio.outgoing;


import com.intpfy.gui.components.web_rtc.audio.BaseWebRtcAudioComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.matchers.VisibleOnlyMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;


public class WebRtcOutgoingAudioComponent extends BaseWebRtcAudioComponent {

    @ElementInfo(name = "Packets sent", findBy = @FindBy(xpath = ".//td[text() = 'packetsSent']/following-sibling::td"))
    private Element packetsSentElement;

    @ElementInfo(name = "Packets sent per second", findBy = @FindBy(xpath = ".//td[text() = '[packetsSent/s]']/following-sibling::td"))
    private Element packetsSentPerSecondElement;

    @ElementInfo(name = "Bytes sent", findBy = @FindBy(xpath = ".//td[text() = 'bytesSent']/following-sibling::td"))
    private Element bytesSentElement;

    @ElementInfo(name = "Bytes sent per second", findBy = @FindBy(xpath = ".//td[text() = '[bytesSent_in_bits/s]']/following-sibling::td"))
    private Element bytesSentPerSecondElement;

    public WebRtcOutgoingAudioComponent(IParent parent) {
        super("WebRTC outgoing audio component", parent,
                By.xpath(".//details[./summary[contains(text(), 'RTCOutboundRTPAudioStream')]]"), new VisibleOnlyMatcher());
    }

    public double getPacketsSent() {
        return getElementValue(packetsSentElement);
    }

    public double getPacketsSentPerSecond() {
        return getElementValue(packetsSentPerSecondElement);
    }

    public double getBytesSent() {
        return getElementValue(bytesSentElement);
    }

    public double getBytesSentPerSecond() {
        return getElementValue(bytesSentPerSecondElement);
    }
}
