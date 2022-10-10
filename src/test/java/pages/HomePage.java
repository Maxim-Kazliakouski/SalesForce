package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(xpath = "//input[@role='combobox']")
    WebElement search;
    private final String HOME_PAGE_URL = BASE_URL + "/lightning/setup/SetupOneHome/home";

    public HomePage(WebDriver browser) {
        super(browser);
    }

    @Step("Open Home page")
    public void open() {
        browser.get(HOME_PAGE_URL);
    }

    public boolean isOpened() {
        return waitForVisibility(search);
    }
}
