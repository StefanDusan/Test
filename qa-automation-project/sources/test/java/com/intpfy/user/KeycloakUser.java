package com.intpfy.user;

import com.intpfyqa.settings.KeycloakSettings;

public class KeycloakUser extends BaseUser {

    private static final KeycloakSettings SETTINGS = KeycloakSettings.instance();

    public static final KeycloakUser PARTNER = new KeycloakUser(
            SETTINGS.getPartnerEmail(), SETTINGS.getPartnerPassword()
    );
    public static final KeycloakUser NOT_PARTNER = new KeycloakUser(
            SETTINGS.getNotPartnerEmail(), SETTINGS.getNotPartnerPassword()
    );
    public static final KeycloakUser PARTNER_WITH_SECURITY_GROUP = new KeycloakUser(
            SETTINGS.getPartnerWithSecurityGroupEmail(), SETTINGS.getPartnerWithSecurityGroupPassword()
    );
    public static final KeycloakUser NOT_PARTNER_WITH_SECURITY_GROUP = new KeycloakUser(
            SETTINGS.getNotPartnerWithSecurityGroupEmail(), SETTINGS.getNotPartnerWithSecurityGroupPassword()
    );
    public static final KeycloakUser PARTNER_WITH_MULTIPLE_KEYCLOAK_ORGANIZATIONS = new KeycloakUser(
            SETTINGS.getPartnerWithMultipleKeycloakOrganizationsEmail(), SETTINGS.getPartnerWithMultipleKeycloakOrganizationsPassword()
    );
    public static final OrganizationUser NOT_APPROVED_USER = new OrganizationUser(
            SETTINGS.getNotApprovedUserEmail(), SETTINGS.getNotApprovedUserPassword()
    );
    public static final KeycloakUser NOT_PARTNER_FROM_OTHER_SECURITY_GROUP = new KeycloakUser(
            SETTINGS.getNotPartnerFromOtherSecurityGroupEmail(), SETTINGS.getNotPartnerFromOtherSecurityGroupPassword()
    );

    private KeycloakUser(String email, String password) {
        super(email, password);
    }
}
