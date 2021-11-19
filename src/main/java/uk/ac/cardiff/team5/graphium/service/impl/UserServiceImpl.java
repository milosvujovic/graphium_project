package uk.ac.cardiff.team5.graphium.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.cardiff.team5.graphium.GraphiumApplication;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.UserEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.OrganisationRepository;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.UserRepository;
import uk.ac.cardiff.team5.graphium.exception.EmailInUseException;
import uk.ac.cardiff.team5.graphium.exception.UsernameInUseException;
import uk.ac.cardiff.team5.graphium.service.UserService;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    PasswordEncoder passwordEncoder = GraphiumApplication.encoder();

    @Override
    public void register(UserDTO userDTO) throws EmailInUseException, UsernameInUseException {
        if (checkEmailInUse(userDTO.getEmail())) {
            throw new EmailInUseException("Email " + userDTO.getEmail() + " is already in use.");
        }

        if (checkUsernameInUse(userDTO.getUsername())) {
            throw new UsernameInUseException("Username " + userDTO.getUsername() + " is already in use.");
        }

        UserEntity userEntity = new UserEntity(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                organisationRepository.findById(userDTO.getOrganisationId()).get(),// need to add verification that organisation id exists
                userDTO.getUsername(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword())
        );

        userRepository.save(userEntity);
    }

    @Override
    public boolean checkEmailInUse(String email) {
        return userRepository.findByEmail(email) !=null ? true : false;
    }

    @Override
    public boolean checkUsernameInUse(String username) {
        return userRepository.findByUsername(username) != null ? true : false;
    }

    @Override
    public boolean checkPasswordsMatch(String password1, String password2) {
        return password1.equals(password2);
    }

}
