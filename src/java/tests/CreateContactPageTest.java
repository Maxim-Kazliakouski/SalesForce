package tests;

import org.testng.annotations.Test;
import pages.BasePage;
import tests.base.BaseTest;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CreateContactPageTest extends BaseTest {

    @Test(description = "create user", groups = "create user")
    public void createNewUser() throws IOException, InterruptedException {
        String firstName = "Max";
        String salutationOption = "Mr.";
        String lastName = "Kazliakouski";
        loginPage.open();
        BasePage.waitForPageLoaded();
        assertTrue(loginPage.isOpened(), "Login page wasn't opened!");
//        Thread.sleep(1500);
        loginPage.login("max@oxa.sandbox", "M@xkaz1991");
        createContactPage.open();
//        assertTrue(createContactPage.isOpened(), "Create new user page wasn't open");
        createContactPage.createContact(firstName, lastName, "1234567",
                salutationOption, "Web", "Minsk, pr.Nezavisimosti");
        assertTrue(createContactPage.isToastMessageAppeared(),
                "The confirmation message about success created user wasn't appeared!");
        assertEquals(createContactPage.getToastMessageText(),
                "Contact \"" + salutationOption + " " + firstName + " " + lastName + "\" was created.");
    }

//    @Test
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
