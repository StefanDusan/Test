package com.intpfy.test;

import com.intpfy.user.OrganizationUser;
import com.intpfy.container.KeycloakOrganizationUserContainer;

public interface KeycloakOrganizationTest extends KeycloakTest, OrganizationTest {

    @Override
    default OrganizationUser takeUser() {
        return KeycloakOrganizationUserContainer.getInstance().take();
    }

    @Override
    default void putUser(OrganizationUser user) {
        KeycloakOrganizationUserContainer.getInstance().put(user);
    }
}
