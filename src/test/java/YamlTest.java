// YamlTest.java
import org.junit.jupiter.api.Test;

import static org.helper.TestCaseLoader.executeYamlTest;

class YamlTest {

    @Test
    void executeBasicTest() {
        executeYamlTest("basic_test.yaml");
    }
}
