package uk.ac.cardiff.team5.graphium.service;

import org.springframework.stereotype.Service;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.AuditEntity;

@Service
public interface AuditService {
    AuditEntity addAudit(AuditEntity audit);

//    AuditEntity retrieveAudit(String file_id);

    AuditEntity retrieveAuditByUsername(String username);
}
