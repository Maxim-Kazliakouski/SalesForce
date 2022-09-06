package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
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

    public void login(String username, String password) {
        browser.findElement(USERNAME).sendKeys(username);
        browser.findElement(PASSWORD).sendKeys(password);
        browser.findElement(LOGIN_BUTTON).click();
    }
}
