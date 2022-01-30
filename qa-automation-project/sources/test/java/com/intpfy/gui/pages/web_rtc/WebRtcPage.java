package com.intpfy.gui.pages.web_rtc;

import com.intpfy.gui.components.web_rtc.audio.incoming.WebRtcIncomingAudioComponent;
import com.intpfy.gui.components.web_rtc.audio.outgoing.WebRtcOutgoingAudioComponent;
import com.intpfy.gui.components.web_rtc.audio.outgoing.WebRtcOutgoingAudioSourceComponent;
import com.intpfy.gui.components.web_rtc.video.WebRtcIncomingVideoComponent;
import com.intpfy.gui.components.web_rtc.video.WebRtcOutgoingVideoComponent;
import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.factories.WebFactoryHelper;
import com.intpfyqa.settings.WebSettings;
import com.intpfyqa.test.Verify;
import com.intpfyqa.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class WebRtcPage extends BaseAutomationPage {

    private static final By GENERIC_STATS_TAB_LOCATOR = By.xpath("//span[contains(@class, 'tab-head') and contains(text(), 'inter')]");

    @ElementInfo(name = "Create dump", findBy = @FindBy(xpath = "//summary[text()='Create Dump']"))
    private Element createDumpElement;

    private final WebRtcIncomingAudioComponent webRtcIncomingAudioComponent;
    private final WebRtcOutgoingAudioComponent webRtcOutgoingAudioComponent;
    private final WebRtcOutgoingAudioSourceComponent webRtcOutgoingAudioSourceComponent;
    private final WebRtcIncomingVideoComponent webRtcIncomingVideoComponent;
    private final WebRtcOutgoingVideoComponent webRtcOutgoingVideoComponent;

    public WebRtcPage(IPageContext pageContext) {
        super("WebRTC page", pageContext);
        webRtcIncomingAudioComponent = new WebRtcIncomingAudioComponent(this);
        webRtcOutgoingAudioComponent = new WebRtcOutgoingAudioComponent(this);
        webRtcOutgoingAudioSourceComponent = new WebRtcOutgoingAudioSourceComponent(this);
        webRtcIncomingVideoComponent = new WebRtcIncomingVideoComponent(this);
        webRtcOutgoingVideoComponent = new WebRtcOutgoingVideoComponent(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        TestUtils.sleep(WebSettings.AJAX_TIMEOUT.toMillis());
        return createDumpElement.visible(timeout);
    }

    public void openIncomingAudioStats() {
        info("Open 'Incoming audio' statistics.");
        webRtcIncomingAudioComponent.open();
    }

    public void openOutgoingAudioSourceStats() {
        info("Open 'Outgoing source audio' statistics.");
        webRtcOutgoingAudioSourceComponent.open();
    }

    public void openOutgoingAudioStats() {
        info("Open 'Outgoing audio' statistics.");
        webRtcOutgoingAudioComponent.open();
    }

    public void openIncomingVideoStats() {
        info("Open 'Incoming video' statistics.");
        webRtcIncomingVideoComponent.open();
    }

    public void openOutgoingVideoStats() {
        info("Open 'Outgoing video' statistics.");
        webRtcOutgoingVideoComponent.open();
    }

    public double getIncomingAudioLevel() {
        return webRtcIncomingAudioComponent.getAudioLevel();
    }

    public double getIncomingAudioTotalEnergy() {
        return webRtcIncomingAudioComponent.getTotalAudioEnergy();
    }

    public double getIncomingAudioPacketsReceived() {
        return webRtcIncomingAudioComponent.getPacketsReceived();
    }

    public double getIncomingAudioPacketsReceivedPerSecond() {
        return webRtcIncomingAudioComponent.getPacketsReceivedPerSecond();
    }

    public double getIncomingAudioBytesReceived() {
        return webRtcIncomingAudioComponent.getBytesReceived();
    }

    public double getIncomingAudioBytesReceivedPerSecond() {
        return webRtcIncomingAudioComponent.getBytesReceivedPerSecond();
    }

    public double getOutgoingAudioLevel() {
        return webRtcOutgoingAudioSourceComponent.getAudioLevel();
    }

    public double getOutgoingAudioTotalEnergy() {
        return webRtcOutgoingAudioSourceComponent.getTotalAudioEnergy();
    }

    public double getOutgoingAudioPacketsSent() {
        return webRtcOutgoingAudioComponent.getPacketsSent();
    }

    public double getOutgoingAudioPacketsSentPerSecond() {
        return webRtcOutgoingAudioComponent.getPacketsSentPerSecond();
    }

    public double getOutgoingAudioBytesSent() {
        return webRtcOutgoingAudioComponent.getBytesSent();
    }

    public double getOutgoingAudioBytesSentPerSecond() {
        return webRtcOutgoingAudioComponent.getBytesSentPerSecond();
    }

    public double getIncomingVideoQpSum() {
        return webRtcIncomingVideoComponent.getQpSum();
    }

    public double getIncomingVideoPacketsReceived() {
        return webRtcIncomingVideoComponent.getPacketsReceived();
    }

    public double getIncomingVideoPacketsReceivedPerSecond() {
        return webRtcIncomingVideoComponent.getPacketsReceivedPerSecond();
    }

    public double getIncomingVideoFramesReceived() {
        return webRtcIncomingVideoComponent.getFramesReceived();
    }

    public double getIncomingVideoFramesDecoded() {
        return webRtcIncomingVideoComponent.getFramesDecoded();
    }

    public double getIncomingVideoBytesReceivedPerSecond() {
        return webRtcIncomingVideoComponent.getBytesReceivedPerSecond();
    }

    public double getOutgoingVideoQpSum() {
        return webRtcOutgoingVideoComponent.getQpSum();
    }

    public double getOutgoingVideoPacketsSent() {
        return webRtcOutgoingVideoComponent.getPacketsSent();
    }

    public double getOutgoingVideoPacketsSentPerSecond() {
        return webRtcOutgoingVideoComponent.getPacketsSentPerSecond();
    }

    public double getOutgoingVideoFramesSent() {
        return webRtcOutgoingVideoComponent.getFramesSent();
    }

    public double getOutgoingVideoFramesEncoded() {
        return webRtcOutgoingVideoComponent.getFramesEncoded();
    }

    public double getOutgoingVideoBytesSentPerSecond() {
        return webRtcOutgoingVideoComponent.getBytesSendPerSecond();
    }

    public double getIncomingAudioTotalEnergyDelta(Duration timeout, int statsTabOrdinalIndex) {
        openStatsTab(statsTabOrdinalIndex);
        webRtcIncomingAudioComponent.open();
        return webRtcIncomingAudioComponent.getTotalAudioEnergyDelta(timeout);
    }

    public boolean waitStatsTabsCountToBe(int tabsCount, Duration timeout) {
        LocalDateTime endTime = LocalDateTime.now().plus(timeout);
        do {
            if (this.children(GENERIC_STATS_TAB_LOCATOR).size() == tabsCount) {
                return true;
            }
            TestUtils.sleep(250);
        }
        while (LocalDateTime.now().isBefore(endTime));

        return false;
    }

    public void openStatsTab(int tabOrdinalIndex) {
        info(String.format("Open %s media statistics tab.", tabOrdinalIndex));
        openStatsTab(this.children(GENERIC_STATS_TAB_LOCATOR).get(tabOrdinalIndex - 1));
    }

    public void openStatsTab(String tabName) {
        info(String.format("Open media statistics tab with name '%s'.", tabName));
        openStatsTab(createStatsTabElement(tabName));
    }

    private void openStatsTab(Element tabElement) {
        tabElement.visible(WebSettings.instance().getPageTimeout());
        if (!tabElement.getCSSClass().contains("active")) {
            tabElement.click();
            Verify.assertTrue(tabElement.waitCssClassContains("active", WebSettings.AJAX_TIMEOUT),
                    "Media statistics tab is opened.");
        }
    }

    public List<String> getAllTabsNames() {
        return this.children(GENERIC_STATS_TAB_LOCATOR).stream()
                .map(Element::getText)
                .collect(Collectors.toList());
    }

    private Element createStatsTabElement(String tabName) {
        return WebFactoryHelper.getElementFactory().createElement(Element.class, "Tab " + tabName, this,
                By.xpath(String.format("//span[contains(@class, 'tab-head') and text()='%s']", tabName)));
    }
}