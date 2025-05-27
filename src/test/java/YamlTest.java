// YamlTest.java
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.helpers.TestCaseLoader.executeYamlTest;

class YamlTest {

    @Test
    void executeBankTest() {
        executeYamlTest("test/bank_test.yaml");
    }

    @Test
    void executeNavigationTest() {
        executeYamlTest("test/nav_test.yaml");
    }

    @Test
    void executeSauceTest() {
        executeYamlTest("test/sauce_test.yaml");
    }

    @AfterEach
    void afterTests() {
        executeYamlTest("test/after_test.yaml");
    }
}
