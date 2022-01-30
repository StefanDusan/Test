package com.intpfyqa.settings;

import java.util.HashMap;
import java.util.Map;

public final class KeycloakSettings extends BaseSettings {

    private static final String PREFIX = "keycloak";

    private static final KeycloakSettings instance = new KeycloakSettings();

    private KeycloakSettings() {
    }

    public static KeycloakSettings instance() {
        return instance;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public Map<String, String> listProps() {

        Map<String, String> props = new HashMap<>();

        props.put("domain", getDomain());
        props.put("security group", getSecurityGroup());

        props.put("organization email", getOrganizationEmail());

        props.put("partner email", getPartnerEmail());
        props.put("partner password", getPartnerPassword());

        props.put("not partner email", getNotPartnerEmail());
        props.put("not partner password", getNotPartnerPassword());

        props.put("not partner with security group email", getNotPartnerWithSecurityGroupEmail());
        props.put("not partner with security group password", getNotPartnerWithSecurityGroupPassword());

        props.put("partner with security group email", getPartnerWithSecurityGroupEmail());
        props.put("partner with security group password", getPartnerWithSecurityGroupPassword());

        props.put("partner with multiple keycloak organizations email", getPartnerWithMultipleKeycloakOrganizationsEmail());
        props.put("partner with multiple keycloak organizations password", getPartnerWithMultipleKeycloakOrganizationsPassword());

        props.put("not approved user email", getNotApprovedUserEmail());
        props.put("not approved user password", getNotApprovedUserPassword());

        props.put("not partner from other security group email", getNotPartnerFromOtherSecurityGroupEmail());
        props.put("not partner from other security group password", getNotPartnerFromOtherSecurityGroupPassword());

        props.put("1-st keycloak organization", getFirstKeycloakOrganization());
        props.put("2-nd keycloak organization", getSecondKeycloakOrganization());

        props.put("event organization email", getEventOrganizationEmail());
        props.put("event organization password", getEventOrganizationPassword());

        return props;
    }

    public String getDomain() {
        return getProperty("domain", "auto"); // Default value is necessary for Keycloak Event tests.
    }

    public String getSecurityGroup() {
        return getProperty("securityGroup");
    }

    public String getOrganizationEmail() {
        return getProperty("organization.email");
    }

    public String getPartnerEmail() {
        return getProperty("partner.email");
    }

    public String getPartnerPassword() {
        return getProperty("partner.password");
    }

    public String getNotPartnerEmail() {
        return getProperty("notPartner.email");
    }

    public String getNotPartnerPassword() {
        return getProperty("notPartner.password");
    }

    public String getNotPartnerWithSecurityGroupEmail() {
        return getProperty("notPartnerWithSecurityGroup.email");
    }

    public String getNotPartnerWithSecurityGroupPassword() {
        return getProperty("notPartnerWithSecurityGroup.password");
    }

    public String getPartnerWithSecurityGroupEmail() {
        return getProperty("partnerWithSecurityGroup.email");
    }

    public String getPartnerWithSecurityGroupPassword() {
        return getProperty("partnerWithSecurityGroup.password");
    }

    public String getPartnerWithMultipleKeycloakOrganizationsEmail() {
        return getProperty("partnerWithMultipleKeycloakOrganizations.email");
    }

    public String getPartnerWithMultipleKeycloakOrganizationsPassword() {
        return getProperty("partnerWithMultipleKeycloakOrganizations.password");
    }

    public String getNotApprovedUserEmail() {
        return getProperty("notApprovedUser.email");
    }

    public String getNotApprovedUserPassword() {
        return getProperty("notApprovedUser.password");
    }

    public String getNotPartnerFromOtherSecurityGroupEmail() {
        return getProperty("notPartnerFromOtherSecurityGroup.email");
    }

    public String getNotPartnerFromOtherSecurityGroupPassword() {
        return getProperty("notPartnerFromOtherSecurityGroup.password");
    }

    public String getFirstKeycloakOrganization() {
        return getProperty("keycloakOrganizations").split(",")[0];
    }

    public String getSecondKeycloakOrganization() {
        return getProperty("keycloakOrganizations").split(",")[1];
    }

    public String getEventOrganizationEmail() {
        return getProperty("event.organization.email");
    }

    public String getEventOrganizationPassword() {
        return getProperty("event.organization.password");
    }
}
