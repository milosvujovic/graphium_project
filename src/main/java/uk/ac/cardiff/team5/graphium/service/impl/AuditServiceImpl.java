package uk.ac.cardiff.team5.graphium.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.AuditEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.AuditRepository;
import uk.ac.cardiff.team5.graphium.service.AuditService;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("auditService")
@Component
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Override
    @Transactional
    public AuditEntity addAudit(AuditEntity audit){

        // Call repo method

        auditRepository.save(audit);


        return audit;
    }

//    public AuditEntity retrieveAudit(String file_id){
//
//        return auditRepository.findAuditEntityByFile_id(file_id);
//    }

    public AuditEntity retrieveAuditByUsername(String username){
        return auditRepository.findAllByUsername(username);
    }

}
