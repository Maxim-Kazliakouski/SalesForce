package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ForgotYourPasswordPage extends BasePage {
    @FindBy(xpath = "//h1[text()='Forgot Your Password']")
    WebElement forgotPasswordTittle;
    private final static String FORGOT_PASSWORD_URL = BASE_URL + "/secur/forgotpassword.jsp?locale=us";

    public ForgotYourPasswordPage(WebDriver browser) {
        super(browser);
    }

    @Step("Open Forgot password page")
    public void open() {
        browser.get(FORGOT_PASSWORD_URL);
    }

    public boolean isOpened() {
        return waitForVisibility(forgotPasswordTittle);
    }
}
