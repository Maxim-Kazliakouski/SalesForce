package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class LoginPage extends BasePage {
    private final By ERROR_MISSED_PASSWORD = By.id("error");
    private final By TEXT_CHECKBOX_REMEMBER_ME = By.xpath("//label[text()='Remember me']");
    private final By CHECKBOX_REMEMBER_ME = By.id("rememberUn");
    private final By FORGOT_PASSWORD_LINK = By.xpath("//a[text()='Forgot Your Password?']");
    private final By USERNAME = By.id("username");
    private final By PASSWORD = By.id("password");
    private final By LOGIN_BUTTON = By.id("Login");

    public LoginPage(WebDriver browser) {
        super(browser);
    }

    public void open() {
        String LOGIN_URL = "https://oxagile-dev-ed.my.salesforce.com/";
        browser.get(LOGIN_URL);
    }

    public boolean isOpened() {
        return waitForElementClickable(browser.findElement(LOGIN_BUTTON));
    }

    public void clickForgotPasswordLink() {
        click(FORGOT_PASSWORD_LINK);
    }

    public String getErrorMessage() {
        waitForVisibility(ERROR_MISSED_PASSWORD);
        return browser.findElement(ERROR_MISSED_PASSWORD).getText();
    }

    public boolean isCheckboxRememberMeAppeared() {
        return waitForVisibility(TEXT_CHECKBOX_REMEMBER_ME);
    }

    public boolean isCheckboxRememberMeUnchecked() {
        waitForVisibility(CHECKBOX_REMEMBER_ME);
        return browser.findElement(CHECKBOX_REMEMBER_ME).isSelected();
    }

    public boolean isForgotPasswordLinkAppeared() {
        return waitForVisibility(FORGOT_PASSWORD_LINK);
    }

    public void signUpForNegative(String username, String password) {
        browser.findElement(USERNAME).sendKeys(username);
        browser.findElement(PASSWORD).sendKeys(password);
        browser.findElement(LOGIN_BUTTON).click();
    }
}
