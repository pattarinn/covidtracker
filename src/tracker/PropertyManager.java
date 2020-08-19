package tracker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class for handling property.
 * 
 * @author Pattarin Wongwaipanich
 */
public class PropertyManager {
    private static final String PROPERTIES_FILE = "tracker.properties";
    private static PropertyManager propertyManager = null;
    private Properties props = null;

    private PropertyManager() {
        loadProperties(PROPERTIES_FILE);
    }

    /**
     * Get instance of singleton class
     * 
     * @return instance of this class
     */
    public synchronized static PropertyManager getInstance() {
        if (propertyManager == null) {
            propertyManager = new PropertyManager();
        }
        return propertyManager;
    }

    /**
     * Load property from file.
     * 
     * @param fileName property's filename
     */
    private void loadProperties(String fileName) {
        props = new Properties();
        InputStream in = null;
        ClassLoader loader = this.getClass().getClassLoader();
        in = loader.getResourceAsStream(fileName);
        try {
            props.load(in);
            in.close();
        } catch (IOException e) {
            System.err.println("Cannot read property file");
            System.exit(1);
        }
    }

    /**
     * Get value of property by its name.
     * 
     * @param name of the property
     * @return value of name
     */
    public String getProperty(String name) {
        return props.getProperty(name);
    }

}