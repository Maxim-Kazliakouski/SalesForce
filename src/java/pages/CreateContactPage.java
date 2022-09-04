package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import tests.wrappers.Dropdown;
import tests.wrappers.Input;

import java.io.IOException;

public class CreateContactPage extends BasePage {
    private final By TITLE_CREATE_CONTACT_PAGE = By.xpath("//h2[text()='New Contact']");

    public CreateContactPage(WebDriver browser) {
        super(browser);
    }

    public void createContact(String firstName, String lastName, String phone, String salutationOption, String leadSourceOption) throws IOException, InterruptedException {
        new Input(browser, "First Name").write(firstName);
        new Input(browser, "Last Name").write(lastName);
        new Input(browser, "Phone").write(phone);
        new Dropdown(browser, "Salutation").select(salutationOption);
        new Dropdown(browser, "Lead Source").select(leadSourceOption);
    }

    public void open() {
        String CREATE_USER_URL = "https://oxagile-dev-ed.lightning.force.com/lightning/o/Contact/new?count=1";
        browser.get(CREATE_USER_URL);
    }

    public boolean isOpened() {
        return waitForVisibility(TITLE_CREATE_CONTACT_PAGE);
    }
}
