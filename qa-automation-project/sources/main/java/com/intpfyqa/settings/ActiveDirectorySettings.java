package com.intpfyqa.settings;

import java.util.HashMap;
import java.util.Map;

public final class ActiveDirectorySettings extends BaseSettings {

    private static final String PREFIX = "ad";

    private static final ActiveDirectorySettings instance = new ActiveDirectorySettings();

    private static final String INCORRECT_SECURITY_GROUP = "Incorrect-Security-Group";
    private static final String INCORRECT_EMAIL_FOR_ALLOWLIST = "incorrect@email.com";

    private ActiveDirectorySettings() {
    }

    public static ActiveDirectorySettings instance() {
        return instance;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public Map<String, String> listProps() {

        Map<String, String> props = new HashMap<>();

        props.put("Identifier", getIdentifier());

        props.put("Correct Security group", getCorrectSecurityGroup());
        props.put("Incorrect Security group", getIncorrectSecurityGroup());

        props.put("Correct Email for 'Allow list'", getCorrectEmailForAllowlist());
        props.put("Incorrect Email for 'Allow list'", getIncorrectEmailForAllowlist());

        props.put("Email for login", getEmailForLogin());
        props.put("Password", getPassword());

        return props;
    }

    public String getIdentifier() {
        return getProperty("identifier", "interprefy-adfs-idp");
    }

    public String getCorrectSecurityGroup() {
        return getProperty("securityGroup", "Interprefy-Room-H");
    }

    public String getIncorrectSecurityGroup() {
        return INCORRECT_SECURITY_GROUP;
    }

    public String getCorrectEmailForAllowlist() {
        return getProperty("emailForAllowlist", "adfs01@interprefy.com");
    }

    public String getIncorrectEmailForAllowlist() {
        return INCORRECT_EMAIL_FOR_ALLOWLIST;
    }

    public String getEmailForLogin() {
        return getProperty("emailForLogin", "adfs01@adserver.interprefy.com");
    }

    public String getPassword() {
        return getProperty("password", "1nt3rPr3f!");
    }
}
