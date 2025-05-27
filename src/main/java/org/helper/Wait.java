package org.helper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wait {

    public static WebElement waitUntilVisible(WebDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.findElement(locator));
    }

    // С кастомным таймаутом
    public static WebElement waitUntilVisible(WebDriver driver, By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(d -> d.findElement(locator));
    }

    public static WebElement waitThenCLick(WebDriver driver, By locator) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.findElement(locator));
        element.click();
        return element;
    }

    // С кастомным таймаутом
    public static WebElement waitThenClick(WebDriver driver, By locator, int timeoutSeconds) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(d -> d.findElement(locator));
        element.click();
        return element;
    }

    public static void waitUntilAlert(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.alertIsPresent());
    }

    // С кастомным таймаутом
    public static void waitUntilAlert(WebDriver driver, int timeoutSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.alertIsPresent());
    }
}
