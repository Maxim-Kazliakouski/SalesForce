package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotYourPasswordPage extends BasePage {
    private final By FORGOT_PASSWORD_TITLE = By.xpath("//h1[text()='Forgot Your Password']");
    private final static String FORGOT_PASSWORD_URL = BASE_URL + "/secur/forgotpassword.jsp?locale=us";

    public ForgotYourPasswordPage(WebDriver browser) {
        super(browser);
    }

    @Step("Open Forgot password page")
    public void open() {
        browser.get(FORGOT_PASSWORD_URL);
    }

    public boolean isOpened() {
        return waitForVisibility(FORGOT_PASSWORD_TITLE);
    }
}
