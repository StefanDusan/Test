package com.intpfy.model.event;

import com.intpfy.data.LocationTestData;
import com.intpfy.model.Language;
import com.intpfy.util.RandomUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private final boolean allowSourceAudio;
    private final boolean allowSourceVideo;
    private final boolean audienceAccessToFloor;
    private final boolean enableModeratorRecording;
    private final boolean enabledImmediateChannelSwitch;
    private final boolean simultaneouslyInterpreterPublishing;
    private final String audienceToken;
    private final String displayName;
    private final LocalDateTime endDate;
    private final List<EventPeriodsData> eventPeriodsData;
    private final String floorToken;
    private final boolean isActive;
    private final boolean isDeleted;
    private final String interpreterToken;
    private final List<Language> languages;
    private final boolean keycloakAuthentication;
    private final Location location;
    private final Object managerId;
    private final Object moderatorId;
    private final String moderatorToken;
    private final String name;
    private final String organizerId;
    private final OrganizerData organizerData;
    private final boolean sendNotificationsManager;
    private final boolean sendNotificationsModerator;
    private final LocalDateTime startDate;
    private final Object uploadedLogoId;
    private final String audioQuality;
    private final String defaultVideoQuality;
    private final boolean audienceAccessToChat;
    private final boolean skipPermissionToAllowStreaming;
    private final boolean sourceAccessToSwitchInterface;
    private final WebInterfaceUserOption webInterfaceOption;
    private final String chairpersonToken;
    private final boolean hideParticipants;
    private final boolean fullScreen;
    private final boolean audienceBlocked;
    private final boolean accessToSecondWindow;
    private final boolean mobileBlocked;
    private final boolean audienceAccessToAutoVolume;
    private final boolean audienceAccessToAutoVolumeMobile;
    private final boolean interpreterBlockToTypeInChat;
    private final boolean disableSpeakerTypeEventChat;
    private final boolean disableSpeakerUsePrivateChat;
    private final boolean allowThirdIncomingChannel;
    private final boolean disallowSpeakersChangeVideoQuality;
    private final StreamTextTranscriptions streamTextTranscriptions;
    private final boolean multipleAudioChromeOutputs;
    private final boolean displayVolumeBars;
    private final boolean allowAudiencePreCallTest;
    private final boolean allowSpeakerInterpreterPreCallTest;
    private final boolean enableTwoVideoInputs;
    private final String mfaAllowedMethod;
    private final List<Object> mfaRequiredRoles;
    private final List<Object> preventIpTrackingRoles;
    private final boolean allowAudienceVideo;
    private final boolean samlAuthenticationRequired;
    private final List<SamlSecurityGroupSet> samlSecurityGroupSets;
    private final boolean preventSourceDuplicate;
    private final boolean floorToLanguageOnInterpreterSilence;
    private final int maxNumberOfHosts;
    private final int maxNumberOfSpeakers;
    private final boolean disableStoreEventChat;
    private final boolean attenuation;
    private final boolean enablePolling;
    private final Object quoteStatus;
    private final List<Object> preventSourceDuplicateLanguagesDesk;
    private final List<Object> preventSourceDuplicateLanguagesWeb;
    private final Toggles toggles;
    private final VideoConferenceIntegrationData videoConferenceIntegrationData;
    private final TranscriptSettings transcriptSettings;
    private final int opentokProjectApiKey;
    private final RtmpStreamingParameters rtmpStreamingParameters;
    private final List<String> punctuationPermittedMarks;
    private final Language fromLanguage;
    private final Language floorLanguage;

    private Event(Builder builder) {

        this.allowSourceAudio = builder.allowSourceAudio;
        this.allowSourceVideo = builder.allowSourceVideo;
        this.audienceAccessToFloor = builder.audienceAccessToFloor;
        this.enableModeratorRecording = builder.enableModeratorRecording;
        this.enabledImmediateChannelSwitch = builder.enabledImmediateChannelSwitch;
        this.simultaneouslyInterpreterPublishing = builder.simultaneouslyInterpreterPublishing;
        this.audienceToken = builder.audienceToken;
        this.displayName = builder.displayName;
        this.endDate = builder.endDate;
        this.eventPeriodsData = builder.eventPeriodsData;
        this.floorToken = builder.speakerToken;
        this.isActive = builder.isActive;
        this.isDeleted = builder.isDeleted;
        this.interpreterToken = builder.interpreterToken;
        this.languages = builder.languages;
        this.keycloakAuthentication = builder.keycloakAuthentication;
        this.location = builder.location;
        this.managerId = builder.managerId;
        this.moderatorId = builder.moderatorId;
        this.moderatorToken = builder.moderatorToken;
        this.name = builder.name;
        this.organizerId = builder.organizerId;
        this.organizerData = builder.organizerData;
        this.sendNotificationsManager = builder.sendNotificationsManager;
        this.sendNotificationsModerator = builder.sendNotificationsModerator;
        this.startDate = builder.startDate;
        this.uploadedLogoId = builder.uploadedLogoId;
        this.audioQuality = builder.audioQuality;
        this.defaultVideoQuality = builder.defaultVideoQuality;
        this.audienceAccessToChat = builder.audienceAccessToChat;
        this.skipPermissionToAllowStreaming = builder.skipPermissionToAllowStreaming;
        this.sourceAccessToSwitchInterface = builder.sourceAccessToSwitchInterface;
        this.webInterfaceOption = builder.webInterfaceOption;
        this.chairpersonToken = builder.chairpersonToken;
        this.hideParticipants = builder.hideParticipants;
        this.fullScreen = builder.fullScreen;
        this.audienceBlocked = builder.audienceBlocked;
        this.accessToSecondWindow = builder.accessToSecondWindow;
        this.mobileBlocked = builder.mobileBlocked;
        this.audienceAccessToAutoVolume = builder.audienceAccessToAutoVolume;
        this.audienceAccessToAutoVolumeMobile = builder.audienceAccessToAutoVolumeMobile;
        this.interpreterBlockToTypeInChat = builder.interpreterBlockToTypeInChat;
        this.disableSpeakerTypeEventChat = builder.disableSpeakerTypeEventChat;
        this.disableSpeakerUsePrivateChat = builder.disableSpeakerUsePrivateChat;
        this.allowThirdIncomingChannel = builder.allowThirdIncomingChannel;
        this.disallowSpeakersChangeVideoQuality = builder.disallowSpeakersChangeVideoQuality;
        this.streamTextTranscriptions = builder.streamTextTranscriptions;
        this.multipleAudioChromeOutputs = builder.multipleAudioChromeOutputs;
        this.displayVolumeBars = builder.displayVolumeBars;
        this.allowAudiencePreCallTest = builder.allowAudiencePreCallTest;
        this.allowSpeakerInterpreterPreCallTest = builder.allowSpeakerInterpreterPreCallTest;
        this.enableTwoVideoInputs = builder.enableTwoVideoInputs;
        this.mfaAllowedMethod = builder.mfaAllowedMethod;
        this.mfaRequiredRoles = builder.mfaRequiredRoles;
        this.preventIpTrackingRoles = builder.preventIpTrackingRoles;
        this.allowAudienceVideo = builder.allowAudienceVideo;
        this.samlAuthenticationRequired = builder.samlAuthenticationRequired;
        this.samlSecurityGroupSets = builder.samlSecurityGroupSets;
        this.preventSourceDuplicate = builder.preventSourceDuplicate;
        this.floorToLanguageOnInterpreterSilence = builder.floorToLanguageOnInterpreterSilence;
        this.maxNumberOfHosts = builder.maxNumberOfHosts;
        this.maxNumberOfSpeakers = builder.maxNumberOfSpeakers;
        this.disableStoreEventChat = builder.disableStoreEventChat;
        this.attenuation = builder.attenuation;
        this.enablePolling = builder.enablePolling;
        this.quoteStatus = builder.quoteStatus;
        this.preventSourceDuplicateLanguagesDesk = builder.preventSourceDuplicateLanguagesDesk;
        this.preventSourceDuplicateLanguagesWeb = builder.preventSourceDuplicateLanguagesWeb;
        this.toggles = builder.toggles;
        this.videoConferenceIntegrationData = builder.videoConferenceIntegrationData;
        this.transcriptSettings = builder.transcriptSettings;
        this.opentokProjectApiKey = builder.opentokProjectApiKey;
        this.rtmpStreamingParameters = builder.rtmpStreamingParameters;
        this.punctuationPermittedMarks = builder.punctuationPermittedMarks;
        this.fromLanguage = builder.fromLanguage;
        this.floorLanguage = builder.floorLanguage;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isAllowSourceAudio() {
        return allowSourceAudio;
    }

    public boolean isAllowSourceVideo() {
        return allowSourceVideo;
    }

    public boolean isAudienceAccessToFloor() {
        return audienceAccessToFloor;
    }

    public boolean isEnableModeratorRecording() {
        return enableModeratorRecording;
    }

    public boolean isEnabledImmediateChannelSwitch() {
        return enabledImmediateChannelSwitch;
    }

    public boolean isSimultaneouslyInterpreterPublishing() {
        return simultaneouslyInterpreterPublishing;
    }

    public String getAudienceToken() {
        return audienceToken;
    }

    public String getDisplayName() {
        return displayName;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public List<EventPeriodsData> getEventPeriodsData() {
        return eventPeriodsData;
    }

    public String getSpeakerToken() {
        return floorToken;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String getInterpreterToken() {
        return interpreterToken;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public boolean isKeycloakAuthentication() {
        return keycloakAuthentication;
    }

    public Location getLocation() {
        return location;
    }

    public Object getManagerId() {
        return managerId;
    }

    public Object getModeratorId() {
        return moderatorId;
    }

    public String getModeratorToken() {
        return moderatorToken;
    }

    public String getName() {
        return name;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public OrganizerData getOrganizerData() {
        return organizerData;
    }

    public boolean isSendNotificationsManager() {
        return sendNotificationsManager;
    }

    public boolean isSendNotificationsModerator() {
        return sendNotificationsModerator;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Object getUploadedLogoId() {
        return uploadedLogoId;
    }

    public String getAudioQuality() {
        return audioQuality;
    }

    public String getDefaultVideoQuality() {
        return defaultVideoQuality;
    }

    public boolean isAudienceAccessToChat() {
        return audienceAccessToChat;
    }

    public boolean isSkipPermissionToAllowStreaming() {
        return skipPermissionToAllowStreaming;
    }

    public boolean isSourceAccessToSwitchInterface() {
        return sourceAccessToSwitchInterface;
    }

    public WebInterfaceUserOption getWebInterfaceOption() {
        return webInterfaceOption;
    }

    public String getHostPassword() {
        return chairpersonToken;
    }

    public boolean isHideParticipants() {
        return hideParticipants;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public boolean isAudienceBlocked() {
        return audienceBlocked;
    }

    public boolean isAccessToSecondWindow() {
        return accessToSecondWindow;
    }

    public boolean isMobileBlocked() {
        return mobileBlocked;
    }

    public boolean isAudienceAccessToAutoVolume() {
        return audienceAccessToAutoVolume;
    }

    public boolean isAudienceAccessToAutoVolumeMobile() {
        return audienceAccessToAutoVolumeMobile;
    }

    public boolean isInterpreterBlockToTypeInChat() {
        return interpreterBlockToTypeInChat;
    }

    public boolean isDisableSpeakerTypeEventChat() {
        return disableSpeakerTypeEventChat;
    }

    public boolean isDisableSpeakerUsePrivateChat() {
        return disableSpeakerUsePrivateChat;
    }

    public boolean isAllowThirdIncomingChannel() {
        return allowThirdIncomingChannel;
    }

    public boolean isDisallowSpeakersChangeVideoQuality() {
        return disallowSpeakersChangeVideoQuality;
    }

    public StreamTextTranscriptions getStreamTextTranscriptions() {
        return streamTextTranscriptions;
    }

    public boolean isMultipleAudioChromeOutputs() {
        return multipleAudioChromeOutputs;
    }

    public boolean isDisplayVolumeBars() {
        return displayVolumeBars;
    }

    public boolean isAllowAudiencePreCallTest() {
        return allowAudiencePreCallTest;
    }

    public boolean isAllowSpeakerInterpreterPreCallTest() {
        return allowSpeakerInterpreterPreCallTest;
    }

    public boolean isEnableTwoVideoInputs() {
        return enableTwoVideoInputs;
    }

    public String getMfaAllowedMethod() {
        return mfaAllowedMethod;
    }

    public List<Object> getMfaRequiredRoles() {
        return mfaRequiredRoles;
    }

    public List<Object> getPreventIpTrackingRoles() {
        return preventIpTrackingRoles;
    }

    public boolean isAllowAudienceVideo() {
        return allowAudienceVideo;
    }

    public boolean isSamlAuthenticationRequired() {
        return samlAuthenticationRequired;
    }

    public List<SamlSecurityGroupSet> getSamlSecurityGroupSets() {
        return samlSecurityGroupSets;
    }

    public boolean isPreventSourceDuplicate() {
        return preventSourceDuplicate;
    }

    public boolean isFloorToLanguageOnInterpreterSilence() {
        return floorToLanguageOnInterpreterSilence;
    }

    public int getMaxNumberOfHosts() {
        return maxNumberOfHosts;
    }

    public int getMaxNumberOfSpeakers() {
        return maxNumberOfSpeakers;
    }

    public boolean isDisableStoreEventChat() {
        return disableStoreEventChat;
    }

    public boolean isAttenuation() {
        return attenuation;
    }

    public boolean isEnablePolling() {
        return enablePolling;
    }

    public Object getQuoteStatus() {
        return quoteStatus;
    }

    public List<Object> getPreventSourceDuplicateLanguagesDesk() {
        return preventSourceDuplicateLanguagesDesk;
    }

    public List<Object> getPreventSourceDuplicateLanguagesWeb() {
        return preventSourceDuplicateLanguagesWeb;
    }

    public Toggles getToggles() {
        return toggles;
    }

    public VideoConferenceIntegrationData getVideoConferenceIntegrationData() {
        return videoConferenceIntegrationData;
    }

    public TranscriptSettings getTranscriptSettings() {
        return transcriptSettings;
    }

    public int getOpentokProjectApiKey() {
        return opentokProjectApiKey;
    }

    public RtmpStreamingParameters getRtmpStreamingParameters() {
        return rtmpStreamingParameters;
    }

    public List<String> getPunctuationPermittedMarks() {
        return punctuationPermittedMarks;
    }

    public Language getFromLanguage() {
        return fromLanguage;
    }

    public Language getFloorLanguage() {
        return floorLanguage;
    }

    public EventType getEventType() {
        return webInterfaceOption.toEventType();
    }

    public static final class Builder {

        private boolean allowSourceAudio;
        private boolean allowSourceVideo;
        private boolean audienceAccessToFloor = false;
        private boolean enableModeratorRecording = false;
        private boolean enabledImmediateChannelSwitch;
        private boolean simultaneouslyInterpreterPublishing;
        private String audienceToken;
        private String displayName;
        private LocalDateTime endDate;
        private List<EventPeriodsData> eventPeriodsData;
        private String speakerToken;
        private boolean isActive;
        private boolean isDeleted;
        private String interpreterToken;
        private final List<Language> languages = new ArrayList<>();
        private boolean keycloakAuthentication;
        private Location location;
        private Object managerId;
        private Object moderatorId;
        private String moderatorToken;
        private String name;
        private String organizerId;
        private OrganizerData organizerData;
        private boolean sendNotificationsManager;
        private boolean sendNotificationsModerator;
        private LocalDateTime startDate;
        private Object uploadedLogoId;
        private String audioQuality;
        private String defaultVideoQuality;
        private boolean audienceAccessToChat = false;
        private boolean skipPermissionToAllowStreaming;
        private boolean sourceAccessToSwitchInterface;
        private WebInterfaceUserOption webInterfaceOption;
        private String chairpersonToken;
        private boolean hideParticipants;
        private boolean fullScreen;
        private boolean audienceBlocked = false;
        private boolean accessToSecondWindow;
        private boolean mobileBlocked;
        private boolean audienceAccessToAutoVolume = false;
        private boolean audienceAccessToAutoVolumeMobile;
        private boolean interpreterBlockToTypeInChat = true;
        private boolean disableSpeakerTypeEventChat = false;
        private boolean disableSpeakerUsePrivateChat;
        private boolean allowThirdIncomingChannel;
        private boolean disallowSpeakersChangeVideoQuality;
        private StreamTextTranscriptions streamTextTranscriptions;
        private boolean multipleAudioChromeOutputs;
        private boolean displayVolumeBars;
        private boolean allowAudiencePreCallTest = false;
        private boolean allowSpeakerInterpreterPreCallTest = false;
        private boolean enableTwoVideoInputs;
        private String mfaAllowedMethod;
        private List<Object> mfaRequiredRoles;
        private List<Object> preventIpTrackingRoles;
        private boolean allowAudienceVideo = false;
        private boolean samlAuthenticationRequired = false;
        private List<SamlSecurityGroupSet> samlSecurityGroupSets;
        private boolean preventSourceDuplicate = false;
        private boolean floorToLanguageOnInterpreterSilence = false;
        private int maxNumberOfHosts;
        private int maxNumberOfSpeakers;
        private boolean disableStoreEventChat;
        private boolean attenuation;
        private boolean enablePolling = false;
        private Object quoteStatus;
        private List<Object> preventSourceDuplicateLanguagesDesk;
        private List<Object> preventSourceDuplicateLanguagesWeb;
        private Toggles toggles;
        private VideoConferenceIntegrationData videoConferenceIntegrationData;
        private TranscriptSettings transcriptSettings;
        private int opentokProjectApiKey;
        private RtmpStreamingParameters rtmpStreamingParameters;
        private List<String> punctuationPermittedMarks;
        private Language fromLanguage;
        private Language floorLanguage;

        public Builder() {
        }

        public Event build() {
            return new Event(this);
        }

        public Builder withAccessToSecondWindow(boolean accessToSecondWindow) {
            this.accessToSecondWindow = accessToSecondWindow;
            return this;
        }

        public Builder withAllowAudiencePreCallTest(boolean allowAudiencePreCallTest) {
            this.allowAudiencePreCallTest = allowAudiencePreCallTest;
            return this;
        }

        public Builder withAllowAudienceVideo(boolean allowAudienceVideo) {
            this.allowAudienceVideo = allowAudienceVideo;
            return this;
        }

        public Builder withAllowSourceAudio(boolean allowSourceAudio) {
            this.allowSourceAudio = allowSourceAudio;
            return this;
        }

        public Builder withAllowSourceVideo(boolean allowSourceVideo) {
            this.allowSourceVideo = allowSourceVideo;
            return this;
        }

        public Builder withAllowSpeakerInterpreterPreCallTest(boolean allowSpeakerInterpreterPreCallTest) {
            this.allowSpeakerInterpreterPreCallTest = allowSpeakerInterpreterPreCallTest;
            return this;
        }

        public Builder withAllowThirdIncomingChannel(boolean allowThirdIncomingChannel) {
            this.allowThirdIncomingChannel = allowThirdIncomingChannel;
            return this;
        }

        public Builder withAttenuation(boolean attenuation) {
            this.attenuation = attenuation;
            return this;
        }

        public Builder withAudienceAccessToAutoVolume(boolean audienceAccessToAutoVolume) {
            this.audienceAccessToAutoVolume = audienceAccessToAutoVolume;
            return this;
        }

        public Builder withAudienceAccessToAutoVolumeMobile(boolean audienceAccessToAutoVolumeMobile) {
            this.audienceAccessToAutoVolumeMobile = audienceAccessToAutoVolumeMobile;
            return this;
        }

        public Builder withAudienceAccessToChat(boolean audienceAccessToChat) {
            this.audienceAccessToChat = audienceAccessToChat;
            return this;
        }

        public Builder withAudienceAccessToFloor(boolean audienceAccessToFloor) {
            this.audienceAccessToFloor = audienceAccessToFloor;
            return this;
        }

        public Builder withAudienceBlocked(boolean audienceBlocked) {
            this.audienceBlocked = audienceBlocked;
            return this;
        }

        public Builder withAudienceToken(String audienceToken) {
            this.audienceToken = audienceToken;
            return this;
        }

        public Builder withAudioQuality(String audioQuality) {
            this.audioQuality = audioQuality;
            return this;
        }

        public Builder withChairpersonToken(String chairpersonToken) {
            this.chairpersonToken = chairpersonToken;
            return this;
        }

        public Builder withDefaultVideoQuality(String defaultVideoQuality) {
            this.defaultVideoQuality = defaultVideoQuality;
            return this;
        }

        public Builder withDisableSpeakerTypeEventChat(boolean disableSpeakerTypeEventChat) {
            this.disableSpeakerTypeEventChat = disableSpeakerTypeEventChat;
            return this;
        }

        public Builder withDisableSpeakerUsePrivateChat(boolean disableSpeakerUsePrivateChat) {
            this.disableSpeakerUsePrivateChat = disableSpeakerUsePrivateChat;
            return this;
        }

        public Builder withDisableStoreEventChat(boolean disableStoreEventChat) {
            this.disableStoreEventChat = disableStoreEventChat;
            return this;
        }

        public Builder withDisallowSpeakersChangeVideoQuality(boolean disallowSpeakersChangeVideoQuality) {
            this.disallowSpeakersChangeVideoQuality = disallowSpeakersChangeVideoQuality;
            return this;
        }

        public Builder withDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder withDisplayVolumeBars(boolean displayVolumeBars) {
            this.displayVolumeBars = displayVolumeBars;
            return this;
        }

        public Builder withEnableModeratorRecording(boolean enableModeratorRecording) {
            this.enableModeratorRecording = enableModeratorRecording;
            return this;
        }

        public Builder withEnablePolling(boolean enablePolling) {
            this.enablePolling = enablePolling;
            return this;
        }

        public Builder withEnableTwoVideoInputs(boolean enableTwoVideoInputs) {
            this.enableTwoVideoInputs = enableTwoVideoInputs;
            return this;
        }

        public Builder withEnabledImmediateChannelSwitch(boolean enabledImmediateChannelSwitch) {
            this.enabledImmediateChannelSwitch = enabledImmediateChannelSwitch;
            return this;
        }

        public Builder withEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder withEventPeriodsData(List<EventPeriodsData> eventPeriodsData) {
            this.eventPeriodsData = eventPeriodsData;
            return this;
        }

        public Builder withFloorLanguage(Language floorLanguage) {
            this.floorLanguage = floorLanguage;
            return this;
        }

        public Builder withFloorToLanguageOnInterpreterSilence(boolean floorToLanguageOnInterpreterSilence) {
            this.floorToLanguageOnInterpreterSilence = floorToLanguageOnInterpreterSilence;
            return this;
        }

        public Builder withFloorToken(String floorToken) {
            this.speakerToken = floorToken;
            return this;
        }

        public Builder withFromLanguage(Language fromLanguage) {
            this.fromLanguage = fromLanguage;
            return this;
        }

        public Builder withFullScreen(boolean fullScreen) {
            this.fullScreen = fullScreen;
            return this;
        }

        public Builder withHideParticipants(boolean hideParticipants) {
            this.hideParticipants = hideParticipants;
            return this;
        }

        public Builder withInterpreterBlockToTypeInChat(boolean interpreterBlockToTypeInChat) {
            this.interpreterBlockToTypeInChat = interpreterBlockToTypeInChat;
            return this;
        }

        public Builder withInterpreterToken(String interpreterToken) {
            this.interpreterToken = interpreterToken;
            return this;
        }

        public Builder withIsActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder withIsDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public Builder withKeycloakAuthentication(boolean keycloakAuthentication) {
            this.keycloakAuthentication = keycloakAuthentication;
            return this;
        }

        public Builder withLanguages(List<Language> languages) {
            this.languages.addAll(languages);
            return this;
        }

        public Builder withLanguage(Language language) {
            this.languages.add(language);
            return this;
        }

        public Builder withLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder withManagerId(Object managerId) {
            this.managerId = managerId;
            return this;
        }

        public Builder withMaxNumberOfHosts(int maxNumberOfHosts) {
            this.maxNumberOfHosts = maxNumberOfHosts;
            return this;
        }

        public Builder withMaxNumberOfSpeakers(int maxNumberOfSpeakers) {
            this.maxNumberOfSpeakers = maxNumberOfSpeakers;
            return this;
        }

        public Builder withMfaAllowedMethod(String mfaAllowedMethod) {
            this.mfaAllowedMethod = mfaAllowedMethod;
            return this;
        }

        public Builder withMfaRequiredRoles(List<Object> mfaRequiredRoles) {
            this.mfaRequiredRoles = mfaRequiredRoles;
            return this;
        }

        public Builder withMobileBlocked(boolean mobileBlocked) {
            this.mobileBlocked = mobileBlocked;
            return this;
        }

        public Builder withModeratorId(Object moderatorId) {
            this.moderatorId = moderatorId;
            return this;
        }

        public Builder withModeratorToken(String moderatorToken) {
            this.moderatorToken = moderatorToken;
            return this;
        }

        public Builder withMultipleAudioChromeOutputs(boolean multipleAudioChromeOutputs) {
            this.multipleAudioChromeOutputs = multipleAudioChromeOutputs;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withOpentokProjectApiKey(int opentokProjectApiKey) {
            this.opentokProjectApiKey = opentokProjectApiKey;
            return this;
        }

        public Builder withOrganizerData(OrganizerData organizerData) {
            this.organizerData = organizerData;
            return this;
        }

        public Builder withOrganizerId(String organizerId) {
            this.organizerId = organizerId;
            return this;
        }

        public Builder withPreventIpTrackingRoles(List<Object> preventIpTrackingRoles) {
            this.preventIpTrackingRoles = preventIpTrackingRoles;
            return this;
        }

        public Builder withPreventSourceDuplicate(boolean preventSourceDuplicate) {
            this.preventSourceDuplicate = preventSourceDuplicate;
            return this;
        }

        public Builder withPreventSourceDuplicateLanguagesDesk(List<Object> preventSourceDuplicateLanguagesDesk) {
            this.preventSourceDuplicateLanguagesDesk = preventSourceDuplicateLanguagesDesk;
            return this;
        }

        public Builder withPreventSourceDuplicateLanguagesWeb(List<Object> preventSourceDuplicateLanguagesWeb) {
            this.preventSourceDuplicateLanguagesWeb = preventSourceDuplicateLanguagesWeb;
            return this;
        }

        public Builder withPunctuationPermittedMarks(List<String> punctuationPermittedMarks) {
            this.punctuationPermittedMarks = punctuationPermittedMarks;
            return this;
        }

        public Builder withQuoteStatus(Object quoteStatus) {
            this.quoteStatus = quoteStatus;
            return this;
        }

        public Builder withRtmpStreamingParameters(RtmpStreamingParameters rtmpStreamingParameters) {
            this.rtmpStreamingParameters = rtmpStreamingParameters;
            return this;
        }

        public Builder withSamlAuthenticationRequired(boolean samlAuthenticationRequired) {
            this.samlAuthenticationRequired = samlAuthenticationRequired;
            return this;
        }

        public Builder withSamlSecurityGroupSets(List<SamlSecurityGroupSet> samlSecurityGroupSets) {
            this.samlSecurityGroupSets = samlSecurityGroupSets;
            return this;
        }

        public Builder withSamlSecurityGroupSet(SamlSecurityGroupSet samlSecurityGroupSet) {

            if (this.samlSecurityGroupSets == null) {
                samlSecurityGroupSets = new ArrayList<>();
            }

            this.samlSecurityGroupSets.add(samlSecurityGroupSet);
            return this;
        }

        public Builder withSendNotificationsManager(boolean sendNotificationsManager) {
            this.sendNotificationsManager = sendNotificationsManager;
            return this;
        }

        public Builder withSendNotificationsModerator(boolean sendNotificationsModerator) {
            this.sendNotificationsModerator = sendNotificationsModerator;
            return this;
        }

        public Builder withSimultaneouslyInterpreterPublishing(boolean simultaneouslyInterpreterPublishing) {
            this.simultaneouslyInterpreterPublishing = simultaneouslyInterpreterPublishing;
            return this;
        }

        public Builder withSkipPermissionToAllowStreaming(boolean skipPermissionToAllowStreaming) {
            this.skipPermissionToAllowStreaming = skipPermissionToAllowStreaming;
            return this;
        }

        public Builder withSourceAccessToSwitchInterface(boolean sourceAccessToSwitchInterface) {
            this.sourceAccessToSwitchInterface = sourceAccessToSwitchInterface;
            return this;
        }

        public Builder withStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withStreamTextTranscriptions(StreamTextTranscriptions streamTextTranscriptions) {
            this.streamTextTranscriptions = streamTextTranscriptions;
            return this;
        }

        public Builder withToggles(Toggles toggles) {
            this.toggles = toggles;
            return this;
        }

        public Builder withTranscriptSettings(TranscriptSettings transcriptSettings) {
            this.transcriptSettings = transcriptSettings;
            return this;
        }

        public Builder withUploadedLogoId(Object uploadedLogoId) {
            this.uploadedLogoId = uploadedLogoId;
            return this;
        }

        public Builder withVideoConferenceIntegrationData(VideoConferenceIntegrationData videoConferenceIntegrationData) {
            this.videoConferenceIntegrationData = videoConferenceIntegrationData;
            return this;
        }

        public Builder withWebInterfaceOption(WebInterfaceUserOption webInterfaceOption) {
            this.webInterfaceOption = webInterfaceOption;
            return this;
        }
    }

    public static Builder createRandomBuilder() {
        return createBuilder(EventType.getRandomEventType());
    }

    public static Builder createConnectProClassroomBuilder() {
        return createBuilder(EventType.Classroom);
    }

    public static Builder createConnectWebMeetBuilder() {
        return createBuilder(EventType.WebMeet);
    }

    public static Builder createEventProBuilder() {
        return createBuilder(EventType.EventPro);
    }

    private static Builder createBuilder(EventType eventType) {

        Builder builder = new Builder();

        setTokens(builder);
        setHostPassword(builder, eventType);
        builder.location = LocationTestData.getRandomLocation();
        builder.webInterfaceOption = WebInterfaceUserOption.fromEventType(eventType);
        setName(builder, eventType);
        setDate(builder);

        return builder;
    }

    private static void setTokens(Builder builder) {

        builder.audienceToken = TokenUtil.createAudienceToken();
        builder.interpreterToken = TokenUtil.createInterpreterToken();
        builder.speakerToken = TokenUtil.createSpeakerToken();
        builder.moderatorToken = TokenUtil.createModeratorToken();
    }

    private static void setHostPassword(Builder builder, EventType eventType) {
        if (eventType == EventType.Classroom) {
            builder.chairpersonToken = TokenUtil.createHostPassword();
        }
    }

    private static void setName(Builder builder, EventType eventType) {
        builder.name = RandomUtil.getRandomNumericStringWithPrefix(eventType.name());
    }

    private static void setDate(Builder builder) {

        LocalDateTime startDateTime = LocalDateTime.now().plusDays(1).withHour(6);
        builder.startDate = startDateTime;

        LocalDateTime endDateTime = startDateTime.withHour(9);
        builder.endDate = endDateTime;

        builder.eventPeriodsData = List.of(new EventPeriodsData(startDateTime, endDateTime));
    }

    private static final class TokenUtil {

        private static final int TOKEN_LENGTH = 8;
        private static final int HOST_PASSWORD_LENGTH = 6;

        private static String createAudienceToken() {
            return createToken();
        }

        private static String createInterpreterToken() {
            return "I-" + createToken();
        }

        private static String createSpeakerToken() {
            return "S-" + createToken();
        }

        private static String createModeratorToken() {
            return "M-" + createToken();
        }

        private static String createToken() {
            return RandomStringUtils.randomAlphabetic(TOKEN_LENGTH);
        }

        private static String createHostPassword() {
            return RandomStringUtils.randomAlphabetic(HOST_PASSWORD_LENGTH);
        }
    }
}
