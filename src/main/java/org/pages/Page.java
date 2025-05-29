package org.pages;

import io.qameta.allure.Step;
import org.helpers.BrowserManager;
import org.helpers.ConfigManager;
import org.helpers.Wait;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public Page() {
        this.driver = BrowserManager.getDriver();
        this.wait = new WebDriverWait(driver, ConfigManager.getImplicitWait());
    }

    /**
     * Открыть базовый URL
     */
    @Step("Open base URL")
    public void open() {
        driver.get(ConfigManager.getBaseUrl());
    }

    /**
     * Открыть URL
     */
    @Step("Open URL")
    public void open(String url)
    {
        driver.get(url);
    }

    /**
     * Ввести данные в элемент
     */
    @Step("Type data")
    public void type(By locator, String text) {
        WebElement element = Wait.waitUntilVisible(driver, locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Кликнуть на элемент
     */
    @Step("Click on element")
    public void click(By locator) {
        Wait.waitThenCLick(driver, locator);
    }

    /**
     * Получить текст из элемента
     * @return String
     */
    @Step("Get text from element")
    public String getText(By locator) {
        WebElement element = Wait.waitUntilVisible(driver, locator);
        return element.getText();
    }

    /**
     * Принять всплывающий алёрт
     */
    @Step("Accept alert")
    public void acceptAlert() {
        Alert alert = driver.switchTo().alert();
        Wait.waitUntilAlert(driver);
        alert.accept();
    }

    /**
     * Проверка видимости элемента
     * @return Boolean
     */
    @Step("Check visible")
    public boolean checkElementVisible(By locator) {
        try {
            return Wait.waitUntilVisible(driver, locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Проверка активности элемента (enabled/disabled)
     * @return Boolean
     */
    @Step("Check enabled")
    public boolean checkElementEnabled(By locator) {
        WebElement element = Wait.waitUntilVisible(driver, locator);
        return element.isEnabled();
    }

    /**
     * Получение текущего URL
     * @return String
     */
    @Step("Get current URL")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Обновление страницы
     */
    @Step("Refresh page")
    public void refreshPage() {
        driver.navigate().refresh();
    }

    /**
     * Навигация назад
     */
    @Step("Navigate back")
    public void navigateBack() {
        driver.navigate().back();
    }

    /**
     * Навигация вперёд
     */
    @Step("Navigate forward")
    public void navigateForward() {
        driver.navigate().forward();
    }

    /**
     * Очистить поле ввода
     */
    @Step("Clear field")
    public void clearField(By locator) {
        WebElement element = Wait.waitUntilVisible(driver, locator);
        element.clear();
    }

}