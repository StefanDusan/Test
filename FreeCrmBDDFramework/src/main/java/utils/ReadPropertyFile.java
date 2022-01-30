package utils;

import constants.FrameworkConstants;
import enums.ConfigProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public final class ReadPropertyFile {

    private ReadPropertyFile() {
        // private constructor
    }

    private static final Properties properties = new Properties();

    static {
        // static block
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(FrameworkConstants.getConfigFilePath());
            properties.load(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(ConfigProperties key) {
        String value = "";
        value = properties.getProperty(key.name().toLowerCase());
        if (Objects.isNull(value)) {
            try {
                throw new Exception("Property name " + key + " is not found. Check 'config.properties'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }
}
