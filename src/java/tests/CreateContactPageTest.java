package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import tests.base.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CreateContactPageTest extends BaseTest {

    @Parameters({"username", "password"})
    @Test(description = "creating user")
    public void createNewUser() {
        String firstName = faker.name().firstName();
        String salutationOption = "Mr.";
        String lastName = faker.name().lastName();
        homePage.open();
        assertTrue(homePage.isOpened(), "Home page wasn't open");
        createContactPage.open();
        assertTrue(createContactPage.isOpened(), "Create new user page wasn't open");
        createContactPage.createContact(firstName, lastName, "1234567",
                salutationOption, "Web", "Minsk, pr.Nezavisimosti");
        assertTrue(createContactPage.isToastMessageAppeared(),
                "The confirmation message about success created user wasn't appeared!");
        assertEquals(createContactPage.getToastMessageText(),
                "Contact \"" + salutationOption + " " + firstName + " " + lastName + "\" has been created.");
        postConditionDeleteContact();
    }
}
