package com.hahn.software.config;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new FileNotFoundException("config.properties file not found in classpath!");
            }
            properties.load(input);
            replacePlaceholders();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void replacePlaceholders() {
        String apiBackend = System.getenv("API_BACKEND");
        if (apiBackend != null) {
            String apiUrl = properties.getProperty("api.backend");
            if (apiUrl != null) {
                properties.setProperty("api.backend", apiUrl.replace("${API_BACKEND}", apiBackend));
            }
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
