package tests.seleniumClass;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Driver;

public class BaseTest {

    protected BaseTest() {
    }
    // protected constructor should be created here in order to be used from other classes (private one cannot be used from other classes)
    // protected can be shared between all classes under the same package (in this case, under "seleniumClass"

    @BeforeMethod
    public void startUp() {
        Driver.initDriver();
    }

    @AfterMethod
    public void tearDown() {
        Driver.quitDriver();
    }
}
