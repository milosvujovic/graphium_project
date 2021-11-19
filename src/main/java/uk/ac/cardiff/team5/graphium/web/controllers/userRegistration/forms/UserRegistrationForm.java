package uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationForm {
    @Size(min = 1, max = 16, message = "First Name must be between 1 and 16 characters.")
    private String firstName;

    @Size(min = 1, max = 16, message = "Last Name must be between 1 and 16 characters.")
    private String lastName;

    @NotEmpty
    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="Must be positive integer.")
    private String organisationId;

    @NotEmpty
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message = "Email address invalid.")
    private String email;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordConf;

    public UserRegistrationForm(UserDTO userDTO) {
        firstName = userDTO.getFirstName();
        lastName = userDTO.getLastName();
        email = userDTO.getEmail();
        password = userDTO.getPassword();
    }
}
