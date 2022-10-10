package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import dto.Contact;
import tests.wrappers.Dropdown;
import tests.wrappers.Input;
import tests.wrappers.Textarea;

public class CreateContactPage extends BasePage {

    @FindBy(xpath = "//h2[text()='New Contact']")
    WebElement titleCreateContactPage;
    @FindBy(xpath = "//span[@class='toastMessage slds-text-heading--small forceActionsText']")
    WebElement toastMessage;
    @FindBy(xpath = "//span[@class='custom-truncate uiOutputText']")
    static WebElement createdContactPage;
    @FindBy(xpath = "//button[text()='Save']")
    WebElement saveButton;

    public CreateContactPage(WebDriver browser) {
        super(browser);
    }

    @Step("Create contact with params:")
    public void createContact(Contact contact) {
        new Input(browser, "First Name").write(contact.getFirstName());
        new Input(browser, "Last Name").write(contact.getLastName());
        new Input(browser, "Phone").write(contact.getPhone());
        new Dropdown(browser, "Salutation").select(contact.getSalutation());
        new Dropdown(browser, "Lead Source").select(contact.getLeadSourceOption());
        new Textarea(browser, "Mailing Street").input(contact.getAddress());
        saveButton.click();
    }

    public void open() {
        String CREATE_USER_URL = "https://oxagile-dev-ed.lightning.force.com/lightning/o/Contact/new?count=1";
        browser.get(CREATE_USER_URL);
    }

    public boolean isOpened() {
        return waitForVisibility(titleCreateContactPage);
    }

    public boolean isToastMessageAppeared() {
        return waitForVisibility(toastMessage);
    }

    @Step("Getting toast message")
    public String getToastMessageText() {
        System.out.println(toastMessage.getText());
        return toastMessage.getText();
    }

    public static boolean isContactNameAppeared() {
        return waitForVisibility(createdContactPage);
    }
}