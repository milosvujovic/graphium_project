package uk.ac.cardiff.team5.graphium.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.OrganisationEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.UserEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
    List<UserEntity> findAll();

    List<UserEntity> findUserEntitiesByOrganisation(OrganisationEntity organisation);
    @Modifying
    @Query("update UserEntity set organisation_approved = true where username = ?1")
    @Transactional
    void verifyUser(String userName);
}
