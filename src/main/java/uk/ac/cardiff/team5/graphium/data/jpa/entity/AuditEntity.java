package uk.ac.cardiff.team5.graphium.data.jpa.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name="insights")
public class AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long insight_id;

    @Column
    private String date;

    @Column
    private String username;

    @Column(name = "file_id")
    private String fileId;

    @Column
    private Long organisation_id;

    @Column
    private String action;

    @Column
    private String owner;

    public AuditEntity(String date, String username, String file_id, Long organisation_id, String action, String owner){
        this.date = date;
        this.username = username;
        this.fileId = file_id;
        this.organisation_id = organisation_id;
        this.action = action;
        this.owner = owner;
    }

    public String getDate(){
        return this.date;
    }
    public Long getInsightId(){ return this.insight_id;}
    public String getUsername(){ return this.username;}
    public String getFileId(){ return this.fileId;}
    public Long getOrganisationId(){ return this.organisation_id;}
    public String getAction(){ return this.action;}
    public String getOwner(){ return this.owner;}
}
