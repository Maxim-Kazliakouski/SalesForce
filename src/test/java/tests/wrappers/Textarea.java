package tests.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static java.lang.String.format;

public class Textarea {
    WebDriver browser;
    String label;

    public Textarea(WebDriver browser, String label) {
        this.browser = browser;
        this.label = label;
    }

    public void input(String text) {
        browser.findElement(By.xpath(format("//label[contains(text(), '%s')]//..//textarea", label))).sendKeys(text);
    }
}