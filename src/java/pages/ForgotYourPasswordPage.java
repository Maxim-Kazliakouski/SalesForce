package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotYourPasswordPage extends BasePage {
    private final By FORGOT_PASSWORD_TITLE = By.xpath("//h1[text()='Forgot Your Password']");

    public ForgotYourPasswordPage(WebDriver browser) {
        super(browser);
    }

    public void open() {
        String FORGOT_PASSWORD_URL = "https://oxagile-dev-ed.my.salesforce.com/secur/forgotpassword.jsp?locale=us";
        browser.get(FORGOT_PASSWORD_URL);
    }

    public boolean isOpened() {
        return waitForVisibility(FORGOT_PASSWORD_TITLE);
    }
}
