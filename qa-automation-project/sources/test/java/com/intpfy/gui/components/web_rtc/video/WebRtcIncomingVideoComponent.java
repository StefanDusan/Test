package com.intpfy.gui.components.web_rtc.video;

import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.matchers.VisibleOnlyMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class WebRtcIncomingVideoComponent extends BaseWebRtcVideoComponent {

    @ElementInfo(name = "Packets received", findBy = @FindBy(xpath = ".//td[text() = 'packetsReceived']/following-sibling::td"))
    private Element packetsReceivedElement;

    @ElementInfo(name = "Packets received per second", findBy = @FindBy(xpath = ".//td[text() = '[packetsReceived/s]']/following-sibling::td"))
    private Element packetsReceivedPerSecondElement;

    @ElementInfo(name = "Frames received", findBy = @FindBy(xpath = ".//td[text() = 'framesReceived']/following-sibling::td"))
    private Element framesReceivedElement;

    @ElementInfo(name = "Frames decoded", findBy = @FindBy(xpath = ".//td[text() = 'framesDecoded']/following-sibling::td"))
    private Element framesDecodedElement;

    @ElementInfo(name = "Bytes received per second", findBy = @FindBy(xpath = ".//td[text() = '[bytesReceived_in_bits/s]']/following-sibling::td"))
    private Element bytesReceivedPerSecondElement;

    public WebRtcIncomingVideoComponent(IParent parent) {
        super("WebRTC incoming video component", parent,
                By.xpath(".//details[./summary[contains(text(), 'RTCInboundRTPVideoStream')]]"), new VisibleOnlyMatcher());
    }

    public double getPacketsReceived() {
        return getElementValue(packetsReceivedElement);
    }

    public double getPacketsReceivedPerSecond() {
        return getElementValue(packetsReceivedPerSecondElement);
    }

    public double getFramesReceived() {
        return getElementValue(framesReceivedElement);
    }

    public double getFramesDecoded() {
        return getElementValue(framesDecodedElement);
    }

    public double getBytesReceivedPerSecond() {
        return getElementValue(bytesReceivedPerSecondElement);
    }
}
