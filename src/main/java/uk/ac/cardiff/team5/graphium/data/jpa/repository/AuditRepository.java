package uk.ac.cardiff.team5.graphium.data.jpa.repository;

import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.AuditEntity;

@Repository("auditRepository")
public interface AuditRepository extends JpaRepository<AuditEntity, Long> {
    AuditEntity findByFileId(String fileId);

}
