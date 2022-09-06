package tests.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import okhttp3.*;
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
import pages.BasePage;
import pages.CreateContactPage;
import pages.HomePage;
import pages.LoginPage;
import tests.TestListener;

import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.testng.Assert.assertEquals;

@Listeners(TestListener.class)
public class BaseTest {
    public WebDriver browser;
    public LoginPage loginPage;
    public HomePage homePage;
    public CreateContactPage createContactPage;
    private static final String client_id = "3MVG9fe4g9fhX0E5qL4iy8FRVTFbj4GiI1_XIxZT1LuG4cX1MTDHGmR9YAg.or2_VnWf6_jJtseR1_QOYFe88";
    private static final String client_secret = "4BB30BC4D932875F595B9E26F3125732351400BF83491DB91EFCC30C968B43D7";
    private static final String password = "M@xkaz1991";
    private static final String username = "max@oxa.sandbox";

    @Parameters({"browserType", "headlessMode"})
    @BeforeMethod
    public void setup(@Optional("chrome") String browserType, @Optional("false") String headlessMode) {
        if (browserType.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("window-size=1920x1080");
            options.addArguments("--disable-notifications");
            options.setHeadless(headlessMode.equals("true"));
            browser = new ChromeDriver(options);
//            setCookie(sidValue);
        } else if (browserType.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("window-size=1920х1080");
            options.addArguments("--disable-notifications");
            options.addPreference("dom.webnotifications.enabled", false);
            options.setHeadless(headlessMode.equals("true"));
            browser = new FirefoxDriver(options);
//            setCookie(sidValue);
        }

        loginPage = new LoginPage(browser);
        homePage = new HomePage(browser);
        createContactPage = new CreateContactPage(browser);
        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterMethod(dependsOnGroups = "create user",
            description = "post condition for certain test --> deleting user")
    public void deleteContact() throws IOException, ParseException, InterruptedException {
        CreateContactPage.isContactNameAppeared();
        String idContact = browser.getCurrentUrl().substring(63, 81);
        deleteContact(idContact);
        System.out.println("Contact has been deleted!!!");
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

    public static String accessToken() throws IOException, ParseException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://login.salesforce.com/services/oauth2/token?grant_type=password&" +
                        "client_id=" + client_id +
                        "&client_secret=" + client_secret +
                        "&password=" + password +
                        "&username=" + username + "")
                .method("POST", body)
                .addHeader("Cookie", "BrowserId=k_j6AiyKEe20rYmjFe7Zzg; CookieConsentPolicy=0:0; LSKey-c$CookieConsentPolicy=0:0")
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