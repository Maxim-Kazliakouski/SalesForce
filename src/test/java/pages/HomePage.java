package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage{
    private final By SEARCH = By.xpath("//input[@role='combobox']");


    public HomePage(WebDriver browser) {
        super(browser);
    }

    public void open(){

        final String HOME_PAGE_URL = "https://oxagile-dev-ed.lightning.force.com/lightning/setup/SetupOneHome/home";
        browser.get(HOME_PAGE_URL);
    }

    public boolean isOpened(){
        return waitForVisibility(SEARCH);
    }
}