package org.framework;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE = "config.yaml";
    private static Map<String, Object> config;
    private static final Properties properties = new Properties();

    static {
        loadYamlConfig();
        loadSystemProperties();
    }

    private static void loadYamlConfig() {
        try (InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            Yaml yaml = new Yaml();
            config = yaml.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config file", e);
        }
    }

    private static void loadSystemProperties() {
        properties.putAll(System.getProperties());
    }

    public static String getBrowserName() {
        return getProperty("browser.name", "chrome");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("browser.headless", "false"));
    }

    public static String getBaseUrl() {
        String env = getProperty("environment", "dev");
        return getProperty("environments." + env + ".base_url");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(
                ConfigManager.getProperty("browser.implicit_wait", "10")
        );
    }

    public static String getProperty(String key, String defaultValue) {
        // 1. Check system properties
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }

        // 2. Check YAML config
        String[] parts = key.split("\\.");
        Map<String, Object> current = config;

        for (int i = 0; i < parts.length - 1; i++) {
            current = (Map<String, Object>) current.get(parts[i]);
            if (current == null) break;
        }

        Object value = current != null ? current.get(parts[parts.length-1]) : null;
        return value != null ? value.toString() : defaultValue;
    }

    public static String getProperty(String key) {
        return getProperty(key, null);
    }
}
