package steps;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.CreateContactPage;
import pages.ForgotYourPasswordPage;
import pages.HomePage;
import pages.LoginPage;
import static java.lang.String.format;


import static org.testng.Assert.*;
@Log4j2
public class LoginSteps {
    LoginPage loginPage;
    HomePage homePage;
    ForgotYourPasswordPage forgotYourPasswordPage;
    CreateContactPage createContactPage;

    public LoginSteps(WebDriver browser) {
        loginPage = new LoginPage(browser);
        homePage = new HomePage(browser);
        createContactPage = new CreateContactPage(browser);
        forgotYourPasswordPage = new ForgotYourPasswordPage(browser);
    }

    @Step("Open Login page")
    public LoginSteps open() {
        loginPage.open();
        log.info("Opening 'Login page'");
        assertTrue(loginPage.isOpened(), "The Login Page hadn't opened");
        return this;
    }

    @Step("Login")
    public void login(String username, String password) {
        log.info("Trying to login");
        loginPage.signUp(username, password);
        log.info("Redirection to the 'Home page'");
        homePage.open();
        assertTrue(homePage.isOpened(), "The Home Page hadn't opened");
    }

    @Step("Login with invalid data")
    public LoginSteps loginWithInvalidData(String invalidUsername, String invalidPassword) {
        log.info(format("Trying to login with invalid data: userName: '%s', password: '%s'", invalidUsername, invalidPassword));
        loginPage.signUp(invalidUsername, invalidPassword);
        return this;
    }

    @Step("Get error message")
    public void getErrorMessage(String error){
        assertEquals(loginPage.errorMessage(), error, "There is no error message after missing password or error message has been changed!");
    }

    @Step("Check that checkbox is displaying")
    public void isCheckboxDisplayed(){
        assertTrue(loginPage.isCheckboxRememberMeAppeared(), "There is no checkbox 'Remember me' at the login page!");
    }

    @Step("Check that checkbox 'Remember me' is unchecked")
    public void isCheckboxUnchecked(){
        assertFalse(loginPage.isCheckboxRememberMeUnchecked(), "The 'Remember me' checkbox has already checked!");
    }

    @Step("Check that 'Forgot Your Password' link is displayed")
    public void isForgotPasswordLinkDisplayed(){
        assertTrue(loginPage.isForgotPasswordLinkAppeared(), "There is no 'forgot password' link at the Login page!");
    }

    @Step("Click on 'Forgot Password' link")
    public void clickOnForgotPassword(){
        loginPage.clickForgotPasswordLink();
        assertTrue(forgotYourPasswordPage.isOpened(), "The 'Forgot password' page hadn't opened!");
    }
}
