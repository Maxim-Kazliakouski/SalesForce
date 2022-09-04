package tests;

import org.testng.annotations.Test;
import pages.BasePage;
import tests.base.BaseTest;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class CreateContactPageTest extends BaseTest {


    @Test
    public void createNewUser() throws IOException, InterruptedException {
//        loginPage.open();
        BasePage.waitForPageLoaded();
        assertTrue(loginPage.isOpened(), "Login page wasn't opened!");
//        loginPage.login("max@oxa.sandbox", "M@xkaz1991");
        homePage.open();
        assertTrue(homePage.isOpened(), "Home page wasn't opened!");
        createContactPage.open();
        assertTrue(createContactPage.isOpened(), "Create new user page wasn't open");
        createContactPage.createContact("Max", "Kazlaikouski", "1234567", "Mr.", "Web");
    }

    @Test
    public void openSalesPage() throws InterruptedException {
//        loginPage.open();
        BasePage.waitForPageLoaded();
        assertTrue(loginPage.isOpened(), "Login page wasn't opened!");
//        loginPage.login("max@oxa.sandbox", "M@xkaz1991");
        homePage.open();
        assertTrue(homePage.isOpened(), "Home page wasn't opened!");
        browser.get("https://oxagile-dev-ed.lightning.force.com/lightning");
    }
}
