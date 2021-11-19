package uk.ac.cardiff.team5.graphium.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.OrganisationEntity;

import java.util.List;

@Repository
public interface OrganisationRepository extends JpaRepository<OrganisationEntity, Long> {
    List<OrganisationEntity> findAll();
}
