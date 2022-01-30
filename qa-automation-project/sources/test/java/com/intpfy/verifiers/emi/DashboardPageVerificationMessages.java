package com.intpfy.verifiers.emi;

final class DashboardPageVerificationMessages {

    static final String MULTIPLE_KEYCLOAK_ORGANIZATIONS_AVAILABLE = "Multiple Keycloak Organizations available.";
    static final String MULTIPLE_KEYCLOAK_ORGANIZATIONS_NOT_AVAILABLE = "Multiple Keycloak Organizations not available.";

    static final String KEYCLOAK_ORGANIZATIONS_COUNT_GREATER_THAN_ONE = "Keycloak Organizations count > 1.";

    static final String KEYCLOAK_ORGANIZATION_SELECTED = "Keycloak Organization '%s' selected.";

    static final String EVENTS_COUNT_NOT_EQUALS = "Events count not equals '%s'.";
    static final String USERS_PER_EVENT_COUNT_NOT_EQUALS = "Users per Event count not equals '%s'.";
    static final String USERS_CONNECTED_COUNT_NOT_EQUALS = "Users connected count not equals '%s'.";
    static final String LANGUAGES_COUNT_NOT_EQUALS = "Languages count not equals '%s'.";
    static final String MINUTES_CONNECTION_PER_USER_COUNT_NOT_EQUALS = "Minutes connection per User count not equals '%s'.";
    static final String ALL_GENERAL_STATS_PARAMS_EQUAL = "All General Stats params equal '%s'.";

    private DashboardPageVerificationMessages() {
    }
}
