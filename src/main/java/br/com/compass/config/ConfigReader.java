package br.com.compass.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final String CONFIG_FILE = "src/main/resources/config.properties";
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(CONFIG_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAdminEmail() {
        return properties.getProperty("admin.EMAIL");
    }

    public static String getAdminPassword() {
        return properties.getProperty("admin.PASSWORD");
    }

    public static String getAdminName() {
        return properties.getProperty("admin.NAME");
    }

    public static String getAdminCpf() {
        return properties.getProperty("admin.CPF");
    }
} 