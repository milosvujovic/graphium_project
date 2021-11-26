package uk.ac.cardiff.team5.graphium.data.jpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "partnerships")
public class PartnershipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String partnershipID;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sharing_organisation_id")
    OrganisationEntity sharingOrganisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viewing_organisation_id")
    OrganisationEntity viewingOrganisation;


    public PartnershipEntity(OrganisationEntity aSharingOrganisation, OrganisationEntity aViewingOrganisation) {

        this.sharingOrganisation = aSharingOrganisation;
        this.viewingOrganisation = aViewingOrganisation;
    }
}
