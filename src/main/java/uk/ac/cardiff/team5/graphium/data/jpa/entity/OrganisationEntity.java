package uk.ac.cardiff.team5.graphium.data.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "organisation")
public class OrganisationEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long organisationId;
    private String organisationName;

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }
}
