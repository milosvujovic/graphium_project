package uk.ac.cardiff.team5.graphium.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.OrganisationEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganisationRepository extends JpaRepository<OrganisationEntity, Long> {
    List<OrganisationEntity> findAll();
    OrganisationEntity findByOrganisationId(Long name);

    @Query(value = "CALL getPossiblePartnerships(:orgID);", nativeQuery = true)
    List<OrganisationEntity> findPossiblePartners(@Param("orgID") Long name);
}
