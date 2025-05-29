package org.helpers;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxOptions;


public class BrowserManager {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = ConfigManager.getBrowserName();
            boolean headless = ConfigManager.isHeadless();

            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions ffOptions = new FirefoxOptions();
                    if (headless) {
                        ffOptions.addArguments("--headless");
                    }
                    else {
                        String[] ffWindowSize = ConfigManager.getFirefoxWindowSize();
                        ffOptions.addArguments("--width=" +
                                ffWindowSize[0]);
                        ffOptions.addArguments("--height=" +
                                ffWindowSize[1]);
                    }
                    driver = new FirefoxDriver(ffOptions);
                    break;
                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) {
                        chromeOptions.addArguments("--headless");
                    }
                    else {
                        chromeOptions.addArguments("--window-size=" +
                                ConfigManager.getChromeWindowSize());
                    }
                    driver = new ChromeDriver(chromeOptions);
            }

        }
        return driver;
    }

    /**
     * Обновить браузер
     */
    @Step("Refresh browser")
    public static void refresh() {
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

    /**
     * Выйти из браузера
     */
    @Step("Quit browser")
    public static void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}