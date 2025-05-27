import org.helpers.BrowserManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pages.LoginPage;

public class LoginTest {
    LoginPage page;

    @BeforeEach
    void setUp() {
        page = new LoginPage();
    }

    @Test
    void Login() {
        page.open("https://www.saucedemo.com/");
        page.login("standard_user", "secret_sauce");
    }

    @AfterAll
    static void tearDown() {
        BrowserManager.refresh();
        BrowserManager.quit();
    }
}
