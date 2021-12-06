package uk.ac.cardiff.team5.graphium.data.jpa.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Entity
@Table(name="insights")
public class AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long insight_id;

    @Column
    private LocalDate date;

    @Column
    private String user_id;

    @Column
    private String file_id;

    @Column
    private Long organisation_id;

    public AuditEntity(LocalDate date, String user_id, String file_id, Long organisation_id){
        this.date = date;
        this.user_id = user_id;
        this.file_id = file_id;
        this.organisation_id = organisation_id;
    }


}
