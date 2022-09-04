package tests;

import org.testng.annotations.Test;
import pages.BasePage;
import tests.base.BaseTest;

import static org.testng.Assert.assertTrue;

public class LoginPageTest extends BaseTest {

    @Test
    public void successfulLogin() throws InterruptedException {
//        loginPage.open();
        BasePage.waitForPageLoaded();
        assertTrue(loginPage.isOpened(), "Login page wasn't opened!");
//        loginPage.login("max@oxa.sandbox", "M@xkaz1991");
    }
}
