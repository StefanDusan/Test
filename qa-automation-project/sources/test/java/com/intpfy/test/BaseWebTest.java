package com.intpfy.test;

import com.intpfy.authorization.Authorizer;
import com.intpfy.authorization.KeycloakAuthorizer;
import com.intpfy.config.web.event.EventConfigurator;
import com.intpfy.config.web.event.KeycloakEventConfigurator;
import com.intpfy.config.web.user.OrganizationConfigurator;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.model.event.Event;
import com.intpfy.model.user.OrganizationModel;
import com.intpfy.run.web.WebRunSession;
import com.intpfy.user.BaseUser;
import com.intpfy.util.BrowserUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfyqa.Environment;
import com.intpfyqa.logging.LogManager;
import com.intpfyqa.logging.impl.log4j_html.SuiteObjectIdentifier;
import com.intpfyqa.logging.impl.log4j_html.UnloggingException;
import com.intpfyqa.run.RunSession;
import com.intpfyqa.run.RunSessions;
import com.intpfyqa.settings.WebSettings;
import com.intpfyqa.test.BaseTest;
import com.intpfyqa.utils.TestUtils;
import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.lang.reflect.Method;

public abstract class BaseWebTest extends BaseTest {

    private static final ThreadLocal<Boolean> EXCEPTION_THROWN_IN_AFTER_METHOD = ThreadLocal.withInitial(() -> false);

    @Override
    @BeforeMethod(alwaysRun = true)
    public final void beforeMethod(ITestContext context, Method method, Object[] params) {
        initTestLogger(method, params);
        initRunSession(method);
        if (EXCEPTION_THROWN_IN_AFTER_METHOD.get()) {
            restartBrowserSessionBeforeCurrentTest();
            EXCEPTION_THROWN_IN_AFTER_METHOD.set(false);
        }
        customBeforeMethod(context, method, params);
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void afterMethod(Method method, ITestResult iTestResult) {
        try {
            getRunSession().switchToDefaultContext();
            getRunSession().closeAllContextsExceptDefault();
            customAfterMethod();
        } catch (Throwable t) {
            EXCEPTION_THROWN_IN_AFTER_METHOD.set(true);
            if (!(t instanceof UnloggingException)) {
                logger.get().fail("Teardown", "Critical error occurred: " + t.getMessage());
                logger.get().debug("Teardown", "Critical error occurred: " + t.getMessage() + "\n" +
                        TestUtils.getThrowableFullDescription(t));
            }
        }

        LogManager.getSuiteLogger().endTest(testObjectIdentifier.get());
        runSession.get().free();
    }

    @Override
    protected RunSession createInitSuiteRunSession() {
        return createRunSession();
    }

    @Override
    protected void initSuite() {
        getLogger().info("Suite init", Environment.instance().getPropertiesAsString());
    }

    @Override
    protected SuiteObjectIdentifier createSuiteObject() {
        String browserName = Environment.instance().getBrowser();
        SuiteObjectIdentifier identifier = new SuiteObjectIdentifier("Suite - " + browserName);
        Environment.instance().getProperties().forEach(identifier::addProperty);
        WebSettings.instance().listProps().forEach(identifier::addProperty);
        return identifier;
    }

    @Override
    protected RunSession createRunSession() {
        return new WebRunSession();
    }

    @Override
    protected void onSessionRegistered(RunSession session) {
        WebContextUtil.switchToNewContext();
    }

    @Override
    protected void customBeforeMethod(ITestContext context, Method method, Object[] params) {

        super.customBeforeMethod(context, method, params);

        webRtcTestCustomBeforeMethod();
        eventTestCustomBeforeMethod();
        organizationTestCustomBeforeMethod();
    }

    @Override
    protected void customAfterMethod() {

        try {
            super.customAfterMethod();

            webRtcTestCustomAfterMethod();
            activeDirectoryTestCustomAfterMethod();
            keycloakTestCustomAfterMethod();
            eventTestCustomAfterMethod();
            fileTestCustomAfterMethod();

        } finally {
            organizationTestCustomAfterMethod();
        }

    }

    protected LoginPage navigateToDomain(String domain) {

        String currentUrl = getCurrentUrl();
        String urlToOpen = createUrlWithDomain(domain);

        LoginPage loginPage;

        if (currentUrl == null || !currentUrl.startsWith(urlToOpen)) {

            BrowserUtil.openUrl(urlToOpen);
            loginPage = new LoginPage(BrowserUtil.getActiveWindow());

        } else {
            loginPage = getAuthorizer().logOut();
        }

        loginPage.assertIsOpened();

        return loginPage;
    }

    protected LoginPage navigateToDefaultDomain() {
        return navigateToDomain("");
    }

    public static String createUrlWithDomain(String domain) {
        return domain.isEmpty() ? Environment.instance().getAppUrl() : getUrlWithDomain(domain);
    }

    private static String getUrlWithDomain(String domain) {
        // Example: https://uat.interprefy.com -> https://domain.uat.interprefy.com
        String appUrl = Environment.instance().getAppUrl();
        int subdomainStartIndex = appUrl.indexOf(":") + 3;
        return appUrl.substring(0, subdomainStartIndex) + domain + "." + appUrl.substring(subdomainStartIndex);
    }

    private String getCurrentUrl() {
        return WebContextUtil.getCurrentContext().getBrowserSession().getActiveWindow().getCurrentUrl();
    }

    private void webRtcTestCustomBeforeMethod() {
        if (this instanceof WebRtcTest) {
            restartBrowserSessionBeforeCurrentTest();
        }
    }

    private void eventTestCustomBeforeMethod() {

        if (this instanceof EventTest) {

            EventTest test = (EventTest) this;

            Event event = test.getEvent();
            BaseUser user = test.getEventUser();

            EventConfigurator configurator;

            if (test instanceof KeycloakEventTest) {

                String domain = KeycloakTest.getDomain();
                navigateToDomain(domain);
                configurator = KeycloakEventConfigurator.getInstance();

            } else {
                configurator = EventConfigurator.getInstance();
            }

            configurator.create(event, user);
        }
    }

    private void organizationTestCustomBeforeMethod() {
        if (this instanceof OrganizationTest) {
            OrganizationModel organizationModel = ((OrganizationTest) this).getOrganizationModel();
            OrganizationConfigurator.getInstance().edit(organizationModel);
        }
    }

    private void webRtcTestCustomAfterMethod() {
        if (this instanceof WebRtcTest) {
            BrowserUtil.closeAllWindowsExceptMain();
        }
    }

    private void eventTestCustomAfterMethod() {

        if (this instanceof EventTest) {

            EventTest test = (EventTest) this;

            Event event = test.getEvent();
            BaseUser user = test.getEventUser();

            EventConfigurator configurator;

            if (test instanceof KeycloakEventTest) {

                String domain = KeycloakTest.getDomain();
                navigateToDomain(domain);
                configurator = KeycloakEventConfigurator.getInstance();
            } else {
                configurator = EventConfigurator.getInstance();
            }

            configurator.delete(event, user);
        }
    }

    private void fileTestCustomAfterMethod() {
        if (this instanceof FileTest) {
            File file = ((FileTest) this).getFile();
            FileUtils.deleteQuietly(file);
        }
    }

    private void organizationTestCustomAfterMethod() {
        if (this instanceof OrganizationTest) {
            OrganizationTest test = (OrganizationTest) this;
            test.putUser(test.getOrganizationUser());
        }
    }

    private void activeDirectoryTestCustomAfterMethod() {
        if (this instanceof ActiveDirectoryTest) {
            BrowserUtil.closeAllWindowsExceptMain();
            restartBrowserSessionAfterCurrentTest();
        }
    }

    private void keycloakTestCustomAfterMethod() {
        if (this instanceof KeycloakTest) {
            restartBrowserSessionAfterCurrentTest();
        }
    }

    private void restartBrowserSessionBeforeCurrentTest() {
        ((WebRunSession) RunSessions.current()).restartBrowserSessionBeforeCurrentTest();
    }

    private void restartBrowserSessionAfterCurrentTest() {
        ((WebRunSession) RunSessions.current()).restartBrowserSessionAfterCurrentTest();
    }

    private Authorizer getAuthorizer() {
        return this instanceof KeycloakTest ? KeycloakAuthorizer.getInstance() : Authorizer.getInstance();
    }
}
