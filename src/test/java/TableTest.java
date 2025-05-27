import org.helpers.BrowserManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pages.TablePage;
import org.testng.Assert;

import java.util.Collections;

class TableTest {
    TablePage page;

    @BeforeEach
    void setUp() {
        page = new TablePage();
    }

    @Test
    void testTable() {
        page.open("https://www.w3schools.com/html/html_tables.asp");
        Assert.assertFalse(page.isSortedByColumn(3), "Первый столбец не отсортирован");
        Assertions.assertEquals(Collections.singletonList("Alfreds Futterkiste Maria Anders Germany"), page.getSelectedRowString(3, "Germany"), "Данные не сходятся с ожидаемой строкой");
    }

    @AfterAll
    static void tearDown() {
        BrowserManager.refresh();
        BrowserManager.quit();
    }
}