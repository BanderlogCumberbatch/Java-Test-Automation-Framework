import org.framework.BasePage;
import org.framework.BrowserManager;
import org.openqa.selenium.By;
import org.junit.jupiter.api.*;

class BasicTest {
    BasePage page;

    @BeforeEach
    void setUp() {
        page = new BasePage();
    }

    @Test
    void testBaSick() {
        page.open("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/openAccount/");
        page.click(By.xpath("//*[contains(@ng-click, 'manager()')]"));
        page.click(By.xpath("//*[contains(@ng-click, 'addCust()')]"));
        page.type(By.xpath("//*[contains(@placeholder, 'First Name')]"), "First Name");
        page.type(By.xpath("//*[contains(@placeholder, 'Last Name')]"), "Last Name");
        page.type(By.xpath("//*[contains(@placeholder, 'Post Code')]"), "Post Code");
        page.click(By.xpath("//*[contains(text(),'Add Customer') and contains(@class, 'btn btn-default')]"));
        page.acceptAlert();
        page.click(By.xpath("//*[contains(@ng-click, 'showCust()')]"));

    }

    @AfterAll
    static void tearDown() {
        BrowserManager.quit();
    }
}