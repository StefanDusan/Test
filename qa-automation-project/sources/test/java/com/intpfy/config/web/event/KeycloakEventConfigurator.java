package com.intpfy.config.web.event;

import com.intpfy.authorization.KeycloakAuthorizer;

public class KeycloakEventConfigurator extends EventConfigurator {

    private static final KeycloakEventConfigurator instance = new KeycloakEventConfigurator(KeycloakAuthorizer.getInstance());

    private KeycloakEventConfigurator(KeycloakAuthorizer authorizer) {
        super(authorizer);
    }

    public static KeycloakEventConfigurator getInstance() {
        return instance;
    }
}
