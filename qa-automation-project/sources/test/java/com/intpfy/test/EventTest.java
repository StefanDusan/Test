package com.intpfy.test;

import com.intpfy.model.event.Event;
import com.intpfy.user.BaseUser;
import com.intpfy.util.UserUtil;

public interface EventTest {

    Event getEvent();

    default BaseUser getEventUser() {
        return UserUtil.getEventOrganizationUser();
    }
}
