package com.intpfy.gui.components.web_rtc.video;

import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.matchers.VisibleOnlyMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class WebRtcOutgoingVideoComponent extends BaseWebRtcVideoComponent {

    @ElementInfo(name = "Packets sent", findBy = @FindBy(xpath = ".//td[text() = 'packetsSent']/following-sibling::td"))
    private Element packetsSentElement;

    @ElementInfo(name = "Packets sent per second", findBy = @FindBy(xpath = ".//td[text() = '[packetsSent/s]']/following-sibling::td"))
    private Element packetsSentPerSecondElement;

    @ElementInfo(name = "Frames sent", findBy = @FindBy(xpath = ".//td[text() = 'framesSent']/following-sibling::td"))
    private Element framesSentElement;

    @ElementInfo(name = "Frames encoded", findBy = @FindBy(xpath = ".//td[text() = 'framesEncoded']/following-sibling::td"))
    private Element framesEncodedElement;

    @ElementInfo(name = "Bytes sent per second", findBy = @FindBy(xpath = ".//td[text() = '[bytesSent_in_bits/s]']/following-sibling::td"))
    private Element bytesSentPerSecondElement;

    public WebRtcOutgoingVideoComponent(IParent parent) {
        super("WebRTC outgoing video component", parent,
                By.xpath(".//details[./summary[not(text() = 'RTCOutboundRTPVideoStream_10001 (outbound-rtp)') and " +
                        "contains(text(), 'RTCOutboundRTPVideoStream')]]"), new VisibleOnlyMatcher());
    }

    public double getPacketsSent() {
        return getElementValue(packetsSentElement);
    }

    public double getPacketsSentPerSecond() {
        return getElementValue(packetsSentPerSecondElement);
    }

    public double getFramesSent() {
        return getElementValue(framesSentElement);
    }

    public double getFramesEncoded() {
        return getElementValue(framesEncodedElement);
    }

    public double getBytesSendPerSecond() {
        return getElementValue(bytesSentPerSecondElement);
    }
}
