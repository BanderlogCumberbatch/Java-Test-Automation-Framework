package org.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.model.TestCase;
import java.io.InputStream;
import java.util.List;

public class TestCaseLoader {
    public static List<TestCase> loadTestCases(String resourcePath) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();

        try (InputStream input = TestCaseLoader.class.getClassLoader()
                .getResourceAsStream(resourcePath)) {
            return mapper.readValue(input, mapper.getTypeFactory()
                    .constructCollectionType(List.class, TestCase.class));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки тест-кейсов", e);
        }
    }
}