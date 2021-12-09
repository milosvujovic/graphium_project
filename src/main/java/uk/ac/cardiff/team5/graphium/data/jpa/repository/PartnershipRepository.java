package uk.ac.cardiff.team5.graphium.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.PartnershipEntity;


@Repository
public interface PartnershipRepository extends JpaRepository<PartnershipEntity, Long> {
    PartnershipEntity save(PartnershipEntity aPartnership);
}

