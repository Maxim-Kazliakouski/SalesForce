package tests.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static java.lang.String.format;

public class Input {
    WebDriver browser;
    String label;

    public Input(WebDriver browser, String label) {
        this.browser = browser;
        this.label = label;
    }

    public void write(String text){
        browser.findElement(By.xpath(format("//label[text()='%s']/..//input", label))).sendKeys(text);
    }
}
