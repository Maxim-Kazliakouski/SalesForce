package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;

@AllArgsConstructor
@Data
@Builder

public class Contact {
    String firstName;
    String lastName;
    String phone;
    String address;
    String leadSourceOption;
    String salutation;
}
