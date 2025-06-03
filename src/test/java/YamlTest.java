import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static org.helpers.TestCaseLoader.executeYamlTest;


@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class YamlTest {

    @Test(description = "XYZ Bank tests", priority = 0)
    public void executeBankTest() {
        executeYamlTest("test/add_client_bank_test.yaml");
        executeYamlTest("test/table_bank_test.yaml");
    }

    @Test(description = "Saucedemo.com tests", priority = 1)
    public void executeSauceTest() {
        executeYamlTest("test/sauce_test.yaml");
    }

    @Test(description = "Example.com tests", priority = 2)
    public void executeNavigationTest() {
        executeYamlTest("test/nav_test.yaml");
    }

    @AfterMethod
    public void afterTests() {
        executeYamlTest("test/after_test.yaml");
    }

}
