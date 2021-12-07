package uk.ac.cardiff.team5.graphium.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.cardiff.team5.graphium.GraphiumApplication;
import uk.ac.cardiff.team5.graphium.data.jdbc.repository.FileRepository;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.OrganisationEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.RoleEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.UserEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.OrganisationRepository;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.RoleRepository;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.UserRepository;
import uk.ac.cardiff.team5.graphium.domain.FileDisplayer;
import uk.ac.cardiff.team5.graphium.exception.EmailInUseException;
import uk.ac.cardiff.team5.graphium.exception.UsernameInUseException;
import uk.ac.cardiff.team5.graphium.service.UserService;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FileRepository fileRepository;
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
        RoleEntity role = roleRepository.findByRoleId("1");
        UserEntity userEntity = new UserEntity(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                organisationRepository.findById(userDTO.getOrganisationId()).get(),// need to add verification that organisation id exists
                userDTO.getUsername(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()),
                role
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

    @Override
//    Reads user from the database and then converts it to a UserDTO
    public UserDTO getUser(String username) {
        UserEntity user = userRepository.findByUsername(username);
        UserDTO userDTO = new UserDTO(user);
        return userDTO;


    }

    @Override
    public List<UserDTO> getOrgAdmin(String organisationID) {
        Long orgIDLong = Long.parseLong(organisationID);
        OrganisationEntity organisation = organisationRepository.findById(orgIDLong).get();
        return userRepository.findUserEntitiesByOrganisation(organisation)
                .stream()
                .filter(c -> Objects.equals(c.getRole(), "2"))
                .map(c -> new UserDTO(c))
                .collect(Collectors.toList());
    }

    public List<FileDisplayer> getsUsersFiles(String username){
        UserEntity user = userRepository.findByUsername(username);
        UserDTO userDTO = new UserDTO(user);
        return userDTO.getFiles()
                .stream()
                .map(c -> new FileDisplayer(c.getFileID(),c.getFileName(),c.getType(),c.getTag(),c.getAccessLevel(),c.getComment(),c.getDate(),userDTO.getUsername(),c.getSubject(),user.getOrganisation().getOrganisationName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FileDisplayer> getFilesForOrg(String username) {
        return fileRepository.findAllByOrg(username);
    }

    @Override
    public List<FileDisplayer> getPublicFiles() {
        return fileRepository.findAllPublic();
    }

    @Override
    public List<FileDisplayer> getPartnersFiles(String username) {
        return fileRepository.findAllPartners(username);
    }

    @Override
    public List<FileDisplayer> getAllFiles(String username) {
        return fileRepository.findAllFiles(username);
    }

    @Override    public List<FileDisplayer> findBySearchTerm(String search, String username) {
        return fileRepository.findAllFiles(username).stream().filter(c -> c.matches(search)).collect(Collectors.toList());
    }
}
