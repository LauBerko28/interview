package io.xyz.utils.common_utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Dynamically loads test data to variables, according to the env value.
 * First checks if "env" value was passed from maven command line.
 * If yes, it will use that value.
 * If not, it will use value in local configuration.properties file
 */

public class Environment {
    public static final String URL;

    static { //runs once in beginning when we use the class- static block
        //class to read from .properties files
        Properties properties = null;
        String environment = System.getProperty("env" ) != null ? System.getProperty("env" ) : ConfigurationReader.getProperties("env" );
        //String environment = ConfigurationReader.get("environment");

        try {
            // the following try/catch block is used to load configuration settings from .properties files. After these operations, the "properties" object will contain all the key-value pairs defined in the file, which can then be accessed using methods like getProperty(String key).
            String path = System.getProperty("user.dir" ) + "/src/test/resources/env/" + environment + ".properties";

            FileInputStream input = new FileInputStream(path); // Creates a new FileInputStream that reads bytes from a file located at the specified path
            properties = new Properties(); // Instantiates a new Properties object, which is essentially a specialized HashMap designed to store configuration values as key-value pairs
            properties.load(input); // Reads the file content from the input stream and loads it into the Properties object
            input.close(); // Closes the file input stream to release system resources and avoid memory leaks
        } catch (IOException e) {
            e.printStackTrace();
        }

        URL = properties.getProperty("url" );
}}
