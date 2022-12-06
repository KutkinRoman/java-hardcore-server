package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

abstract class AbstractConfigFromFile {

    private final Properties properties;

    public AbstractConfigFromFile (String fileName) {
        this.properties = createProperties(fileName);
    }

    protected String getProperty (String key){
        return properties.getProperty (key);
    }

    private Properties createProperties (String fileName) {
        try (InputStream in = getClass ().getClassLoader ().getResourceAsStream (fileName)) {
            Properties properties = new Properties ();
            properties.load (in);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }
}
