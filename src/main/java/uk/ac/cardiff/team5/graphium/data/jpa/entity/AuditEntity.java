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

    public AuditEntity(String date, String username, String file_id, Long organisation_id){
        this.date = date;
        this.username = username;
        this.file_id = file_id;
        this.organisation_id = organisation_id;
    }

    public String getDate(){
        return this.date;
    }


}
