package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static java.lang.String.format;
import static org.testng.Assert.assertTrue;
@Log4j2
public class LoginPage extends BasePage {

    @FindBy(id = "error")
    WebElement errorMissedPassword;
    @FindBy(xpath = "//label[text()='Remember me']")
    WebElement textCheckboxRememberMe;
    @FindBy(id = "rememberUn")
    WebElement checkboxRememberMe;
    @FindBy(xpath = "//a[text()='Forgot Your Password?']")
    WebElement forgotPasswordLink;
    @FindBy(id = "username")
    WebElement USERNAME;
    @FindBy(id = "password")
    WebElement PASSWORD;
    @FindBy(id = "Login")
    WebElement loginButton;

    public LoginPage(WebDriver browser) {
        super(browser);
    }

    public static String getClientID() {
        return "3MVG9fe4g9fhX0E5qL4iy8FRVTFbj4GiI1_XIxZT1LuG4cX1MTDHGmR9YAg.or2_VnWf6_jJtseR1_QOYFe88";
    }

    public static String getClientSecret() {
        return "4BB30BC4D932875F595B9E26F3125732351400BF83491DB91EFCC30C968B43D7";
    }

    public void open() {
        browser.get(BASE_URL);
    }

    public boolean isOpened() {
        return waitForElementClickable(loginButton);
    }

    @Step("Click on forgot password link at Login page")
    public LoginPage clickForgotPasswordLink() {
        log.info("Click on 'Forgot password' link'");
        forgotPasswordLink.click();
        return this;
    }

    @Step("Getting error message")
    public String errorMessage() {
        log.info("Getting error message...");
        waitForVisibility(errorMissedPassword);
        log.info(format("Error message has been got --> %s", errorMissedPassword.getText()));
        return errorMissedPassword.getText();
    }

    @Step("Checkbox 'Remember me' at the Login Page")
    public boolean isCheckboxRememberMeAppeared() {
        log.info("Checking 'Remember me' checkbox at 'Login Page'");
        return waitForVisibility(textCheckboxRememberMe);
    }

    @Step("Checkbox 'Remember me' has unchecked position")
    public boolean isCheckboxRememberMeUnchecked() {
        log.info("Checking 'Remember me' checkbox is unchecked at 'Login Page'");
        waitForVisibility(checkboxRememberMe);
        return checkboxRememberMe.isSelected();
    }

    @Step("'Forgot Password' link at the 'Forgot Password' page")
    public boolean isForgotPasswordLinkAppeared() {
        log.info("Checking 'Forgot password' link' at the 'Forgot password' page");
        return waitForVisibility(forgotPasswordLink);
    }

    public void signUp(String username, String password) {
        assertTrue(waitForElementClickable(USERNAME), "There is no field USERNAME");
        USERNAME.sendKeys(username);
        assertTrue(waitForElementClickable(PASSWORD), "The 'PASSWORD' field isn't clickable");
        assertTrue(waitForVisibility(PASSWORD), "There is no 'PASSWORD' field");
        PASSWORD.sendKeys(password);
        waitForVisibility(loginButton);
        loginButton.click();
    }
}