package uk.ac.cardiff.team5.graphium.api.controllers.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.UserEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.UserRepository;
import uk.ac.cardiff.team5.graphium.domain.User;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("api/user")
public class UserAPIController {
    private final UserRepository repository;

    UserAPIController(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @GetMapping()
    List<UserDTO> all() {
        return repository.findAll().stream()
                .map(userEntity -> new UserDTO(
                        userEntity.getFirstName(),
                        userEntity.getLastName(),
                        (userEntity.getOrganisation() != null) ? userEntity.getOrganisation().getOrganisationId() : -1,
                        userEntity.getUsername(),
                        userEntity.getEmail(),
                        userEntity.getPassword()
                )).collect(Collectors.toList());
    }
}
