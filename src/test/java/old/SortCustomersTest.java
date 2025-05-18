package old;

import org.pages.BankManagerPage;
import org.pages.CustomersPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Класс тестов сортировки пользователей для globalsqa.com.
 */
public class SortCustomersTest extends BaseTest {

    BankManagerPage bankManagerPage;

    CustomersPage customersPage;

    /**
     * Действия перед тестом.
     */
    @BeforeMethod
    public final void setup() {
        bankManagerPage = startPage.goToBankManagerPage();
    }

    /**
     * Тест сортировки пользователей.
     */
    @Test(description = "Sort customers test")
    public void sortCustomersTest() {
        customersPage = bankManagerPage
                .goToCustomersPage();
        customersPage.sortByFirstName();

        Assert.assertTrue(customersPage.isSortedByFirstNameInReverse(), "Имена не отсортированы в обратном порядке");
        customersPage.sortByFirstName();
        Assert.assertTrue(customersPage.isSortedByFirstName(), "Имена не отсортированы");
    }

    /**
     * Действия после теста.
     */
    @AfterMethod
    public final void goToStartAndClearCookies() {
        customersPage.goToStartPage();
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

}