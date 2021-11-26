package uk.ac.cardiff.team5.graphium.service;

import uk.ac.cardiff.team5.graphium.domain.FileDisplayer;
import uk.ac.cardiff.team5.graphium.exception.EmailInUseException;
import uk.ac.cardiff.team5.graphium.exception.UsernameInUseException;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;

import java.util.List;

public interface UserService {
    void register(UserDTO userDTO) throws EmailInUseException, UsernameInUseException;
    boolean checkEmailInUse(String email);
    boolean checkUsernameInUse(String username);
    boolean checkPasswordsMatch(String password1, String password2);
    UserDTO getUser(String username);
    List<UserDTO> getOrgAdmin(String organisationID);
    List<FileDisplayer> getsUsersFiles(String username);
    List<FileDisplayer> getFilesForOrg(String username);
    List<FileDisplayer> getPublicFiles();
}