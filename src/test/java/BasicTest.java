import org.pages.Page;
import org.helper.BrowserManager;
import org.openqa.selenium.By;
import org.junit.jupiter.api.*;
import org.pages.TablePage;
import org.testng.asserts.SoftAssert;

import java.util.Collections;

class BasicTest {
    Page page;

    @BeforeEach
    void setUp() {
        page = new Page();
    }

    @Test
    void testBasic() {
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
        Assertions.assertEquals("Home", page.getText(By.xpath("//*[contains(@class, 'btn home')]")), "Данные не сходятся");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(Collections.singletonList("First Name Last Name Post Code Delete"), page.getSelectedRowString(1, "First Name"), "Данные не сходятся с ожидаемой строкой");
        page.sortByColumn(1);
        softAssert.assertTrue(page.isSortedByColumnInReverse(1), "Первый столбец не отсортирован в обратном порядке");
        page.sortByColumn(1);
        softAssert.assertTrue(page.isSortedByColumn(1), "Первый столбец не отсортирован");
        softAssert.assertEquals(Collections.singletonList("First Name"), page.getSelectedElementOfColumn("First Name", 1), "Удаляемая строка не существует до удаления");
        page.deleteElement(5, 1, "First Name");
        softAssert.assertNotEquals(Collections.singletonList("First Name"), page.getSelectedElementOfColumn("First Name", 1), "Удаляемая строка не существует после удаления");
        softAssert.assertAll();
    }

    @AfterAll
    static void tearDown() {
        BrowserManager.refresh();
        BrowserManager.quit();
    }
}