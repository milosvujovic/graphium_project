package uk.ac.cardiff.team5.graphium.api.controllers.organisation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.OrganisationEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.OrganisationRepository;
import uk.ac.cardiff.team5.graphium.exception.OrganisationNotFoundException;

import java.util.List;

@RestController()
@RequestMapping("api/organisation")
public class OrganisationAPIController {
    private final OrganisationRepository repository;

    OrganisationAPIController(OrganisationRepository organisationRepository) {
        this.repository = organisationRepository;
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
}
