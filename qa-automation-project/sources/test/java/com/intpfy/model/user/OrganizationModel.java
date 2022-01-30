package com.intpfy.model.user;

import com.intpfy.model.event.Location;
import com.intpfy.model.event.Toggles;

import java.util.HashSet;
import java.util.Set;

public class OrganizationModel {

    private final String firstName;
    private final String lastName;
    private final String login;
    private final int maxStreamingUsers;
    private final String mobilePhone;
    private final String name;
    private final String phone;
    private final boolean showLogo;
    private final String title;
    private final Location location;
    private final Object uploadedLogoId;
    private final Object uploadedFavIconId;
    private final Object uploadedLoginScreenImageId;
    private final Object uploadedPartnerFooterImageId;
    private final String webInterfaceOption;
    private final boolean recordingAccess;
    private final boolean allowAudioRoutingPanel;
    private final boolean allowRtmpStreaming;
    private final boolean autoVolumeAccess;
    private final boolean pollingAccess;
    private final boolean quoteStatusAccess;
    private final boolean transcriptAccess;
    private final boolean disallowAnonymize;
    private final boolean recordingNotification;
    private final boolean transcriptsNotification;
    private final Object linkToPrivacyPolicy;
    private final Toggles toggles;
    private final String id;
    private final int timeZone;
    private final String timeZoneStr;
    private final String domain;
    private final Object subdomainForEvent;
    private final Object partnerFooter;
    private final Object color;
    private final Object backgroundColor;
    private final boolean attenuation;
    private final boolean allowedMfa;
    private final String samlRequiredGroup;
    private final String samlIdProviderCode;
    private final Set<String> allowlistEmails;
    private final boolean keycloakEnabled;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public int getMaxStreamingUsers() {
        return maxStreamingUsers;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isShowLogo() {
        return showLogo;
    }

    public String getTitle() {
        return title;
    }

    public Location getLocation() {
        return location;
    }

    public Object getUploadedLogoId() {
        return uploadedLogoId;
    }

    public Object getUploadedFavIconId() {
        return uploadedFavIconId;
    }

    public Object getUploadedLoginScreenImageId() {
        return uploadedLoginScreenImageId;
    }

    public Object getUploadedPartnerFooterImageId() {
        return uploadedPartnerFooterImageId;
    }

    public String getWebInterfaceOption() {
        return webInterfaceOption;
    }

    public boolean isRecordingAccess() {
        return recordingAccess;
    }

    public boolean isAllowAudioRoutingPanel() {
        return allowAudioRoutingPanel;
    }

    public boolean isAllowRtmpStreaming() {
        return allowRtmpStreaming;
    }

    public boolean isAutoVolumeAccess() {
        return autoVolumeAccess;
    }

    public boolean isPollingAccess() {
        return pollingAccess;
    }

    public boolean isQuoteStatusAccess() {
        return quoteStatusAccess;
    }

    public boolean isTranscriptAccess() {
        return transcriptAccess;
    }

    public boolean isDisallowAnonymize() {
        return disallowAnonymize;
    }

    public boolean isRecordingNotification() {
        return recordingNotification;
    }

    public boolean isTranscriptsNotification() {
        return transcriptsNotification;
    }

    public Object getLinkToPrivacyPolicy() {
        return linkToPrivacyPolicy;
    }

    public Toggles getToggles() {
        return toggles;
    }

    public String getId() {
        return id;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public String getTimeZoneStr() {
        return timeZoneStr;
    }

    public String getDomain() {
        return domain;
    }

    public Object getSubdomainForEvent() {
        return subdomainForEvent;
    }

    public Object getPartnerFooter() {
        return partnerFooter;
    }

    public Object getColor() {
        return color;
    }

    public Object getBackgroundColor() {
        return backgroundColor;
    }

    public boolean isAttenuation() {
        return attenuation;
    }

    public boolean isAllowedMfa() {
        return allowedMfa;
    }

    public String getSamlRequiredGroup() {
        return samlRequiredGroup;
    }

    public String getSamlIdProviderCode() {
        return samlIdProviderCode;
    }

    public Set<String> getAllowlistEmails() {
        return allowlistEmails;
    }

    public boolean isKeycloakEnabled() {
        return keycloakEnabled;
    }

    private OrganizationModel(Builder builder) {

        this.name = builder.name;
        this.timeZoneStr = builder.timeZoneStr;
        this.maxStreamingUsers = builder.maxStreamingUsers;
        this.title = builder.title;
        this.firstName = builder.firstName;
        this.disallowAnonymize = builder.disallowAnonymize;
        this.subdomainForEvent = builder.subdomainForEvent;
        this.samlIdProviderCode = builder.samlIdProviderCode;
        this.linkToPrivacyPolicy = builder.linkToPrivacyPolicy;
        this.recordingNotification = builder.recordingNotification;
        this.uploadedFavIconId = builder.uploadedFavIconId;
        this.autoVolumeAccess = builder.autoVolumeAccess;
        this.color = builder.color;
        this.attenuation = builder.attenuation;
        this.id = builder.id;
        this.domain = builder.domain;
        this.showLogo = builder.showLogo;
        this.timeZone = builder.timeZone;
        this.uploadedLogoId = builder.uploadedLogoId;
        this.backgroundColor = builder.backgroundColor;
        this.samlRequiredGroup = builder.samlRequiredGroup;
        this.uploadedPartnerFooterImageId = builder.uploadedPartnerFooterImageId;
        this.pollingAccess = builder.pollingAccess;
        this.allowAudioRoutingPanel = builder.allowAudioRoutingPanel;
        this.lastName = builder.lastName;
        this.phone = builder.phone;
        this.allowedMfa = builder.allowedMfa;
        this.mobilePhone = builder.mobilePhone;
        this.login = builder.login;
        this.keycloakEnabled = builder.keycloakEnabled;
        this.recordingAccess = builder.recordingAccess;
        this.webInterfaceOption = builder.webInterfaceOption;
        this.quoteStatusAccess = builder.quoteStatusAccess;
        this.transcriptAccess = builder.transcriptAccess;
        this.partnerFooter = builder.partnerFooter;
        this.transcriptsNotification = builder.transcriptsNotification;
        this.location = builder.location;
        this.allowRtmpStreaming = builder.allowRtmpStreaming;
        this.toggles = builder.toggles;
        this.uploadedLoginScreenImageId = builder.uploadedLoginScreenImageId;
        this.allowlistEmails = builder.allowlistEmails;
    }


    public static final class Builder {

        private String firstName;
        private String lastName;
        private String login;
        private int maxStreamingUsers;
        private String mobilePhone;
        private String name;
        private String phone;
        private boolean showLogo;
        private String title;
        private Location location;
        private Object uploadedLogoId;
        private Object uploadedFavIconId;
        private Object uploadedLoginScreenImageId;
        private Object uploadedPartnerFooterImageId;
        private String webInterfaceOption;
        private boolean recordingAccess;
        private boolean allowAudioRoutingPanel;
        private boolean allowRtmpStreaming;
        private boolean autoVolumeAccess;
        private boolean pollingAccess;
        private boolean quoteStatusAccess;
        private boolean transcriptAccess;
        private boolean disallowAnonymize;
        private boolean recordingNotification;
        private boolean transcriptsNotification;
        private Object linkToPrivacyPolicy;
        private Toggles toggles;
        private String id;
        private int timeZone;
        private String timeZoneStr;
        private String domain;
        private Object subdomainForEvent;
        private Object partnerFooter;
        private Object color;
        private Object backgroundColor;
        private boolean attenuation;
        private boolean allowedMfa;
        private String samlRequiredGroup;
        private String samlIdProviderCode;
        private final Set<String> allowlistEmails = new HashSet<>();
        private boolean keycloakEnabled;

        public Builder() {
        }

        public Builder withAllowAudioRoutingPanel(boolean allowAudioRoutingPanel) {
            this.allowAudioRoutingPanel = allowAudioRoutingPanel;
            return this;
        }

        public Builder withAllowRtmpStreaming(boolean allowRtmpStreaming) {
            this.allowRtmpStreaming = allowRtmpStreaming;
            return this;
        }

        public Builder withAllowedMfa(boolean allowedMfa) {
            this.allowedMfa = allowedMfa;
            return this;
        }

        public Builder withAttenuation(boolean attenuation) {
            this.attenuation = attenuation;
            return this;
        }

        public Builder withAutoVolumeAccess(boolean autoVolumeAccess) {
            this.autoVolumeAccess = autoVolumeAccess;
            return this;
        }

        public Builder withBackgroundColor(Object backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder withColor(Object color) {
            this.color = color;
            return this;
        }

        public Builder withDisallowAnonymize(boolean disallowAnonymize) {
            this.disallowAnonymize = disallowAnonymize;
            return this;
        }

        public Builder withDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withKeycloakEnabled(boolean keycloakEnabled) {
            this.keycloakEnabled = keycloakEnabled;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withLinkToPrivacyPolicy(Object linkToPrivacyPolicy) {
            this.linkToPrivacyPolicy = linkToPrivacyPolicy;
            return this;
        }

        public Builder withLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder withLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder withMaxStreamingUsers(int maxStreamingUsers) {
            this.maxStreamingUsers = maxStreamingUsers;
            return this;
        }

        public Builder withMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPartnerFooter(Object partnerFooter) {
            this.partnerFooter = partnerFooter;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder withPollingAccess(boolean pollingAccess) {
            this.pollingAccess = pollingAccess;
            return this;
        }

        public Builder withQuoteStatusAccess(boolean quoteStatusAccess) {
            this.quoteStatusAccess = quoteStatusAccess;
            return this;
        }

        public Builder withRecordingAccess(boolean recordingAccess) {
            this.recordingAccess = recordingAccess;
            return this;
        }

        public Builder withRecordingNotification(boolean recordingNotification) {
            this.recordingNotification = recordingNotification;
            return this;
        }

        public Builder withSamlIdProviderCode(String samlIdProviderCode) {
            this.samlIdProviderCode = samlIdProviderCode;
            return this;
        }

        public Builder withSamlRequiredGroup(String samlRequiredGroup) {
            this.samlRequiredGroup = samlRequiredGroup;
            return this;
        }

        public Builder withShowLogo(boolean showLogo) {
            this.showLogo = showLogo;
            return this;
        }

        public Builder withSubdomainForEvent(Object subdomainForEvent) {
            this.subdomainForEvent = subdomainForEvent;
            return this;
        }

        public Builder withTimeZone(int timeZone) {
            this.timeZone = timeZone;
            return this;
        }

        public Builder withTimeZoneStr(String timeZoneStr) {
            this.timeZoneStr = timeZoneStr;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withToggles(Toggles toggles) {
            this.toggles = toggles;
            return this;
        }

        public Builder withTranscriptAccess(boolean transcriptAccess) {
            this.transcriptAccess = transcriptAccess;
            return this;
        }

        public Builder withTranscriptsNotification(boolean transcriptsNotification) {
            this.transcriptsNotification = transcriptsNotification;
            return this;
        }

        public Builder withUploadedFavIconId(Object uploadedFavIconId) {
            this.uploadedFavIconId = uploadedFavIconId;
            return this;
        }

        public Builder withUploadedLoginScreenImageId(Object uploadedLoginScreenImageId) {
            this.uploadedLoginScreenImageId = uploadedLoginScreenImageId;
            return this;
        }

        public Builder withUploadedLogoId(Object uploadedLogoId) {
            this.uploadedLogoId = uploadedLogoId;
            return this;
        }

        public Builder withUploadedPartnerFooterImageId(Object uploadedPartnerFooterImageId) {
            this.uploadedPartnerFooterImageId = uploadedPartnerFooterImageId;
            return this;
        }

        public Builder withAllowlistEmails(String... emails) {
            this.allowlistEmails.addAll(Set.of(emails));
            return this;
        }

        public Builder withWebInterfaceOption(String webInterfaceOption) {
            this.webInterfaceOption = webInterfaceOption;
            return this;
        }

        public OrganizationModel build() {
            return new OrganizationModel(this);
        }
    }
}
