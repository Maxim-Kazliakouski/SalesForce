package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.BasePage;
import tests.base.BaseTest;

import static org.testng.Assert.*;

public class LoginPageTest extends BaseTest {
    @DataProvider()
    public Object[][] loginData() {
        return new Object[][]{
                {"max@oxa.sandbox", "", "Please enter your password."},
                {"user", "123", "Please check your username and password. If you still can't log in, contact your Salesforce administrator."}
        };
    }

    @Test(description = "successful signUp with correct login data",
            groups = "valid login")
    public void successfulSignUp() {
        homePage.open();
        assertTrue(homePage.isOpened(), "Home page hadn't open");
    }

    @Test(dataProvider = "loginData",
            description = "parametrize test for unsuccessful signUp",
            groups = "unsuccessful login")
    public void unsuccessfulSignUp(String invalidUsername, String invalidPassword, String error) {
        loginPage.open();
        BasePage.waitForPageLoaded();
        assertTrue(loginPage.isOpened(), "Login page wasn't opened!");
        loginPage.signUpForNegative(invalidUsername, invalidPassword);
        assertEquals(loginPage.getErrorMessage(), error, "There is no error message after missing password or error message has been changed!");
    }

    @Test(description = "is 'Remember me' checkbox displayed at the Login page",
            groups = "unsuccessful login")
    public void checkBoxRememberMe() {
        loginPage.open();
        BasePage.waitForPageLoaded();
        assertTrue(loginPage.isOpened(), "Login page hadn't opened!");
        assertTrue(loginPage.isCheckboxRememberMeAppeared(), "There is no checkbox 'Remember me' at the login page!");
    }

    @Test(description = " is 'Remember me' checkbox unchecked at the Login page",
            groups = "unsuccessful login")
    public void uncheckedRememberMeCheckbox() {
        loginPage.open();
        BasePage.waitForPageLoaded();
        assertTrue(loginPage.isOpened(), "Login page hadn't opened!");
        assertFalse(loginPage.isCheckboxRememberMeUnchecked(), "The 'Remember me' checkbox has already checked!");
    }

    @Test(description = "is 'Forgot Your Password' link displayed at the Login page",
            groups = "unsuccessful login")
    public void forgotPasswordLink() {
        loginPage.open();
        BasePage.waitForPageLoaded();
        assertTrue(loginPage.isOpened(), "Login page hadn't opened!");
        assertTrue(loginPage.isForgotPasswordLinkAppeared(), "There is no 'forgot password' link at the Login page!");
    }

    @Test(description = "redirection to the forgot password page",
            groups = "unsuccessful login")
    public void redirectionForgotPage() {
        loginPage.open();
        BasePage.waitForPageLoaded();
        assertTrue(loginPage.isOpened(), "Login page hadn't opened!");
        loginPage.clickForgotPasswordLink();
        assertTrue(forgotYourPasswordPage.isOpened(), "The 'Forgot password' page hadn't opened!");
    }
}

