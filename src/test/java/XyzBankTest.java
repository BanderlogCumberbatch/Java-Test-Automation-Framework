import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class XyzBankTest {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver = new ChromeDriver(chromeOptions);
        try {
            driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject");
            Thread.sleep(2000);
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> d.findElement((By.xpath("//*[contains(@ng-click, 'manager()')]"))));
            element.click();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}