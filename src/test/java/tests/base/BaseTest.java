package tests.base;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import pages.*;
import tests.utils.SFAPIUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Listeners(TestListener.class)
public class BaseTest {
    public WebDriver browser;
    public Faker faker;
    public LoginPage loginPage;
    public HomePage homePage;
    public ForgotYourPasswordPage forgotYourPasswordPage;
    public CreateContactPage createContactPage;

    @Parameters({"browserType", "headlessMode", "isLogin"})
    @BeforeMethod(description = "Opening browser")
    public void setup(@Optional("chrome") String browserType, ITestContext testContext,
                      @Optional("false") String headlessMode,
                      @Optional("true") String isLogin) {
        if (browserType.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("window-size=1920x1080");
            options.addArguments("--disable-notifications");
            options.setHeadless(headlessMode.equals("true"));
            browser = new ChromeDriver(options);
            browser.manage().deleteAllCookies();
        } else if (browserType.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("window-size=1920х1080");
            options.addArguments("--disable-notifications");
            options.addPreference("dom.webnotifications.enabled", false);
            options.setHeadless(headlessMode.equals("true"));
            browser = new FirefoxDriver(options);
            browser.manage().deleteAllCookies();
        }
        // для связки с скриншотами в TestListener
        testContext.setAttribute("browser", browser);
        faker = new Faker();
        loginPage = new LoginPage(browser);
        homePage = new HomePage(browser);
        createContactPage = new CreateContactPage(browser);
        forgotYourPasswordPage = new ForgotYourPasswordPage(browser);
        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        if (isLogin.equals("false")) {
            System.out.println("Skip");
        } else if (browserType.equals("chrome")) {
            loginPage.signUp(System.getProperty("usernameChrome"), System.getProperty("passwordChrome"));
        } else if (browserType.equals("firefox")) {
            loginPage.signUp(System.getProperty("usernameFirefox"), System.getProperty("passwordFirefox"));
        }
    }

    @AfterMethod(alwaysRun = true, description = "closing browser")
    public void tearDown() {
        if (browser != null) {
            browser.quit();
        }
    }

    public void postConditionDeleteContact() {
        Assert.assertTrue(CreateContactPage.isContactNameAppeared(), "Contact name hadn't appeared at 'Create Contact page' after creating contact");
        String idContact = browser.getCurrentUrl().substring(63, 81);
        try {
            SFAPIUtils.deleteContact(idContact);
            System.out.println("Contact has been deleted!!!");
        } catch (IOException | ParseException error) {
            System.out.println("Error! --> " + error.toString().substring(10));
        }
    }

    public void makeScreenShoot() throws IOException {
        TakesScreenshot scrShot = ((TakesScreenshot) browser);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy::h-m-s");
        Date date = new Date();
        File DestFile = new File("src/test/java/tests/screenshoots/image:" + dateFormat.format(date) + ".jpg");
        FileUtils.copyFile(SrcFile, DestFile);
    }
}