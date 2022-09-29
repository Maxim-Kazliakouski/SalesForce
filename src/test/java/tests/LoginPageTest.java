package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.base.BaseWithStepsTest;

public class LoginPageTest extends BaseWithStepsTest {

    @DataProvider()
    public Object[][] loginData() {
        return new Object[][]{
                {"max@oxa.sandbox", "", "Please enter your password."},
                {"user", "123", "Please check your username and password. If you still can't log in, contact your Salesforce administrator."}
        };
    }

//    @Test(description = "successful signUp with correct login data",
//            groups = "valid login")
//    public void successfulSignUp() {
//        loginSteps
//                .open()
//                .login(getUsername(), getPassword());
//    }
//
//    @Test(dataProvider = "loginData",
//            description = "Unsuccessful signUp with invalid data",
//            groups = "unsuccessful login")
//    public void unsuccessfulSignUp(String invalidUsername, String invalidPassword, String error) {
//        loginSteps
//                .open()
//                .loginWithInvalidData(invalidUsername, invalidPassword)
//                .getErrorMessage(error);
//    }
//
//    @Test(description = "Is 'Remember me' checkbox displayed at the Login page",
//            groups = "unsuccessful login")
//    public void checkBoxRememberMe() {
//        loginSteps
//                .open()
//                .isCheckboxDisplayed();
//    }
//
//    @Test(description = " is 'Remember me' checkbox unchecked at the Login page",
//            groups = "unsuccessful login")
//    public void uncheckedRememberMeCheckbox() {
//        loginSteps
//                .open()
//                .isCheckboxUnchecked();
//    }
//
//    @Test(description = "is 'Forgot Your Password' link displayed at the Login page",
//            groups = "unsuccessful login")
//    public void forgotPasswordLink() {
//        loginSteps
//                .open()
//                .isForgotPasswordLinkDisplayed();
//    }
//
    @Test(description = "redirection to the forgot password page",
            groups = "unsuccessful login")
    public void redirectionForgotPage() {
        loginSteps
                .open()
                .clickOnForgotPassword();
    }
}