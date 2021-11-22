package uk.ac.cardiff.team5.graphium.service;

import uk.ac.cardiff.team5.graphium.data.jpa.entity.UserEntity;
import uk.ac.cardiff.team5.graphium.exception.EmailInUseException;
import uk.ac.cardiff.team5.graphium.exception.UsernameInUseException;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;

public interface UserService {
    void register(UserDTO userDTO) throws EmailInUseException, UsernameInUseException;
    boolean checkEmailInUse(String email);
    boolean checkUsernameInUse(String username);
    boolean checkPasswordsMatch(String password1, String password2);
}