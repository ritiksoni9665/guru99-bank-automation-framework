package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    Properties prop;

    public ConfigReader() {

        try {

            FileInputStream fis =
            new FileInputStream("src/test/resources/config.properties");

            prop = new Properties();
            prop.load(fis);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return prop.getProperty("url");
    }

    public String getUsername() {
        return prop.getProperty("username");
    }

    public String getPassword() {
        return prop.getProperty("password");
    }
}