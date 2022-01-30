package com.intpfy.authorization;

import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.gui.pages.keycloak.KeycloakPage;
import com.intpfy.test.BaseWebTest;
import com.intpfy.user.BaseUser;
import com.intpfy.util.BrowserUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfyqa.settings.KeycloakSettings;

public class KeycloakAuthorizer extends Authorizer {

    private static final KeycloakAuthorizer instance = new KeycloakAuthorizer();

    private KeycloakAuthorizer() {
    }

    public static KeycloakAuthorizer getInstance() {
        return instance;
    }

    @Override
    DashboardPage openDashboardPage(LoginPage loginPage, BaseUser user) {

        KeycloakPage keycloakPage = loginPage.proceedToKeycloakPageAsAdmin();
        keycloakPage.assertIsOpened();

        return keycloakPage.signIn(user);
    }

    @Override
    LoginPage openLoginPageByRestartingBrowserSession() {

        String domain = KeycloakSettings.instance().getDomain();
        String urlToOpen = BaseWebTest.createUrlWithDomain(domain);
        WebContextUtil.restartBrowserSession(urlToOpen);

        return new LoginPage(BrowserUtil.getActiveWindow());
    }
}
