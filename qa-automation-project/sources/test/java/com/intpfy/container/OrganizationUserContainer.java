package com.intpfy.container;

import com.intpfy.user.OrganizationUser;
import com.intpfy.util.UserUtil;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class OrganizationUserContainer {

    private static final OrganizationUserContainer instance = new OrganizationUserContainer(UserUtil.getOrganizationUsers());

    private final BlockingQueue<OrganizationUser> organizationUsers;

    OrganizationUserContainer(Collection<OrganizationUser> organizationUsers) {
        this.organizationUsers = new ArrayBlockingQueue<>(organizationUsers.size());
        this.organizationUsers.addAll(organizationUsers);
    }

    public static OrganizationUserContainer getInstance() {
        return instance;
    }

    public OrganizationUser take() {
        try {
            return organizationUsers.take();
        } catch (InterruptedException e) {
            handleInterruptedException(e);
            return null; // Safe as RuntimeException will be thrown.
        }
    }

    public void put(OrganizationUser user) {
        try {
            organizationUsers.put(user);
        } catch (InterruptedException e) {
            handleInterruptedException(e);
        }
    }

    private void handleInterruptedException(InterruptedException exception) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(exception);
    }
}
