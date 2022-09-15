package tests.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;

import static java.lang.String.format;

public class Dropdown extends BasePage {
    String label;

    public Dropdown(WebDriver browser, String label) {
        super(browser);
        this.label = label;
    }

    public void select(String option) {
        By dropdown = By.xpath(format("//label[text()='%s']//..//button[@class='slds-combobox__input slds-input_faux slds-combobox__input-value']", label));
        clickJS(dropdown);
        By dropdownOption = By.xpath(format("//span[@class='slds-media__body']//span[@class='slds-truncate'][text()='%s']", option));
        clickJS(dropdownOption);
    }
}
