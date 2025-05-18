package org.pages;

import org.helpers.Wait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import io.qameta.allure.Step;

/**
 * Класс стартовой страницы.
 */
public class StartPage extends BasePage {
    /**
     * Кнопка входа в качестве пользователя.
     */
    @FindBy(xpath = "//*[contains(@ng-click, 'customer()')]")
    WebElement customerLoginButton;

    /**
     * Кнопка входа в качестве менеджера.
     */
    @FindBy(xpath = "//*[contains(@ng-click, 'manager()')]")
    WebElement bankMangerLoginButton;

    public StartPage(final WebDriver webDriver) { super(webDriver); }

    /**
     * Переходит на страницу менеджера.
     * @return текущий экземпляр класса
     */
    @Step("Go to bank manager page")
    public BankManagerPage goToBankManagerPage() {
        Wait.waitThenCLick(driver, bankMangerLoginButton);
        return new BankManagerPage(driver);
    }
}