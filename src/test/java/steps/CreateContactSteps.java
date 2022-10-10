package steps;

import dto.Contact;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.CreateContactPage;

import static java.lang.String.format;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Log4j2
public class CreateContactSteps {
    CreateContactPage createContactPage;

    public CreateContactSteps(WebDriver browser) {
        createContactPage = new CreateContactPage(browser);
    }

    @Step("Go to the create contact page")
    public CreateContactSteps goToCreateContactPage() {
        createContactPage.open();
        assertTrue(createContactPage.isOpened(), "Create new user page wasn't open");
        return this;
    }

    public void createNewContact(Contact contact) {
        createContactPage.createContact(contact);
        assertTrue(createContactPage.isToastMessageAppeared(),
                "The confirmation message about success created user wasn't appeared!");
        log.info(format("New contact '%s%s%s' has been created", contact.getSalutation(), contact.getFirstName(), contact.getLastName()));
        assertEquals(createContactPage.getToastMessageText(),
                "Contact \"" + contact.getSalutation() + " " + contact.getFirstName() + " " + contact.getLastName() + "\" was created.");
    }
}