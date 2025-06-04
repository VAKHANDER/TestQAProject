import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.*;

@Getter
public class LoginPage {

    private final SelenideElement loginField = $("[name='user']");
    private final SelenideElement passwordField = $("[name='password']");
    private final SelenideElement submitButton = $("#button_submit_login_form");
    private final SelenideElement forgotPasswordLink = $("[class='mira-default-login-page-link']");
    private final SelenideElement showPasswordButton = $("#show_password");

    public void open() {
        Selenide.open("https://qa.copy.mirapolis.ru/mira/");
    }

    public void clickLogin() {
        submitButton.click();
    }

    public void login(String login, String password) {
        setLogin(login);
        setPassword(password);
        clickLogin();
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private void setLogin(String login) {
        loginField.setValue(login);
    }

    private void setPassword(String password) {
        passwordField.setValue(password);
    }
}