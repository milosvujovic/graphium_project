package uk.ac.cardiff.team5.graphium.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.OrganisationEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.OrganisationRepository;
import uk.ac.cardiff.team5.graphium.service.OrganisationService;
import uk.ac.cardiff.team5.graphium.service.dto.OrganisationDTO;

@Service("organisationService")
public class OrganisationServiceImpl implements OrganisationService {
    @Autowired
    private OrganisationRepository organisationRepository;

    public void addOrganisation(OrganisationDTO organisationDTO) {
        OrganisationEntity organisationEntity = new OrganisationEntity(
                organisationDTO.getOrganisationName()
        );

        organisationRepository.save(organisationEntity);
    }
}
