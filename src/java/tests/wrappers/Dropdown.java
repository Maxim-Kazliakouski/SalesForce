package tests.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import static java.lang.String.format;

public class Dropdown {
    WebDriver browser;
    String label;

    public Dropdown(WebDriver browser, String label) {
        this.browser = browser;
        this.label = label;
    }

    public void select(String option) throws InterruptedException {
        BasePage.waitForPageLoaded();
        JavascriptExecutor js = (JavascriptExecutor) browser;
        WebElement dropdown = browser.findElement(By.xpath(format("//label[text()='%s']//..//button[@class='slds-combobox__input slds-input_faux slds-combobox__input-value']", label)));
        js.executeScript("arguments[0].scrollIntoView(true)", dropdown);
        Thread.sleep(1000);

        BasePage.waitForElementClickable(dropdown);
        dropdown.click();
        WebElement dropdownOption = browser.findElement(By.xpath(format("//span[@class='slds-media__body']//span[@class='slds-truncate'][text()='%s']", option)));
        js.executeScript("arguments[0].scrollIntoView(true)", dropdownOption);
        BasePage.waitForElementClickable(dropdownOption);
        dropdownOption.click();
    }
}
