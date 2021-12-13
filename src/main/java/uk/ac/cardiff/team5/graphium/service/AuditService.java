package uk.ac.cardiff.team5.graphium.service;

import org.springframework.stereotype.Service;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.AuditEntity;

import java.util.List;

@Service
public interface AuditService {
    AuditEntity addAudit(AuditEntity audit);

//    AuditEntity retrieveAudit(String file_id);

    List<AuditEntity> getAudits(String username);

    List<AuditEntity> getAuditByFileId(String fileId);

}
