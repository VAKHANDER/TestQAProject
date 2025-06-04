import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPageTest {
    private static final String VALID_LOGIN = "fominaelena";
    private static final String VALID_PASSWORD = "1P73BP4Z";
    private static final String INVALID_LOGIN = "Test";
    private static final String INVALID_PASSWORD = "TestPassword";
    private static final String EMPTY_LOGIN = "";
    private static final String EMPTY_PASSWORD = "";
    private static final String INVALID_DATA_ALERT_MESSAGE = "Неверные данные для авторизации";
    private static final String EMPTY_DATA_ALERT_MESSAGE = "Неверные данные для авторизации.";

    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage();
        loginPage.open();
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @Test
    void testPageLoads() {
        assertTrue(loginPage.getLoginField().isDisplayed());
        assertTrue(loginPage.getPasswordField().isDisplayed());
        assertTrue(loginPage.getSubmitButton().isDisplayed());
        assertTrue(loginPage.getForgotPasswordLink().isDisplayed());
        assertTrue(loginPage.getShowPasswordButton().isDisplayed());
    }

    @Test
    void testShowPasswordButton(){
        assertEquals("password", loginPage.getPasswordField().getAttribute("type"));
        loginPage.getShowPasswordButton().click();
        assertEquals("text", loginPage.getPasswordField().getAttribute("type"));
    }

    @Test
    void testValidLogin() {
        loginPage.login(VALID_LOGIN, VALID_PASSWORD);
        assertTrue(WebDriverRunner.url().contains("type=myMeasureList"));
    }

    @Test
    void testInvalidLogin() {
        loginPage.login(INVALID_LOGIN, INVALID_PASSWORD);
        testAlert(INVALID_DATA_ALERT_MESSAGE);
        assertTrue(WebDriverRunner.url().contains("https://qa.copy.mirapolis.ru/mira/Do?"));
    }

    @Test
    void testEmptyFieldsLogin() {
        loginPage.login(EMPTY_LOGIN, EMPTY_PASSWORD);
        testAlert(EMPTY_DATA_ALERT_MESSAGE);
        assertTrue(WebDriverRunner.url().contains("https://qa.copy.mirapolis.ru/mira/Do?"));
    }

    @Test
    void testForgotPasswordLink() {
        assertTrue(loginPage.getForgotPasswordLink().isDisplayed());
        String href = loginPage.getForgotPasswordLink().getAttribute("href");
        assertTrue(href.contains("type=remindpassword"));
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private void testAlert(String message){
        try {
            WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = WebDriverRunner.getWebDriver().switchTo().alert();
            assertEquals(message, alert.getText());
            alert.accept();
        } catch (NoAlertPresentException e) {
            fail("Alert didnt exist");
        }
    }
}