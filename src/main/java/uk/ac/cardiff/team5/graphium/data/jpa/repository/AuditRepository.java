package uk.ac.cardiff.team5.graphium.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.AuditEntity;

import java.util.List;

@Repository("auditRepository")
public interface AuditRepository extends JpaRepository<AuditEntity, Long> {

    List<AuditEntity> findAllByUsername(String username);

    List<AuditEntity> findAuditEntityByFileId(String fileId);

}
