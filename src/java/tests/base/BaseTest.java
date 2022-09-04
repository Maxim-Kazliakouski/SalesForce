package tests.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import pages.CreateContactPage;
import pages.HomePage;
import pages.LoginPage;
import tests.TestListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Listeners(TestListener.class)
public class BaseTest {
    public WebDriver browser;
    public LoginPage loginPage;
    public HomePage homePage;
    public CreateContactPage createContactPage;

    @Parameters({"browserType", "headlessMode", "sid"})
    @BeforeMethod
    public void setup(@Optional("chrome") String browserType, @Optional("true") String headlessMode, String sidValue) {
        if (browserType.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("window-size=1920x1080");
            options.addArguments("--disable-notifications");
            options.setHeadless(headlessMode.equals("true"));
            browser = new ChromeDriver(options);
            setCookie(sidValue);
        } else if (browserType.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("window-size=1920х1080");
            options.addArguments("--disable-notifications");
            options.setHeadless(headlessMode.equals("true"));
            browser = new FirefoxDriver(options);
            setCookie(sidValue);
        }

        loginPage = new LoginPage(browser);
        homePage = new HomePage(browser);
        createContactPage = new CreateContactPage(browser);
        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (browser != null) {
            browser.quit();
        }
    }

    public void login(String USERNAME, String password) {
        browser.findElement(By.id("username")).sendKeys(USERNAME);
        browser.findElement(By.id("password")).sendKeys(password);
        browser.findElement(By.id("login-button")).click();
    }

//    public static void makeScreenShoot() throws IOException {
//        TakesScreenshot scrShot = ((TakesScreenshot) browser);
//        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
//        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy::h-m-s");
//        Date date = new Date();
//        File DestFile = new File("src/test/java/tests/screenshoots/image:" + dateFormat.format(date) + ".jpg");
//        FileUtils.copyFile(SrcFile, DestFile);
//    }

    public void setCookie(String sidValue) {
        String LOGIN_URL = "https://oxagile-dev-ed.my.salesforce.com/";
        browser.get(LOGIN_URL);
        Cookie cookie = new Cookie("sid", sidValue, "/", (new Date(System.currentTimeMillis() * 1000L)));
        browser.manage().addCookie(cookie);
    }
}

