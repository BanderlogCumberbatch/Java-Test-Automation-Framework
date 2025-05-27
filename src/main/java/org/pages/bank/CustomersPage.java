package org.pages.bank;

import io.qameta.allure.Step;
import org.helpers.bank.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.utils.SortChecker;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс страницы со списком пользователей.
 */
public class CustomersPage extends BankManagerPage {

    public CustomersPage(final WebDriver webDriver) { super(webDriver); }

    /**
     * Столбец таблицы с именами.
     */
    @FindBy(xpath = "//table[contains(@class, 'table-bordered')]/tbody/tr/td[1]")
    WebElement productsName;

    /**
     * Кнопка сортировки по имени.
     */
    @FindBy(xpath = "//table[contains(@class, 'table-bordered')]/thead/tr/td[1]/a")
    WebElement sortByFirstNameButton;

    /**
     * Селектор выбирающий из таблицы список всех имён.
     */
    private final String productNamesSelector = "//table[contains(@class, 'table-bordered')]/tbody/tr/td[1]";

    /**
     * Селектор выбирающий из таблицы все данные строки с определенными значениями 1-го, 2-го и 3-го элементов (имя, фамилия, почтовый индекс).
     */
    private final String productDataSelector = "//table[contains(@class, 'table-bordered')]/tbody/tr[" +
            "td[1][text()='%s'] and " +
            "td[2][text()='%s'] and " +
            "td[3][text()='%s']]";

    /**
     * Селектор выбирающий из таблицы 5-й элемент строки (кнопку удаления) с определённым значением 1-го элемента (имя).
     */
    private final String deleteButtonSelector = "//table[contains(@class, 'table-bordered')]/tbody/tr[td[1][text()='%s']]/td[5]/button";

    /**
     * Возвращает список со строкой со всеми данными выбранного пользователя.
     * @return List<String>
     */
    @Step("Get selected customers data string")
    public final List<String> getSelectedCustomers(String firstName, String lastName, String postCode) {
        Wait.waitUntilVisible(driver, productsName);
        return driver
                .findElements(By.xpath(String.format(productDataSelector, firstName, lastName, postCode)))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список имён пользователей.
     * @return List<String>
     */
    @Step("Get customers first names list")
    public final List<String> getCustomersFirstNames() {
        Wait.waitUntilVisible(driver, productsName);
        return driver
                .findElements(By.xpath(productNamesSelector))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список имён пользователей с определённым именем.
     * @return List<String>
     */
    @Step("Get customers first names with first name list")
    public final List<String> getCustomersFirstNames(String firstName) {
        Wait.waitUntilVisible(driver, productsName);
        return driver
                .findElements(By.xpath(productNamesSelector))
                .stream()
                .map(WebElement::getText)
                .filter(s -> s.equals(firstName))
                .collect(Collectors.toList());
    }

    /**
     * Проверяет отсортирована ли таблица по именам.
     * @return boolean
     */
    @Step("Check if the table is sorted")
    public boolean isSortedByFirstName() {
        List<String> names = getCustomersFirstNames();
        return SortChecker.isSorted(names);
    }

    /**
     * Проверяет отсортирована ли таблица по именам в обратном порядке.
     * @return boolean
     */
    @Step("Check if the table is sorted in reverse order")
    public boolean isSortedByFirstNameInReverse() {
        List<String> names = getCustomersFirstNames();
        return SortChecker.isSortedInReverse(names);
    }

    /**
     * Сортирует таблицу по именам.
     */
    @Step("Sort table by first names")
    public CustomersPage sortByFirstName() {
        Wait.waitThenCLick(driver, sortByFirstNameButton);
        return new CustomersPage(driver);
    }

    /**
     * Удаляет из таблицы пользователя с именем.
     */
    @Step("Delete customer with first name")
    public CustomersPage deleteCustomerWithFirstName(String firstName) {
        WebElement deleteButton = driver.findElement(By.xpath(String.format(deleteButtonSelector, firstName)));
        Wait.waitThenCLick(driver, deleteButton);
        return new CustomersPage(driver);
    }
}
