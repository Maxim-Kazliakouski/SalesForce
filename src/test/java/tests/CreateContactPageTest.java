package tests;

import groovy.util.logging.Log4j2;
import org.testng.annotations.Test;
import tests.base.BaseWithStepsTest;

@Log4j2
public class CreateContactPageTest extends BaseWithStepsTest {

    @Test(description = "creating user")
    public void createNewUser() {
        loginSteps
                .open()
                .login(getUsername(), getPassword());
        createContactSteps
                .goToCreateContactPage()
                .createNewContact();
        deleteCreatedContact();
    }
}