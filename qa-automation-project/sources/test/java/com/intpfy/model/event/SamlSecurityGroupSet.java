package com.intpfy.model.event;

import java.util.HashSet;
import java.util.Set;

public class SamlSecurityGroupSet {

    private final Set<Role> roles;
    private final boolean emptySet;
    private final Set<String> groups;
    private final Object whitelistId;
    private final Set<String> whitelistEmails;

    private SamlSecurityGroupSet(Builder builder) {

        this.roles = builder.roles;
        this.emptySet = builder.emptySet;
        this.groups = builder.groups;
        this.whitelistId = builder.whitelistId;
        this.whitelistEmails = builder.whitelistEmails;
    }

    public static final class Builder {

        private final Set<Role> roles = new HashSet<>();
        private boolean emptySet;
        private final Set<String> groups = new HashSet<>();
        private Object whitelistId;
        private final Set<String> whitelistEmails = new HashSet<>();

        public Builder() {
        }

        public Builder withRoles(Role... roles) {
            this.roles.addAll(Set.of(roles));
            return this;
        }

        public Builder withNoSecurityGroups(boolean emptySet) {
            this.emptySet = emptySet;
            return this;
        }

        public Builder withGroups(String... groups) {
            this.groups.addAll(Set.of(groups));
            return this;
        }

        public Builder withWhitelistId(Object whitelistId) {
            this.whitelistId = whitelistId;
            return this;
        }

        public Builder withAllowlistEmails(String... emails) {
            this.whitelistEmails.addAll(Set.of(emails));
            return this;
        }

        public SamlSecurityGroupSet build() {
            return new SamlSecurityGroupSet(this);
        }
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean isNoSecurityGroups() {
        return emptySet;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public Object getWhitelistId() {
        return whitelistId;
    }

    public Set<String> getWhitelistEmails() {
        return whitelistEmails;
    }
}
