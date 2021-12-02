package uk.ac.cardiff.team5.graphium.api.controllers.organisation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.OrganisationEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.OrganisationRepository;
import uk.ac.cardiff.team5.graphium.exception.OrganisationNotFoundException;
import uk.ac.cardiff.team5.graphium.service.OrganisationService;
import uk.ac.cardiff.team5.graphium.service.dto.OrganisationDTO;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("api/organisation")
public class OrganisationAPIController {
    private final OrganisationRepository repository;
    private final OrganisationService organisationService;


    OrganisationAPIController(OrganisationRepository organisationRepository, OrganisationService organisationService) {
        this.repository = organisationRepository;
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

    @PostMapping("")
    String add(@RequestBody Map<String, String> body) {
        OrganisationDTO organisationDTO = new OrganisationDTO(body.get("name"));
        organisationService.addOrganisation(organisationDTO);

        return "Success";
    }
}
