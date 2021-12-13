package uk.ac.cardiff.team5.graphium.service.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import uk.ac.cardiff.team5.graphium.data.jpa.entity.OrganisationEntity;

@Value
@AllArgsConstructor
public class OrganisationDTO {
    private Long organisation_id;
    private String organisationName;


    public OrganisationDTO(OrganisationEntity organisation) {
        this.organisation_id = organisation.getOrganisationId();
        this.organisationName = organisation.getOrganisationName();
    }
}
