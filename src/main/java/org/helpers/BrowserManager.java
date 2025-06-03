package org.helpers;

import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


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

    public static void takeScreenshot() {
        File srcfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm");
        String fileName = UUID.randomUUID().toString();
        File targetFile = new File("screenshots\\" + dateFormat.format(new Date())  + fileName + ".jpg");
        try {
            FileUtils.copyFile(srcfile, targetFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}