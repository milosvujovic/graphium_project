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

    @Column
    private String file_id;

    @Column
    private Long organisation_id;

    @Column
    private String action;

    public AuditEntity(String date, String username, String file_id, Long organisation_id, String action){
        this.date = date;
        this.username = username;
        this.file_id = file_id;
        this.organisation_id = organisation_id;
        this.action = action;
    }

    public String getDate(){
        return this.date;
    }


}
