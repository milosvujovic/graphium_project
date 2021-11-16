package uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;
import uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.UserRegistration;

//import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationForm {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordConf;

    public UserRegistrationForm(UserDTO userDTO) {
        firstName = userDTO.getFirstName();
        lastName = userDTO.getLastName();
        email = userDTO.getEmail();
        password = userDTO.getPassword();
        passwordConf = userDTO.getPasswordConf();
    }
}
