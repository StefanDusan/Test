package com.intpfy.verifiers.media.web_rtc;

import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.gui.pages.web_rtc.WebRtcPage;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfy.verifiers.media.web_rtc.audio.WebRtcIncomingAudioVerifier;
import com.intpfy.verifiers.media.web_rtc.audio.WebRtcOutgoingAudioVerifier;
import com.intpfy.verifiers.media.web_rtc.video.WebRtcIncomingVideoVerifier;
import com.intpfy.verifiers.media.web_rtc.video.WebRtcOutgoingVideoVerifier;
import com.intpfyqa.test.Verify;

import static com.intpfyqa.settings.WebRtcSettings.MEASUREMENT_TIMEOUT;
import static com.intpfyqa.settings.WebRtcSettings.STATS_TABS_DISPLAYING_TIMEOUT;

public class WebRtcVerifier {

    private static final String NO_STREAMS_PRESENT_VERIFICATION_MESSAGE = "No streams present.";

    private final WebRtcPage page;

    private final IWebRtcVerifier incomingAudioVerifier;
    private final IWebRtcVerifier outgoingAudioVerifier;
    private final IWebRtcVerifier incomingVideoVerifier;
    private final IWebRtcVerifier outgoingVideoVerifier;

    public WebRtcVerifier(WebRtcPage page) {
        this.page = page;
        incomingAudioVerifier = new WebRtcIncomingAudioVerifier(page);
        outgoingAudioVerifier = new WebRtcOutgoingAudioVerifier(page);
        incomingVideoVerifier = new WebRtcIncomingVideoVerifier(page);
        outgoingVideoVerifier = new WebRtcOutgoingVideoVerifier(page);
    }

    public void verifyIncomingAudioPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openIncomingAudioStats();
        incomingAudioVerifier.verifyPresent();
    }

    public void assertIncomingAudioPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openIncomingAudioStats();
        incomingAudioVerifier.assertPresent();
    }

    public void verifyOutgoingAudioPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openOutgoingAudioSourceStats();
        page.openOutgoingAudioStats();
        outgoingAudioVerifier.verifyPresent();
    }

    public void assertOutgoingAudioPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openOutgoingAudioSourceStats();
        page.openOutgoingAudioStats();
        outgoingAudioVerifier.assertPresent();
    }

    public void verifyIncomingAudioNotPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openIncomingAudioStats();
        incomingAudioVerifier.verifyNotPresent();
    }

    public void assertIncomingAudioNotPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openIncomingAudioStats();
        incomingAudioVerifier.assertNotPresent();
    }

    public void verifyOutgoingAudioNotPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openOutgoingAudioSourceStats();
        outgoingAudioVerifier.verifyNotPresent();
    }

    public void assertOutgoingAudioNotPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openOutgoingAudioSourceStats();
        outgoingAudioVerifier.assertNotPresent();
    }

    public void verifyIncomingAudioDisconnected(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openIncomingAudioStats();
        incomingAudioVerifier.verifyDisconnected();
    }

    public void assertIncomingAudioDisconnected(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openIncomingAudioStats();
        incomingAudioVerifier.assertDisconnected();
    }

    public void verifyOutgoingAudioDisconnected(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openOutgoingAudioSourceStats();
        page.openOutgoingAudioStats();
        outgoingAudioVerifier.verifyDisconnected();
    }

    public void assertOutgoingAudioDisconnected(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openOutgoingAudioSourceStats();
        page.openOutgoingAudioStats();
        outgoingAudioVerifier.assertDisconnected();
    }

    public void verifyIncomingVideoPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openIncomingVideoStats();
        incomingVideoVerifier.verifyPresent();
    }

    public void assertIncomingVideoPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openIncomingVideoStats();
        incomingVideoVerifier.assertPresent();
    }

    public void verifyOutgoingVideoPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openOutgoingVideoStats();
        outgoingVideoVerifier.verifyPresent();
    }

    public void assertOutgoingVideoPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openOutgoingVideoStats();
        outgoingVideoVerifier.assertPresent();
    }

    public void verifyIncomingVideoNotPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openIncomingVideoStats();
        incomingVideoVerifier.verifyNotPresent();
    }

    public void assertIncomingVideoNotPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openIncomingVideoStats();
        incomingVideoVerifier.assertNotPresent();
    }

    public void verifyOutgoingVideoNotPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openOutgoingVideoStats();
        outgoingVideoVerifier.verifyNotPresent();
    }

    public void assertOutgoingVideoNotPresent(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openOutgoingVideoStats();
        outgoingVideoVerifier.assertNotPresent();
    }

    public void verifyIncomingVideoDisconnected(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openIncomingVideoStats();
        incomingVideoVerifier.verifyDisconnected();
    }

    public void assertIncomingVideoDisconnected(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openIncomingVideoStats();
        incomingVideoVerifier.assertDisconnected();
    }

    public void verifyOutgoingVideoDisconnected(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openOutgoingVideoStats();
        outgoingVideoVerifier.verifyDisconnected();
    }

    public void assertOutgoingVideoDisconnected(int streamIndex) {
        page.openStatsTab(streamIndex);
        page.openOutgoingVideoStats();
        outgoingVideoVerifier.assertDisconnected();
    }

    public void verifyNoStreamsPresent() {
        Verify.verifyTrue(areNoStreamsPresent(), NO_STREAMS_PRESENT_VERIFICATION_MESSAGE);
    }

    public void assertNoStreamsPresent() {
        Verify.assertTrue(areNoStreamsPresent(), NO_STREAMS_PRESENT_VERIFICATION_MESSAGE);
    }

    private boolean areNoStreamsPresent() {
        return page.waitStatsTabsCountToBe(0, STATS_TABS_DISPLAYING_TIMEOUT);
    }

    public void verifyIncomingAudioAndVideoPresentInStreams(int... streamsIndexes) {
        for (int i = 1; i <= streamsIndexes.length; i++) {
            verifyIncomingAudioAndVideoPresent(i);
        }
    }

    public void assertIncomingAudioAndVideoPresentOnStatsTabs(int tabsCount) {
        for (int i = 1; i <= tabsCount; i++) {
            assertIncomingAudioAndVideoPresent(i);
        }
    }

    public void verifyIncomingAudioAndVideoPresent(int streamIndex) {
        verifyIncomingAudioPresent(streamIndex);
        verifyIncomingVideoPresent(streamIndex);
    }

    public void assertIncomingAudioAndVideoPresent(int streamIndex) {
        assertIncomingAudioPresent(streamIndex);
        assertIncomingVideoPresent(streamIndex);
    }

    public void verifyOutgoingAudioAndVideoPresent(int streamIndex) {
        verifyOutgoingAudioPresent(streamIndex);
        verifyOutgoingVideoPresent(streamIndex);
    }

    public void assertOutgoingAudioAndVideoPresent(int streamIndex) {
        assertOutgoingAudioPresent(streamIndex);
        assertOutgoingVideoPresent(streamIndex);
    }

    public void verifyOutgoingAudioAndVideoNotPresent(int streamIndex) {
        verifyOutgoingAudioNotPresent(streamIndex);
        verifyOutgoingVideoNotPresent(streamIndex);
    }

    public void assertOutgoingAudioAndVideoNotPresent(int streamIndex) {
        assertOutgoingAudioNotPresent(streamIndex);
        assertOutgoingVideoNotPresent(streamIndex);
    }

    public void verifyOutgoingAudioAndVideoDisconnected(int streamIndex) {
        verifyOutgoingAudioDisconnected(streamIndex);
        verifyOutgoingVideoDisconnected(streamIndex);
    }

    public void assertOutgoingAudioAndVideoDisconnected(int streamIndex) {
        assertOutgoingAudioDisconnected(streamIndex);
        assertOutgoingVideoDisconnected(streamIndex);
    }

    public void verifyStreamsCount(int count) {
        String message = String.format("Streams count is '%s'.", count);
        Verify.verifyTrue(page.waitStatsTabsCountToBe(count, STATS_TABS_DISPLAYING_TIMEOUT), message);
    }

    public void assertStreamsCount(int count) {
        String message = String.format("Streams count is '%s'.", count);
        Verify.assertTrue(page.waitStatsTabsCountToBe(count, STATS_TABS_DISPLAYING_TIMEOUT), message);
    }

    public void assertInterpreterCanIncreaseVolumeLevelForIncomingChannel(InterpreterPage interpreterPage, int streamIndex) {

        interpreterPage.setMinVolumeLevelForIncomingChannel();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);

        interpreterVerifier.assertVolumeLevelIsMinForIncomingChannel();

        double minVolumeTotalAudioEnergyDelta = page.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, streamIndex);

        interpreterPage.setMaxVolumeLevelForIncomingChannel();
        interpreterVerifier.assertVolumeLevelIsMaxForIncomingChannel();

        double maxVolumeTotalAudioEnergyDelta = page.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, streamIndex);

        assertVolumeLevelGreater(minVolumeTotalAudioEnergyDelta, maxVolumeTotalAudioEnergyDelta,
                "Volume level increased for Incoming channel.");
    }

    public void assertInterpreterCanIncreaseVolumeLevelForOutgoingChannel(InterpreterPage interpreterPage, int streamIndex) {
        interpreterPage.setMinVolumeLevelForOutgoingChannel();
        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertVolumeLevelIsMinForOutgoingChannel();

        double minVolumeTotalAudioEnergyDelta = page.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, streamIndex);

        interpreterPage.setMaxVolumeLevelForOutgoingChannel();
        interpreterVerifier.assertVolumeLevelIsMaxForOutgoingChannel();

        double maxVolumeTotalAudioEnergyDelta = page.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, streamIndex);

        assertVolumeLevelGreater(minVolumeTotalAudioEnergyDelta, maxVolumeTotalAudioEnergyDelta,
                "Volume level increased for Outgoing channel.");
    }

    public void assertSpeakerCanIncreaseVolumeLevelForLanguageChannel(SpeakerPage speakerPage, int streamIndex) {
        speakerPage.setMinVolumeLevelForLanguageChannel();
        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertVolumeLevelIsMinForLanguageChannel();

        double minVolumeTotalAudioEnergyDelta = page.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, streamIndex);

        speakerPage.setMaxVolumeLevelForLanguageChannel();
        speakerVerifier.assertVolumeLevelIsMaxForLanguageChannel();

        double maxVolumeTotalAudioEnergyDelta = page.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, streamIndex);

        assertVolumeLevelGreater(minVolumeTotalAudioEnergyDelta, maxVolumeTotalAudioEnergyDelta,
                "Volume level increased for Language channel.");
    }

    public void assertSpeakerCanIncreaseVolumeLevelForSourceChannel(SpeakerPage speakerPage, int streamIndex) {
        speakerPage.setMinVolumeLevelForSourceChannel();
        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertVolumeLevelIsMinForSourceChannel();

        double minVolumeTotalAudioEnergyDelta = page.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, streamIndex);

        speakerPage.setMaxVolumeLevelForSourceChannel();
        speakerVerifier.assertVolumeLevelIsMaxForSourceChannel();

        double maxVolumeTotalAudioEnergyDelta = page.getIncomingAudioTotalEnergyDelta(MEASUREMENT_TIMEOUT, streamIndex);

        assertVolumeLevelGreater(minVolumeTotalAudioEnergyDelta, maxVolumeTotalAudioEnergyDelta,
                "Volume level increased for Source channel.");
    }

    public void assertVolumeLevelGreater(double lowTotalAudioEnergyDelta, double highTotalAudioEnergyDelta, String message) {
        message = createVolumeLevelGreaterVerificationMessage(message, lowTotalAudioEnergyDelta, highTotalAudioEnergyDelta);
        Verify.assertTrue(isVolumeLevelGreater(lowTotalAudioEnergyDelta, highTotalAudioEnergyDelta), message);
    }

    private String createVolumeLevelGreaterVerificationMessage(String baseMessage, double lowTotalAudioEnergyDelta, double highTotalAudioEnergyDelta) {
        return baseMessage +
                "\n" + "Low total audio energy: " + lowTotalAudioEnergyDelta +
                "\n" + "High total audio energy: " + highTotalAudioEnergyDelta;
    }

    public void assertVolumeLevelEqual(double firstTotalAudioEnergyDelta, double secondTotalAudioEnergyDelta, String message) {
        message = createVolumeLevelEqualVerificationMessage(message, firstTotalAudioEnergyDelta, secondTotalAudioEnergyDelta);
        Verify.assertTrue(isVolumeLevelEqual(firstTotalAudioEnergyDelta, secondTotalAudioEnergyDelta), message);
    }

    public void verifyVolumeLevelEqual(double firstTotalAudioEnergyDelta, double secondTotalAudioEnergyDelta, String message) {
        String verificationMessage = createVolumeLevelEqualVerificationMessage(message, firstTotalAudioEnergyDelta, secondTotalAudioEnergyDelta);
        Verify.verifyTrue(isVolumeLevelEqual(firstTotalAudioEnergyDelta, secondTotalAudioEnergyDelta), verificationMessage);
    }

    private String createVolumeLevelEqualVerificationMessage(String baseMessage, double firstTotalAudioEnergyDelta, double secondTotalAudioEnergyDelta) {
        return baseMessage +
                "\n" + "First total audio energy delta: " + firstTotalAudioEnergyDelta +
                "\n" + "Second total audio energy delta: " + secondTotalAudioEnergyDelta;
    }

    private boolean isVolumeLevelGreater(double lowTotalAudioEnergyDelta, double highTotalAudioEnergyDelta) {
        if (lowTotalAudioEnergyDelta >= highTotalAudioEnergyDelta) {
            return false;
        }
        double minDifference = 10;
        return minDifference < (highTotalAudioEnergyDelta / lowTotalAudioEnergyDelta);
    }

    private boolean isVolumeLevelEqual(double firstTotalAudioEnergyDelta, double secondTotalAudioEnergyDelta) {
        if (firstTotalAudioEnergyDelta == secondTotalAudioEnergyDelta) {
            return true;
        }
        double maxDifference = 2.5;
        if (firstTotalAudioEnergyDelta < secondTotalAudioEnergyDelta) {
            return (secondTotalAudioEnergyDelta / firstTotalAudioEnergyDelta) < maxDifference;
        }
        return (firstTotalAudioEnergyDelta / secondTotalAudioEnergyDelta) < maxDifference;
    }

    public void assertInterpreterCanMuteIncomingAndOutgoingChannels(InterpreterPage interpreterPage, int incomingStreamIndex, int outgoingStreamIndex) {
        assertInterpreterCanMuteIncomingChannel(interpreterPage, incomingStreamIndex);
        assertInterpreterCanMuteOutgoingChannel(interpreterPage, outgoingStreamIndex);
    }

    private void assertInterpreterCanMuteIncomingChannel(InterpreterPage interpreterPage, int streamIndex) {

        interpreterPage.muteIncomingChannel();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertIncomingChannelMuted();

        assertIncomingAudioNotPresent(streamIndex);
    }

    private void assertInterpreterCanMuteOutgoingChannel(InterpreterPage interpreterPage, int streamIndex) {

        interpreterPage.muteOutgoingChannel();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertOutgoingChannelMuted();

        assertIncomingAudioNotPresent(streamIndex);
    }

    public void assertSpeakerCanMuteLanguageChannel(SpeakerPage speakerPage, int streamIndex) {
        speakerPage.muteLanguageChannel();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertAudioNotPresentInLanguageChannel();

        assertIncomingAudioNotPresent(streamIndex);
    }

    public void assertSpeakerCanMuteSourceChannel(SpeakerPage speakerPage, int streamIndex) {
        speakerPage.muteSourceChannel();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertAudioNotPresentInSourceChannel();

        assertIncomingAudioNotPresent(streamIndex);
    }

    public void assertSpeakerCanMuteSourceAndLanguageChannels(SpeakerPage speakerPage, int sourceStreamIndex, int languageStreamIndex) {
        assertSpeakerCanMuteSourceChannel(speakerPage, sourceStreamIndex);
        assertSpeakerCanMuteLanguageChannel(speakerPage, languageStreamIndex);
    }

    public void assertInterpreterCanUnmuteIncomingAndOutgoingChannels(InterpreterPage interpreterPage, int incomingStreamIndex, int outgoingStreamIndex) {
        assertInterpreterCanUnmuteIncomingChannel(interpreterPage, incomingStreamIndex);
        assertInterpreterCanUnmuteOutgoingChannel(interpreterPage, outgoingStreamIndex);
    }

    private void assertInterpreterCanUnmuteIncomingChannel(InterpreterPage interpreterPage, int streamIndex) {

        interpreterPage.unmuteIncomingChannel();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertIncomingChannelUnmuted();

        assertIncomingAudioPresent(streamIndex);
    }

    private void assertInterpreterCanUnmuteOutgoingChannel(InterpreterPage interpreterPage, int streamIndex) {

        interpreterPage.unmuteOutgoingChannel();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertOutgoingChannelUnmuted();

        assertIncomingAudioPresent(streamIndex);
    }

    public void assertSpeakerCanUnmuteLanguageChannel(SpeakerPage speakerPage, int streamIndex) {
        speakerPage.unmuteLanguageChannel();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertLanguageChannelUnmuted();

        assertIncomingAudioPresent(streamIndex);
    }

    public void assertSpeakerCanUnmuteSourceChannel(SpeakerPage speakerPage, int streamIndex) {
        speakerPage.unmuteSourceChannel();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertSourceChannelUnmuted();

        assertIncomingAudioPresent(streamIndex);
    }

    public void assertSpeakerCanUnmuteSourceAndLanguageChannels(SpeakerPage speakerPage, int sourceStreamIndex, int languageStreamIndex) {
        assertSpeakerCanUnmuteSourceChannel(speakerPage, sourceStreamIndex);
        assertSpeakerCanUnmuteLanguageChannel(speakerPage, languageStreamIndex);
    }
}
