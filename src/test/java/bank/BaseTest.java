package bank;

import org.helpers.bank.PropertyProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.pages.bank.StartPage;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import java.time.Duration;


/**
 * Базовый класс для тестов.
 */
public class BaseTest {

    WebDriver driver;

    StartPage startPage;

    /**
     * Действия при инициализации.
     */
    @BeforeClass
    void init(final ITestContext context) {
        int pageLoadTimeout = Integer.parseInt(PropertyProvider.getInstance().getProperty("page.load.timeout"));

        driver = new ChromeDriver(new ChromeOptions()
                .addArguments("--remote-allow-origins=*")
                .addArguments("--disable-gpu")
                .addArguments("--start-maximized")
        .addArguments("--headless")  // Запуск без GUI
        .addArguments("--no-sandbox") // Для GitHub Actions
        .addArguments("--disable-dev-shm-usage")); // Улучшает стабильность в CI/CD

        driver.manage().window().maximize();
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        context.setAttribute("driver", driver);
        String webUrl = PropertyProvider.getInstance().getProperty("web.url");
        driver.get(webUrl);
        startPage = new StartPage(driver);

    }

    /**
     * Закрытие драйвера.
     */
    @AfterTest
    public final void tearDown() { driver.quit(); }
}
