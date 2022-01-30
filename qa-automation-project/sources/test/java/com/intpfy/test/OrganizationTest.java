package com.intpfy.test;

import com.intpfy.container.OrganizationUserContainer;
import com.intpfy.model.user.OrganizationModel;
import com.intpfy.user.OrganizationUser;

public interface OrganizationTest {

    OrganizationModel getOrganizationModel();

    OrganizationUser getOrganizationUser();

    default OrganizationUser takeUser() {
        return OrganizationUserContainer.getInstance().take();
    }

    default void putUser(OrganizationUser user) {
        OrganizationUserContainer.getInstance().put(user);
    }
}
