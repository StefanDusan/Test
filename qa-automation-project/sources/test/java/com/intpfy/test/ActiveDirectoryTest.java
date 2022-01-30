package com.intpfy.test;


import com.intpfyqa.settings.ActiveDirectorySettings;

public interface ActiveDirectoryTest {

    ActiveDirectorySettings SETTINGS = ActiveDirectorySettings.instance();

    static String getIdentifier() {
        return SETTINGS.getIdentifier();
    }

    static String getCorrectSecurityGroup() {
        return SETTINGS.getCorrectSecurityGroup();
    }

    static String getIncorrectSecurityGroup() {
        return SETTINGS.getIncorrectSecurityGroup();
    }

    static String getCorrectEmailForAllowlist() {
        return SETTINGS.getCorrectEmailForAllowlist();
    }

    static String getIncorrectEmailForAllowlist() {
        return SETTINGS.getIncorrectEmailForAllowlist();
    }

    static String getEmailForLogin() {
        return SETTINGS.getEmailForLogin();
    }

    static String getPassword() {
        return SETTINGS.getPassword();
    }
}
