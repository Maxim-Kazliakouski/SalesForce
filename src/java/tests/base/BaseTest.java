package tests.base;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import pages.*;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Listeners(TestListener.class)
public class BaseTest {
    public WebDriver browser;
    public Faker faker;
    public LoginPage loginPage;
    public HomePage homePage;
    public ForgotYourPasswordPage forgotYourPasswordPage;
    public CreateContactPage createContactPage;
    private final By USERNAME = By.id("username");
    private final By PASSWORD = By.id("password");
    private final By LOGIN_BUTTON = By.id("Login");
    private static final String client_id = "3MVG9fe4g9fhX0E5qL4iy8FRVTFbj4GiI1_XIxZT1LuG4cX1MTDHGmR9YAg.or2_VnWf6_jJtseR1_QOYFe88";
    private static final String client_secret = "4BB30BC4D932875F595B9E26F3125732351400BF83491DB91EFCC30C968B43D7";

    @Parameters({"browserType", "headlessMode", "isLogin", "username", "password"})
    @BeforeMethod
    public void setup(@Optional("chrome") String browserType,
                      @Optional("false") String headlessMode,
                      @Optional("true") String isLogin,
                      @Optional("chrome@oxa.sandbox") String username,
                      @Optional("M@xkaz1991") String password) {
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
        faker = new Faker();
        loginPage = new LoginPage(browser);
        homePage = new HomePage(browser);
        createContactPage = new CreateContactPage(browser);
        forgotYourPasswordPage = new ForgotYourPasswordPage(browser);
        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        if (isLogin.equals("false")) {
            System.out.println("Skip");
        } else signUp(username, password);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (browser != null) {
            browser.quit();
        }
    }

    public void postConditionDeleteContact() {
        assertTrue(CreateContactPage.isContactNameAppeared(), "Contact name hadn't appeared at 'Create Contact page' after creating contact");
        String idContact = browser.getCurrentUrl().substring(63, 81);
        try{
            deleteContact(idContact);
            System.out.println("Contact has been deleted!!!");
        }
        catch (IOException | ParseException error){
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

    public void setCookie(String sidValue) {
        String LOGIN_URL = "https://oxagile-dev-ed.my.salesforce.com/";
        browser.get(LOGIN_URL);
        Cookie cookie = new Cookie("sid", sidValue, "/", (new Date(System.currentTimeMillis() * 1000L)));
        browser.manage().addCookie(cookie);
    }

    public void signUp(String username, String password) {
        loginPage.open();
        assertTrue(loginPage.isOpened(), "Login page wasn't opened!");
        browser.findElement(USERNAME).sendKeys(username);
        browser.findElement(PASSWORD).sendKeys(password);
        browser.findElement(LOGIN_BUTTON).click();
    }

    public static String accessToken() throws IOException, ParseException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://login.salesforce.com/services/oauth2/token?grant_type=password&" +
                        "client_id=" + client_id +
                        "&client_secret=" + client_secret +
                        "&password=M@xkaz1991" +
                        "&username=chrome@oxa.sandbox")
                .method("POST", body)
                .addHeader("Cookie", "CookieConsentPolicy=0:0; LSKey-c$CookieConsentPolicy=0:0")
                .build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        // read json
        Object json = new JSONParser().parse(s);
        // Customize json into JSONObject
        JSONObject jsonObject = (JSONObject) json;
        // Get accessToken
        return (String) jsonObject.get("access_token");
    }

    public static void deleteContact(String idContact) throws IOException, ParseException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://oxagile-dev-ed.my.salesforce.com/services/data/v52.0/sobjects/Contact/" + idContact + "")
                .method("DELETE", body)
                .addHeader("Authorization", "Bearer " + accessToken() + "")
                .addHeader("Cookie", "BrowserId=k_j6AiyKEe20rYmjFe7Zzg; CookieConsentPolicy=0:1; LSKey-c$CookieConsentPolicy=0:1")
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(response.code(), 204, format("The contact with id '%s' hasn't been deleted or something went wrong!", idContact));
    }
}