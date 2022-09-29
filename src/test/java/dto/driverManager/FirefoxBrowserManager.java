package dto.driverManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxBrowserManager extends BrowserManager {

    @Override
    protected void createBrowser(String headlessMode) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("window-size=1920x1080");
        options.addArguments("--disable-notifications");
        options.addPreference("dom.webnotifications.enabled", false);
        options.setHeadless(headlessMode.equals("true"));
        browser = new FirefoxDriver(options);
        browser.manage().deleteAllCookies();
    }
}
