package uk.ac.cardiff.team5.graphium.service.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class UserDTO {
    String firstName;
    String lastName;
    String username;
    String email;
    String password;
    Boolean isAdmin;
    Boolean authenticated;

    public UserDTO(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = false;
        this.authenticated = false;
    }
}
