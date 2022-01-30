package com.intpfy.model.event;

public class Toggles {

    private final boolean allowPeerToPeer;
    private final boolean allowRtmpStreaming;
    private final boolean videoConferenceIntegrationEnabled;
    private final boolean manageIpTracking;
    private final boolean transcriptsNotification;
    private final boolean recordingNotification;
    private final boolean machineTranslationAccess;
    private final boolean languageLaboratory;
    private final boolean allowNoMicJoin;
    private final boolean lobbyEnabled;
    private final boolean documentSharing;
    private final boolean transcriptAccess;
    private final boolean audienceAutoVolumeExtended;
    private final boolean mobileSpeakersBlocked;
    private final boolean tokBoxDataEncryption;
    private final boolean enablePeerToPeer;
    private final boolean audienceFullScreen;
    private final boolean enableRtmpStreaming;
    private final boolean audioRoutingPanel;

    private Toggles(Builder builder) {

        this.allowPeerToPeer = builder.allowPeerToPeer;
        this.allowRtmpStreaming = builder.allowRtmpStreaming;
        this.videoConferenceIntegrationEnabled = builder.videoConferenceIntegrationEnabled;
        this.manageIpTracking = builder.manageIpTracking;
        this.transcriptsNotification = builder.transcriptsNotification;
        this.recordingNotification = builder.recordingNotification;
        this.machineTranslationAccess = builder.machineTranslationAccess;
        this.languageLaboratory = builder.languageLaboratory;
        this.allowNoMicJoin = builder.allowNoMicJoin;
        this.lobbyEnabled = builder.lobbyEnabled;
        this.documentSharing = builder.documentSharing;
        this.transcriptAccess = builder.transcriptAccess;
        this.audienceAutoVolumeExtended = builder.audienceAutoVolumeExtended;
        this.mobileSpeakersBlocked = builder.mobileSpeakersBlocked;
        this.tokBoxDataEncryption = builder.tokBoxDataEncryption;
        this.enablePeerToPeer = builder.enablePeerToPeer;
        this.audienceFullScreen = builder.audienceFullScreen;
        this.enableRtmpStreaming = builder.enableRtmpStreaming;
        this.audioRoutingPanel = builder.audioRoutingPanel;
    }

    public boolean isDocumentSharing() {
        return documentSharing;
    }

    public boolean isLobbyEnabled() {
        return lobbyEnabled;
    }

    public boolean isTranscriptAccess() {
        return transcriptAccess;
    }

    public static final class Builder {

        private boolean allowPeerToPeer;
        private boolean allowRtmpStreaming;
        private boolean videoConferenceIntegrationEnabled;
        private boolean manageIpTracking;
        private boolean transcriptsNotification;
        private boolean recordingNotification;
        private boolean machineTranslationAccess;
        private boolean languageLaboratory;
        private boolean allowNoMicJoin;
        private boolean lobbyEnabled = false;
        private boolean documentSharing = false;
        private boolean transcriptAccess = false;
        private boolean audienceAutoVolumeExtended;
        private boolean mobileSpeakersBlocked;
        private boolean tokBoxDataEncryption;
        private boolean enablePeerToPeer;
        private boolean audienceFullScreen;
        private boolean enableRtmpStreaming;
        private boolean audioRoutingPanel;

        public Builder withDocumentSharing(boolean documentSharing) {
            this.documentSharing = documentSharing;
            return this;
        }


        public Builder withLobbyRoom(boolean lobbyEnabled) {
            this.lobbyEnabled = lobbyEnabled;
            return this;
        }

        public Builder withTranscriptAccess(boolean transcriptAccess) {
            this.transcriptAccess = transcriptAccess;
            return this;
        }

        public Toggles build() {
            return new Toggles(this);
        }

    }
}
