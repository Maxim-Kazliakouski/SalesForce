package dto.driverManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeBrowserManager extends BrowserManager {

    @Override
    public void createBrowser(String headlessMode) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("window-size=1920x1080");
        options.addArguments("--disable-notifications");
        options.setHeadless(Boolean.parseBoolean(headlessMode));
        browser = new ChromeDriver(options);
        browser.manage().deleteAllCookies();
    }
}
