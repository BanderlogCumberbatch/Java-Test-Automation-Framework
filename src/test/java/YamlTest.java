// YamlTest.java
import org.helper.TestCaseLoader;
import org.helper.TestExecutor;
import org.junit.jupiter.api.Test;
import org.model.TestCase;

import java.util.List;

class YamlTest {

    @Test
    void executeBasicTest() {
        List<TestCase> testCases = TestCaseLoader.loadTestCases("basic_test.yaml");
        TestExecutor executor = new TestExecutor();
        testCases.forEach(executor::execute);
        // Debug
        System.out.println(testCases);
    }
}
