package factories;

import com.github.javafaker.Faker;
import dto.Contact;

public class ContactFactory {

    public static Contact get() {
        Faker faker = new Faker();
        return Contact.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .phone(faker.phoneNumber().cellPhone())
                .address(faker.address().fullAddress())
                .leadSourceOption("Web")
                .salutation("Mr.")
                .build();
    }
}
