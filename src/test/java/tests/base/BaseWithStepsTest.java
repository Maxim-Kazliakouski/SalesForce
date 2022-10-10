package tests.base;

import com.github.javafaker.Faker;
import dto.driverManager.BrowserManager;
import factories.BrowserManagerFactory;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import pages.CreateContactPage;
import pages.ForgotYourPasswordPage;
import pages.HomePage;
import steps.CreateContactSteps;
import steps.LoginSteps;
import utils.PropertyReader;
import utils.SFAPIUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static factories.BrowserManagerFactory.DriverType.CHROME;
import static factories.BrowserManagerFactory.DriverType.FIREFOX;

@Log4j2
@Listeners(TestListener.class)
public abstract class BaseWithStepsTest {
    public WebDriver browser;
    public BrowserManager browserManager;
    public Faker faker;
    public LoginSteps loginSteps;
    public CreateContactSteps createContactSteps;
    public HomePage homePage;
    public ForgotYourPasswordPage forgotYourPasswordPage;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Parameters({"browserType", "headlessMode", "isLogin"})
    @BeforeMethod(description = "Opening browser")
    public void setup(@Optional("chrome") String browserType, ITestContext testContext,
                      @Optional("false") String headlessMode,
                      @Optional("true") String isLogin) {
        if (browserType.equals("chrome")) {
            browserManager = BrowserManagerFactory.getBrowser(CHROME);

            username = System.getProperty("USERNAME_CHROME", PropertyReader.getProperty("usernameChrome"));
            password = System.getProperty("PASSWORD_CHROME", PropertyReader.getProperty("passwordChrome"));
        } else if (browserType.equals("firefox")) {
            browserManager = BrowserManagerFactory.getBrowser(FIREFOX);
            username = System.getProperty("USERNAME_FIREFOX", PropertyReader.getProperty("usernameFirefox"));
            password = System.getProperty("PASSWORD_FIREFOX", PropertyReader.getProperty("passwordFirefox"));
//
        }
        browser = browserManager.getBrowser(headlessMode);
        // для связки со скриншотами в TestListener
        testContext.setAttribute("browser", browser);
        faker = new Faker();
        loginSteps = new LoginSteps(browser);
        homePage = new HomePage(browser);
        createContactSteps = new CreateContactSteps(browser);
        forgotYourPasswordPage = new ForgotYourPasswordPage(browser);
        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod(alwaysRun = true, description = "closing browser")
    public void tearDown() {
        if (browser != null) {
            browser.quit();
        }
    }

    @Step("Deleting already created contact by id...")
    public void deleteCreatedContact() {
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