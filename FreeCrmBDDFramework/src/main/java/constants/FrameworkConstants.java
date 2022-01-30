package constants;

public class FrameworkConstants {

    private FrameworkConstants() {
        // private constructor
    }

    private static final String CHROMEDRIVERPATH = System.getProperty("user.dir") + "/src/test/java/resources/drivers.chrome.v94/chromedriver.exe";
    private static final String CONFIGFILEPATH = System.getProperty("user.dir") + "/src/test/java/resources/config/config.properties";
    private static final int WAITTIMESECONDS = 10;

    public static String getChromeDriverPath() {
        return CHROMEDRIVERPATH;
    }

    public static String getConfigFilePath() {
        return CONFIGFILEPATH;
    }

    public static int getWaittimeseconds() {
        return WAITTIMESECONDS;
    }

}
