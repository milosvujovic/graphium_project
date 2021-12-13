package uk.ac.cardiff.team5.graphium.api.controllers.organisation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.OrganisationEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.RoleEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.UserEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.OrganisationRepository;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.RoleRepository;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.UserRepository;
import uk.ac.cardiff.team5.graphium.exception.OrganisationNotFoundException;
import uk.ac.cardiff.team5.graphium.service.OrganisationService;
import uk.ac.cardiff.team5.graphium.service.dto.OrganisationDTO;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;

import javax.management.relation.Role;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("api/organisation")
public class OrganisationAPIController {
    private final OrganisationRepository repository;
    private final UserRepository userRepository;
    private final OrganisationService organisationService;
    private final RoleRepository roleRepository;


    OrganisationAPIController(OrganisationRepository organisationRepository, UserRepository userRepository, RoleRepository roleRepository, OrganisationService organisationService) {
        this.repository = organisationRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.organisationService = organisationService;
    }

    @GetMapping("")
    List<OrganisationEntity> all() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    OrganisationEntity one(@PathVariable Long id) throws OrganisationNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new OrganisationNotFoundException(id) );
    }

    @GetMapping("{id}/users")
    List<UserDTO> all_users(@PathVariable Long id) {
        OrganisationEntity organisationEntity = repository.getById(id);


        return userRepository.findUserEntitiesByOrganisation(organisationEntity).stream()
                .map(userEntity -> new UserDTO(
                        userEntity.getFirstName(),
                        userEntity.getLastName(),
                        (userEntity.getOrganisation() != null) ? userEntity.getOrganisation().getOrganisationId() : -1,
                        userEntity.getUsername(),
                        userEntity.getEmail(),
                        userEntity.getPassword()
                )).collect(Collectors.toList());
    }

    @PostMapping("")
    String add(@RequestBody Map<String, String> body) {
        OrganisationDTO organisationDTO = new OrganisationDTO(body.get("name"));
        organisationService.addOrganisation(organisationDTO);

        return "Success";
    }

    @PostMapping("{organisation_id}/set-admin/{username}")
    String set_admin(@PathVariable Long organisation_id, @PathVariable String username) {
        UserEntity userEntity = userRepository.getByUsername(username);
        userEntity.setRole(roleRepository.getById("2"));
        userRepository.save(userEntity);
        return "Success";
    }
}
