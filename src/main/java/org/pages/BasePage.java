package org.pages;

import org.helper.BrowserManager;
import org.helper.ConfigManager;
import org.helper.Wait;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = BrowserManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getImplicitWait()));
    }

    public void open() {
        driver.get(ConfigManager.getBaseUrl());
    }

    public void open(String url) {
        driver.get(url);
    }

    public void type(By locator, String text) {
        WebElement element = Wait.waitUntilVisible(driver, locator);
        element.clear();
        element.sendKeys(text);
    }

    public void click(By locator) {
        Wait.waitThenCLick(driver, locator);
    }

    public String getText(By locator) {
        WebElement element = Wait.waitUntilVisible(driver, locator);
        return element.getText();
    }

    public void acceptAlert() {
        Alert alert = driver.switchTo().alert();
        Wait.waitUntilAlert(driver);
        alert.accept();
    }
}