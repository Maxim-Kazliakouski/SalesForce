package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import tests.wrappers.ButtonAtCreateUserPage;
import tests.wrappers.Dropdown;
import tests.wrappers.Input;
import tests.wrappers.Textarea;
import java.io.IOException;

public class CreateContactPage extends BasePage {
    private final By TITLE_CREATE_CONTACT_PAGE = By.xpath("//h2[text()='New Contact']");
    private final By TOAST_MESSAGE = By.xpath("//span[@class='toastMessage slds-text-heading--small forceActionsText']");
    public static By CREATED_CONTACT_NAME = By.xpath("//span[@class='custom-truncate uiOutputText']");

    public CreateContactPage(WebDriver browser) {
        super(browser);
    }

    public void createContact(String firstName, String lastName, String phone, String salutationOption, String leadSourceOption, String text) throws IOException, InterruptedException {
        new Input(browser, "First Name").write(firstName);
        new Input(browser, "Last Name").write(lastName);
        new Input(browser, "Phone").write(phone);
        new Dropdown(browser, "Salutation").select(salutationOption);
        new Dropdown(browser, "Lead Source").select(leadSourceOption);
        new Textarea(browser, "Mailing Street").input(text);
        new ButtonAtCreateUserPage(browser,"Save").click();
    }

    public void open() {
        String CREATE_USER_URL = "https://oxagile-dev-ed.lightning.force.com/lightning/o/Contact/new?count=1";
        browser.get(CREATE_USER_URL);
    }

    public boolean isOpened() {
        return waitForVisibility(TITLE_CREATE_CONTACT_PAGE);
    }

    public boolean isToastMessageAppeared(){
        return waitForVisibility(TOAST_MESSAGE);
    }

    public String getToastMessageText(){
        System.out.println(browser.findElement(TOAST_MESSAGE).getText());
        return browser.findElement(TOAST_MESSAGE).getText();
    }

    public static boolean isContactNameAppeared(){
        return waitForVisibility(CREATED_CONTACT_NAME);
    }
}






