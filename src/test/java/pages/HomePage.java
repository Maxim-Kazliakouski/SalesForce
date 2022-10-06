package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By SEARCH = By.xpath("//input[@role='combobox']");
    private final String HOME_PAGE_URL = BASE_URL + "/lightning/setup/SetupOneHome/home";

    public HomePage(WebDriver browser) {
        super(browser);
    }

    @Step("Open Home page")
    public void open() {
        browser.get(HOME_PAGE_URL);
    }

    public boolean isOpened() {
        return waitForVisibility(SEARCH);
    }
}
