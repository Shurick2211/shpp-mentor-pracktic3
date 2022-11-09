package com.onimko;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class for gets properties from file.
 */
public class LoadProperties {
    /**The logger for this class*/
    private static final Logger log = LoggerFactory.getLogger(LoadProperties.class);

    /**The name of System property*/
    private static final String TYPE = "type";

    /**The Properties for reading*/
    private final Properties props;

    /**
     * Constructor for loads property for fileName.
     * @param fileName the name property's file.
     */
    public LoadProperties(String fileName) {
        props = new Properties();
        InputStream rootPath = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(fileName);
        try {
            props.load(rootPath);
        } catch (IOException e) {
            log.error("File {} not found! ", fileName, e);
            throw new MyRuntimeException(e);
        }
        log.debug("File {} was reading!", fileName);
    }

    /**
     * The method for get property's value for an input key.
     * @param inProp - the input key.
     * @return the value.
     */
    public String getProperty(String inProp) {
        log.debug("Return property: {}", inProp);
        return props.getProperty(inProp);
    }

    /**
     * Method gets system's property with name in the const TYPE.
     * If type is null then return default value - int.
     * @return String - property.
     */
    public String getTypeProperty() {
        String sysProp = System.getProperty(TYPE);
        log.debug("System property: {}", sysProp != null ? sysProp : "default");
        return sysProp != null ? sysProp : "int";
    }
}
