package tests.seleniumClass;

import enums.ConfigProperties;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.OrangeHRMLoginPage;
import utils.ReadPropertyFile;

public final class OrangeHRMTest extends BaseTest {

    private OrangeHRMTest() {
        // private constructor
    }

    @Test
    public void loginTest() {
        // to use any of pages defined inside pages package, new objects for each page need to be created
        OrangeHRMLoginPage lp = new OrangeHRMLoginPage();

        // open login page, enter username and password, click on login button
        lp.pageNavigateTo()
                .enterUsername(ReadPropertyFile.getValue(ConfigProperties.USERNAME))
                .enterPassword(ReadPropertyFile.getValue(ConfigProperties.PASSWORD))
                .clickLoginButton()
                .clickWelcomeLink()
                .clickLogoutButton();
    }

    @Test
    public void loginWithInvalidCredentialsTest() {
        // to use any of pages defined inside pages package, new objects for each page need to be created
        OrangeHRMLoginPage lp = new OrangeHRMLoginPage();

        // open login page, enter username and password, click on login button
        lp.pageNavigateTo()
                .enterUsername(ReadPropertyFile.getValue(ConfigProperties.USERNAME))
                .enterPassword(ReadPropertyFile.getValue(ConfigProperties.PASSWORDINVALID))
                .clickLoginButton();
        Assert.assertEquals(lp.invalidCredentialsErrorText(), ReadPropertyFile.getValue(ConfigProperties.INVALIDCREDENTIALSERRORMESSAGE));
    }
}
