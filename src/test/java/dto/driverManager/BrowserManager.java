package dto.driverManager;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;

public abstract class BrowserManager {
    protected WebDriver browser;

    protected abstract void createBrowser(String headlessMode);

    @Parameters({"headlessMode"})
    public WebDriver getBrowser(String headlessMode) {
        if (null == browser) {
            createBrowser(headlessMode);
        }
        return browser;
    }
}
