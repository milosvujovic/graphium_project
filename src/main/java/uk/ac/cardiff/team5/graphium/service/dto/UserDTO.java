package uk.ac.cardiff.team5.graphium.service.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordConf;
    private Boolean isAdmin;
    private Boolean authenticated;
}
