package com.intpfy.test;

import com.intpfy.user.KeycloakUser;
import com.intpfy.user.OrganizationUser;
import com.intpfyqa.settings.KeycloakSettings;

public interface KeycloakTest {

    KeycloakSettings SETTINGS = KeycloakSettings.instance();

    static String getDomain() {
        return SETTINGS.getDomain();
    }

    static String getSecurityGroup() {
        return SETTINGS.getSecurityGroup();
    }

    static String getOrganizationEmail() {
        return SETTINGS.getOrganizationEmail();
    }

    static KeycloakUser getPartner() {
        return KeycloakUser.PARTNER;
    }

    static KeycloakUser getNotPartner() {
        return KeycloakUser.NOT_PARTNER;
    }

    static KeycloakUser getPartnerWithSecurityGroup() {
        return KeycloakUser.PARTNER_WITH_SECURITY_GROUP;
    }

    static KeycloakUser getNotPartnerWithSecurityGroup() {
        return KeycloakUser.NOT_PARTNER_WITH_SECURITY_GROUP;
    }

    static KeycloakUser getPartnerWithMultipleKeycloakOrganizations() {
        return KeycloakUser.PARTNER_WITH_MULTIPLE_KEYCLOAK_ORGANIZATIONS;
    }

    static OrganizationUser getNotApprovedUser() {
        return KeycloakUser.NOT_APPROVED_USER;
    }

    static KeycloakUser getNotPartnerFromOtherSecurityGroup() {
        return KeycloakUser.NOT_PARTNER_FROM_OTHER_SECURITY_GROUP;
    }

    static String getFirstKeycloakOrganization() {
        return SETTINGS.getFirstKeycloakOrganization();
    }

    static String getSecondKeycloakOrganization() {
        return SETTINGS.getSecondKeycloakOrganization();
    }
}
