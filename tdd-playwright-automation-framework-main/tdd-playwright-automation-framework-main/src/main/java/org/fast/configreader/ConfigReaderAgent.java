package org.fast.configreader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigReaderAgent {
    private static final Properties properties = new Properties();

    /**
     * Fetch the properties file dynamically and set all properties
     * @return Properties - returns in key and value format
     */
    public static Properties setProperties() {
        String env = System.getProperty("env") != null ? System.getProperty("env") : "sit";
        Path path = Path.of(System.getProperty("user.dir"))
                .resolve("src").resolve("test").resolve("resources").resolve("environment").resolve(env+".properties");
        try(InputStream inputStream = new FileInputStream(path.toString())) {
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }


    public static String getProperties(String propName) {
        return setProperties().getProperty(propName);
    }
}
