import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static org.helpers.TestCaseLoader.executeYamlTest;


@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class YamlTest {

    @Test(description = "XYZ Bank tests")
    public void executeBankTest() {
        executeYamlTest("test/bank_test.yaml");
    }

    @Test(description = "Example.com tests")
    public void executeNavigationTest() {
        executeYamlTest("test/nav_test.yaml");
    }

    @Test(description = "Saucedemo.com tests")
    public void executeSauceTest() {
        executeYamlTest("test/sauce_test.yaml");
    }

    @AfterMethod
    public void afterTests() {
        executeYamlTest("test/after_test.yaml");
    }
}
