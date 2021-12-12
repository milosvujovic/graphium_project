package uk.ac.cardiff.team5.graphium.service.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.DBFile;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.UserEntity;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Value
@AllArgsConstructor
public class UserDTO {
    String firstName;
    String lastName;
    Long organisationId;
    String username;
    String email;
    String password;
    Boolean organisationApproved;
    Boolean emailVerified;
    List<FileDTO> files;

    public UserDTO(String firstName, String lastName, Long organisationId, String username, String email, String password, List<FileDTO> files) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.organisationId = organisationId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.organisationApproved = false;
        this.emailVerified = false;
        this.files = files;
    }
    public UserDTO(String firstName, String lastName, Long organisationId, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.organisationId = organisationId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.organisationApproved = false;
        this.emailVerified = false;
        this.files = new ArrayList<>();
    }
//    Converts a UserEntity to a UserDTO
    public UserDTO(UserEntity user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
//        Check about the organisation
        this.organisationId = user.getOrganisation().getOrganisationId();
        this.username = user.getUsername();
        TextEncryptor encryptor =
                Encryptors.delux("password", new String(Hex.encode("salt".getBytes(StandardCharsets.UTF_8))));
        this.email = encryptor.decrypt(user.getEmail());
        this.password = user.getPassword();
        this.organisationApproved = user.getOrganisation_approved();
        this.emailVerified = user.getEmail_verified();
        this.files =  user.getFiles()
                .stream()
                .map(c -> new FileDTO(c))
                .collect(Collectors.toList());
    }
}
