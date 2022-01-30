package com.intpfy.gui.components.web_rtc.audio.incoming;


import com.intpfy.gui.components.web_rtc.audio.BaseWebRtcAudioComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.elements.util.matchers.VisibleOnlyMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;


public class WebRtcIncomingAudioComponent extends BaseWebRtcAudioComponent {

    @ElementInfo(name = "Packets received", findBy = @FindBy(xpath = ".//td[text() = 'packetsReceived']/following-sibling::td"))
    private Element packetsReceivedElement;

    @ElementInfo(name = "Packets received per second", findBy = @FindBy(xpath = ".//td[text() = '[packetsReceived/s]']/following-sibling::td"))
    private Element packetsReceivedPerSecondElement;

    @ElementInfo(name = "Bytes received", findBy = @FindBy(xpath = ".//td[text() = 'bytesReceived']/following-sibling::td"))
    private Element bytesReceivedElement;

    @ElementInfo(name = "Bytes received per second", findBy = @FindBy(xpath = ".//td[text() = '[bytesReceived_in_bits/s]']/following-sibling::td"))
    private Element bytesReceivedPerSecondElement;

    public WebRtcIncomingAudioComponent(IParent parent) {
        super("WebRTC incoming audio component", parent,
                By.xpath(".//details[./summary[contains(text(), 'RTCInboundRTPAudioStream')]]"), new VisibleOnlyMatcher());
    }

    public double getPacketsReceived() {
        return getElementValue(packetsReceivedElement);
    }

    public double getPacketsReceivedPerSecond() {
        return getElementValue(packetsReceivedPerSecondElement);
    }

    public double getBytesReceived() {
        return getElementValue(bytesReceivedElement);
    }

    public double getBytesReceivedPerSecond() {
        return getElementValue(bytesReceivedPerSecondElement);
    }
}
