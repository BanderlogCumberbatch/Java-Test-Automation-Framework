import org.pages.BasePage;
import org.helper.BrowserManager;
import org.openqa.selenium.By;
import org.junit.jupiter.api.*;
import org.pages.TablePage;
import org.testng.Assert;

import java.util.Collections;

class BasicTest {
    BasePage page;

    @BeforeEach
    void setUp() {
        page = new BasePage();
    }

    @Test
    void testBaSick() {
        page.open();
        page.click(By.xpath("//*[contains(@ng-click, 'manager()')]"));
        page.click(By.xpath("//*[contains(@ng-click, 'addCust()')]"));
        page.type(By.xpath("//*[contains(@placeholder, 'First Name')]"), "First Name");
        page.type(By.xpath("//*[contains(@placeholder, 'Last Name')]"), "Last Name");
        page.type(By.xpath("//*[contains(@placeholder, 'Post Code')]"), "Post Code");
        page.click(By.xpath("//*[contains(text(),'Add Customer') and contains(@class, 'btn btn-default')]"));
        page.acceptAlert();
        page.click(By.xpath("//*[contains(@ng-click, 'showCust()')]"));
        TablePage page = new TablePage();
        Assertions.assertEquals(Collections.singletonList("First Name Last Name Post Code Delete"), page.getColumnSelectedElement(1, "First Name"), "Данные не сходятся с ожидаемой строкой");
        page.sortByColumn(1);
        Assert.assertTrue(page.isSortedByColumnInReverse(1), "Первый столбец не отсортирован в обратном порядке");
        page.sortByColumn(1);
        Assert.assertTrue(page.isSortedByColumn(1), "Первый столбец не отсортирован");
        Assert.assertEquals(Collections.singletonList("First Name"), page.getSelectedElementOfColumn("First Name", 1), "Удаляемая строка не существует до удаления");
        page.deleteElement(5, 1, "First Name");
        Assert.assertNotEquals(Collections.singletonList("First Name"), page.getSelectedElementOfColumn("First Name", 1), "Удаляемая строка не существует после удаления");
    }

    @AfterAll
    static void tearDown() {
        BrowserManager.quit();
    }
}