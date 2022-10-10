package factories;

import dto.driverManager.ChromeBrowserManager;
import dto.driverManager.BrowserManager;
import dto.driverManager.FirefoxBrowserManager;

public class BrowserManagerFactory {
    public enum DriverType {
        CHROME,
        FIREFOX,
//        IE,
//        SAFARI;
    }

    public static BrowserManager getBrowser(DriverType type) {
        BrowserManager browserManager;
        switch (type) {
            case CHROME:
                browserManager = new ChromeBrowserManager();
                break;
            case FIREFOX:
                browserManager = new FirefoxBrowserManager();
                break;
            default:
                System.out.println("There is no any browser");
                browserManager = null;
                break;
        }
        return browserManager;
    }
}
