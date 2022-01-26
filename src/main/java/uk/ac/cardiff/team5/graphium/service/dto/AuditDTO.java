package uk.ac.cardiff.team5.graphium.service.dto;

import lombok.Value;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.AuditEntity;

@Value
public class AuditDTO {
    Long insightId;
    String date;
    String username;
    String fileId;
    Long organisationId;
    String action;
    String owner;

    public AuditDTO(Long insightId,String date,String username,String fileId,Long organisationId,String action, String owner){
        this.insightId = insightId;
        this.date = date;
        this.username = username;
        this.fileId = fileId;
        this.organisationId = organisationId;
        this.action = action;
        this.owner = owner;
    }

    public AuditDTO(AuditEntity audit){
        this.insightId = audit.getInsightId();
        this.date = audit.getDate();
        this.username = audit.getUsername();
        this.fileId = audit.getFileId();
        this.organisationId = audit.getOrganisationId();
        this.action = audit.getAction();
        this.owner = audit.getOwner();
    }
}
