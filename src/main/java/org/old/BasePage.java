package org.pages;

import io.qameta.allure.Step;
import org.helpers.Wait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Базовая страница.
 */
public class BasePage {

    protected final WebDriver driver;

    /**
     * Кнопка для возвращения на стартовую страницу.
     */
    @FindBy(xpath = "//*[contains(@ng-click, 'home()')]")
    WebElement toStartPageButton;

    /**
     * Заголовок сайта.
     */
    @FindBy(xpath = "//*[contains(@class, 'mainHeading')]")
    WebElement mainHeading;

    public BasePage(final WebDriver webDriver) {
        try {
            PageFactory.initElements(webDriver, this);
            this.driver = webDriver;
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Переходит на стартовую страницу.
     * @return текущий экземпляр класса
     */
    @Step("Go to start page")
    public StartPage goToStartPage() {
        Wait.waitThenCLick(driver, toStartPageButton);
        return new StartPage(driver);
    }
}
