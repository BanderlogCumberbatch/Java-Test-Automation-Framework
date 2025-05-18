package org.pages;

import org.helpers.Wait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import io.qameta.allure.Step;

/**
 * Класс страницы менеджера.
 */
public class BankManagerPage extends BasePage {
    /**
     * Кнопка перехода на страницу добавления пользователя.
     */
    @FindBy(xpath = "//*[contains(@ng-click, 'addCust()')]")
    WebElement addCustomerPageButton;

    /**
     * Кнопка перехода на страницу входа в аккаунт.
     */
    @FindBy(xpath = "//*[contains(@ng-click, 'openAccount()')]")
    WebElement openAccountButton;

    /**
     * Кнопка перехода на страницу вывода списка пользователей.
     */
    @FindBy(xpath = "//*[contains(@ng-click, 'showCust()')]")
    WebElement customersButton;

    public BankManagerPage(final WebDriver webDriver) { super(webDriver); }

    /**
     * Переходит на страницу добавления пользователя.
     * @return текущий экземпляр класса
     */
    @Step("Go to add customer page")
    public AddCustomerPage goToAddCustomerPage() {
        Wait.waitThenCLick(driver, addCustomerPageButton);
        return new AddCustomerPage(driver);
    }

    /**
     * Переходит на страницу входа в аккаунт.
     * @return текущий экземпляр класса
     */
    @Step("Go to open account page")
    public OpenAccountPage goToOpenAccountPage() {
        Wait.waitThenCLick(driver, openAccountButton);
        return new OpenAccountPage(driver);
    }

    /**
     * Переходит на страницу со списком пользователей.
     * @return текущий экземпляр класса
     */
    @Step("Go to customers page")
    public CustomersPage goToCustomersPage() {
        Wait.waitThenCLick(driver, customersButton);
        return new CustomersPage(driver);
    }
}

