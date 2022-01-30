package com.intpfy.test;

import com.intpfy.user.OrganizationUser;
import com.intpfy.user.BaseUser;

public interface KeycloakEventTest extends KeycloakTest, EventTest {

    default BaseUser getEventUser() {
        return new OrganizationUser(SETTINGS.getEventOrganizationEmail(), SETTINGS.getEventOrganizationPassword());
    }
}
