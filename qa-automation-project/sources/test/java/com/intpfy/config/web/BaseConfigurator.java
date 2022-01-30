package com.intpfy.config.web;

import com.intpfy.authorization.Authorizer;

public abstract class BaseConfigurator {

    protected final Authorizer authorizer;

    protected BaseConfigurator(Authorizer authorizer) {
        this.authorizer = authorizer;
    }
}
