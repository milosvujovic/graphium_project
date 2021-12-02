package uk.ac.cardiff.team5.graphium.data.jpa.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "organisation")
public class OrganisationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organisation_id;
    private String organisationName;

    public OrganisationEntity(String organisationName) {
        this.organisationName = organisationName;
    }

    public Long getOrganisationId() {
        return organisation_id;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisation_id = organisationId;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }
}
