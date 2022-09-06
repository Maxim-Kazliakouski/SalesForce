package tests.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import static java.lang.String.format;

public class ButtonAtCreateUserPage extends BasePage {
    WebDriver browser;
    String buttonName;

    public ButtonAtCreateUserPage(WebDriver browser, String buttonName) {
        super(browser);
        this.browser = browser;
        this.buttonName = buttonName;
    }

    public void click() {
        WebElement button = browser.findElement(By.xpath(format("//button[text()='%s']", buttonName)));
        waitForElementClickable(button);
        button.click();
    }
}
