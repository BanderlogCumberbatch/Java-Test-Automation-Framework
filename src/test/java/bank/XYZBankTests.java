package bank;

import generator.TestDataGenerator;
import org.pages.bank.BankManagerPage;
import org.pages.bank.CustomersPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.utils.StringFinder;
import java.util.Collections;

/**
 * Класс тестов для globalsqa.com.
 */
public class XYZBankTests extends BaseTest {

    @DataProvider(name = "Add customers data")
    public Object[][] dpMethod() {
        return new Object[][]{
                {TestDataGenerator.generatePostCode(), "LastName", TestDataGenerator.generateFirstNameFromPostCode()},
                {TestDataGenerator.generatePostCode(), "LastName" , TestDataGenerator.generateFirstNameFromPostCode()},
                {TestDataGenerator.generatePostCode(), "LastName", TestDataGenerator.generateFirstNameFromPostCode()}
        };
    }

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
     * Тест добавления пользователя.
     */
    @Test(description = "Add customer test", dataProvider = "Add customers data" )
    public void addCustomerTest(String postCode, String lastName, String firstName) {
        customersPage = bankManagerPage
                .goToAddCustomerPage()
                .addCustomer(firstName, lastName, postCode)
                .addCustomer(firstName, lastName, postCode)
                .goToCustomersPage();

        Assert.assertEquals(customersPage.getSelectedCustomers(firstName, lastName, postCode), Collections.singletonList(String.format("%s %s %s Delete", firstName, lastName, postCode)), "Данные не сходятся с ожидаемой строкой вида: firstName lastName postCode Delete)");
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
     * Тест удаления пользователя.
     */
    @Test(description = "Delete customer test")
    public void deleteCustomerTest() {
        customersPage = bankManagerPage
                .goToCustomersPage()
                .sortByFirstName()
                .sortByFirstName();
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