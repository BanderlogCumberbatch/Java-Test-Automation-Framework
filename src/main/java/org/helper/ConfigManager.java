package org.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigManager {
    private static final String CONFIG_FILE = "config.yaml";
    private static Map<String, Object> config;
    private static final Properties SYSTEM_PROPS = new Properties();
    private static final StringSubstitutor SUBSTITUTOR = new StringSubstitutor(
            key -> SYSTEM_PROPS.getProperty(key, "")
    );

    static {
        loadConfig();
        SYSTEM_PROPS.putAll(System.getProperties());
    }

    private static void loadConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try (InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            //noinspection unchecked
            config = mapper.readValue(input, Map.class);
            // Подмена плейсхолдеров в конфиге сразу после загрузки
            replacePlaceholdersInConfig(config);

        } catch (Exception e) {
            throw new RuntimeException("Critical config error", e);
        }
    }

    private static void replacePlaceholdersInConfig(Map<String, Object> node) {
        node.forEach((key, value) -> {
            if (value instanceof String strValue) {
                node.put(key, SUBSTITUTOR.replace(strValue));
            } else if (value instanceof Map) {
                //noinspection unchecked
                replacePlaceholdersInConfig((Map<String, Object>) value);
            }
        });
    }

    // Методы доступа к конфигу (без изменений)
    public static String getBrowserName() {
        return get("browser.name", "chrome");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("browser.headless", "false"));
    }

    public static String getBaseUrl() {
        String env = get("environment", "dev");
        return get("environments." + env + ".base_url");
    }

    public static long getImplicitWait() {
        return Long.parseLong(get("browser.implicit_wait", "10"));
    }

    public static String get(String key) {
        return get(key, key);
    }

    public static String get(String key, String defaultValue) {
        Object value = resolveYamlValue(key);
        String result = value != null ? value.toString() : defaultValue;
        return SUBSTITUTOR.replace(result); // Дополнительная подмена (на случай динамических значений)
    }

    private static Object resolveYamlValue(String key) {
        String[] path = key.split("\\.");
        Map<String, Object> node = config;

        for (int i = 0; i < path.length - 1; i++) {
            //noinspection unchecked
            node = (Map<String, Object>) node.get(path[i]);
            if (node == null) break;
        }

        return node != null ? node.get(path[path.length - 1]) : null;
    }
}