package com.intpfy.util;

import com.intpfy.user.OrganizationUser;
import com.intpfy.user.AdminUser;
import com.intpfy.user.BaseUser;
import com.intpfy.user.ManagerUser;
import com.intpfyqa.Environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public final class UserUtil {

    private UserUtil() {
    }

    private static Properties properties;

    private synchronized static Properties getProperties() {

        if (properties == null) {

            properties = new Properties();

            try {
                String environment = Environment.getEnvironmentName().toLowerCase(Locale.ROOT);

                switch (environment) {

                    case "uat":
                        properties.load(UserUtil.class.getResourceAsStream("/data/users.uat.properties"));
                        return properties;
                    case "stage":
                        properties.load(UserUtil.class.getResourceAsStream("/data/users.stage.properties"));
                        return properties;
                    case "prod":
                        properties.load(UserUtil.class.getResourceAsStream("/data/users.prod.properties"));
                        return properties;
                    default:
                        throw new RuntimeException("No users properties specified for environment: " + environment);
                }
            } catch (IOException e) {
                throw new RuntimeException("Cannot load user properties.");
            }
        }
        return properties;
    }

    public static AdminUser getAdminUser() {
        Properties props = getProperties();
        String email = props.getProperty("admin.email");
        String password = props.getProperty("admin.password");
        return new AdminUser(email, password);
    }

    /**
     * @return Organization user which should be used to create / delete events.
     */
    public static OrganizationUser getEventOrganizationUser() {

        Properties props = getProperties();

        String email = props.getProperty("event.organization.email");
        String password = props.getProperty("event.organization.password");

        return new OrganizationUser(email, password);
    }

    /**
     * @return List of Organization users which should be used for Active Directory / Keycloak tests.
     */
    public static List<OrganizationUser> getOrganizationUsers() {

        Properties props = getProperties();

        String[] emails = props.getProperty("organization.email").split(",");
        String[] passwords = props.getProperty("organization.password").split(",");

        List<OrganizationUser> resultList = new ArrayList<>();

        for (int i = 0; i < emails.length; i++) {
            resultList.add(new OrganizationUser(emails[i], passwords[i]));
        }

        return List.copyOf(resultList);
    }

    public static ManagerUser getManagerUser() {
        Properties props = getProperties();
        String email = props.getProperty("manager.email");
        String password = props.getProperty("manager.password");
        return new ManagerUser(email, password);
    }

    public static BaseUser getCurrentUser() {
        return WebContextUtil.getCurrentContext().getCurrentUser();
    }

    public static void setCurrentUser(BaseUser user) {
        WebContextUtil.getCurrentContext().setCurrentUser(user);
    }
}
