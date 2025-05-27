package bank;

import org.pages.bank.BankManagerPage;
import org.pages.bank.CustomersPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.utils.StringFinder;
import java.util.Collections;

/**
 * Класс тестов удаления пользователей для globalsqa.com.
 */
public class DeleteCustomersTest extends BaseTest {

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
     * Тест удаления пользователей.
     */
    @Test(description = "Delete customer test")
    public void deleteCustomerTest() {
        customersPage = bankManagerPage
                .goToCustomersPage();
        String firstName = StringFinder.getTheMostAverage(customersPage.getCustomersFirstNames());

        Assert.assertEquals(customersPage.getCustomersFirstNames(firstName), Collections.singletonList(firstName), "Удаляемая строка не существует до удаления");
        customersPage.deleteCustomerWithFirstName(firstName);
        Assert.assertNotEquals(customersPage.getCustomersFirstNames(firstName), Collections.singletonList(firstName), "Удаляемая строка существует после удаления");
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