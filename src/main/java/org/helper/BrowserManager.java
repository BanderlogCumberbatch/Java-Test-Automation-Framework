package org.helper;

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
                        ffOptions.addArguments("-headless=new");
                    }
                    else {
                        ffOptions.addArguments("--window-size=" +
                                ConfigManager.get("browser.window_size"));
                    }
                    driver = new FirefoxDriver(ffOptions);
                    break;
                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) {
                        chromeOptions.addArguments("-headless=new");
                    }
                    else {
                        chromeOptions.addArguments("--window-size=" +
                                ConfigManager.get("browser.window_size"));
                    }
                    driver = new ChromeDriver(chromeOptions);
            }

        }
        return driver;
    }

    public static void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}