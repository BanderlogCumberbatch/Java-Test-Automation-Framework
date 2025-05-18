package org.pages;
import io.qameta.allure.Step;
import org.helpers.Wait;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Класс страницы для добавления пользователей.
 */
public class AddCustomerPage extends BankManagerPage {
    /**
     * Поле для ввода имени.
     */
    @FindBy(xpath = "//*[contains(@placeholder, 'First Name')]")
    WebElement firstNameInput;

    /**
     * Поле для ввода фамилии.
     */
    @FindBy(xpath = "//*[contains(@placeholder, 'Last Name')]")
    WebElement lastNameInput;

    /**
     * Поле для ввода почтового индекса.
     */
    @FindBy(xpath = "//*[contains(@placeholder, 'Post Code')]")
    WebElement postCodeInput;

    /**
     * Кнопка для добавления пользователя.
     */
    @FindBy(xpath = "//*[contains(text(),'Add Customer') and contains(@class, 'btn btn-default')]")
    WebElement addCustomerButton;

    public AddCustomerPage(final WebDriver webDriver) { super(webDriver); }

    /**
     * Добавляет пользователя.
     * @return текущий экземпляр класса
     */
    @Step("Add customer")
    public AddCustomerPage addCustomer(String firstName, String lastName, String postCode) {
        Wait.waitUntilVisible(driver, firstNameInput);
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        postCodeInput.sendKeys(postCode);
        addCustomerButton.click();
        Alert alert = driver.switchTo().alert();
        Wait.waitUntilAlert(driver);
        alert.accept();
        return new AddCustomerPage(driver);
    }
}
