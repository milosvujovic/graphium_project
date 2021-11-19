package uk.ac.cardiff.team5.graphium.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String firstName;
    String lastName;
    Long organisationId;
    String username;
    String email;
    String password;
    Boolean organisationApproved;
    Boolean emailVerified;
}
