package uk.ac.cardiff.team5.graphium.api.controllers.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.UserEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.UserRepository;
import uk.ac.cardiff.team5.graphium.domain.User;

import java.util.List;

@RestController()
@RequestMapping("api/user")
public class UserAPIController {
    private final UserRepository repository;

    UserAPIController(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @GetMapping()
    String all() {
        return repository.findAll().get(0).toString();
    }
}
