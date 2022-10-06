package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static org.testng.Assert.assertTrue;

public class LoginPage extends BasePage {
    private final By ERROR_MISSED_PASSWORD = By.id("error");
    private final By TEXT_CHECKBOX_REMEMBER_ME = By.xpath("//label[text()='Remember me']");
    private final By CHECKBOX_REMEMBER_ME = By.id("rememberUn");
    private final By FORGOT_PASSWORD_LINK = By.xpath("//a[text()='Forgot Your Password?']");
    public static final By USERNAME = By.id("username");
    public static final By PASSWORD = By.id("password");
    public static final By LOGIN_BUTTON = By.id("Login");

    public static String getClientID() {
        return "3MVG9fe4g9fhX0E5qL4iy8FRVTFbj4GiI1_XIxZT1LuG4cX1MTDHGmR9YAg.or2_VnWf6_jJtseR1_QOYFe88";
    }

    public static String getClientSecret() {
        return "4BB30BC4D932875F595B9E26F3125732351400BF83491DB91EFCC30C968B43D7";
    }

    public LoginPage(WebDriver browser) {
        super(browser);
        PageFactory.initElements(browser, this);
    }

    @Step("Opening login page")
    public void open() {
        browser.get(BASE_URL);
    }

    public boolean isOpened() {
        return waitForElementClickable(browser.findElement(LOGIN_BUTTON));
    }

    @Step("Click on forgot password link at Login page")
    public void clickForgotPasswordLink() {
        browser.findElement(FORGOT_PASSWORD_LINK).click();
    }


    @Step("Getting error message")
    public String getErrorMessage() {
        waitForVisibility(ERROR_MISSED_PASSWORD);
        return browser.findElement(ERROR_MISSED_PASSWORD).getText();
    }

    @Step("Checkbox 'Remember me' at the Login Page")
    public boolean isCheckboxRememberMeAppeared() {
        return waitForVisibility(TEXT_CHECKBOX_REMEMBER_ME);
    }

    @Step("Checkbox 'Remember me' has unchecked position")
    public boolean isCheckboxRememberMeUnchecked() {
        waitForVisibility(CHECKBOX_REMEMBER_ME);
//        return browser.findElement(CHECKBOX_REMEMBER_ME).isSelected();
        return browser.findElement(CHECKBOX_REMEMBER_ME).isSelected();
    }

    @Step("'Forgot Password Link' at the Forgot Password page")
    public boolean isForgotPasswordLinkAppeared() {
        return waitForVisibility(FORGOT_PASSWORD_LINK);
    }

    @Step("Login by '{username}', using password '{password}'")
    public void signUp(String username, String password) {
        open();
        assertTrue(isOpened(), "Login page wasn't opened!");
        assertTrue(waitForElementClickable(browser.findElement(USERNAME)), "There is no field USERNAME");
        browser.findElement(USERNAME).sendKeys(username);
        waitForVisibility(PASSWORD);
        browser.findElement(PASSWORD).sendKeys(password);
        waitForVisibility(LOGIN_BUTTON);
        browser.findElement(LOGIN_BUTTON).click();
    }
}
