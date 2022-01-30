package com.intpfy.container;

import com.intpfy.user.OrganizationUser;
import com.intpfyqa.settings.KeycloakSettings;

import java.util.Collection;
import java.util.List;

public class KeycloakOrganizationUserContainer extends OrganizationUserContainer {

    private static final KeycloakOrganizationUserContainer instance;

    static {
        String email = KeycloakSettings.instance().getOrganizationEmail();
        String password = ""; // No need to Log in with this user.
        OrganizationUser user = new OrganizationUser(email, password);
        instance = new KeycloakOrganizationUserContainer(List.of(user));
    }

    KeycloakOrganizationUserContainer(Collection<OrganizationUser> organizationUsers) {
        super(organizationUsers);
    }

    public static KeycloakOrganizationUserContainer getInstance() {
        return instance;
    }
}
