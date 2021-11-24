package uk.ac.cardiff.team5.graphium.service.dto;
import lombok.Value;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.GeneratedValue;
import javax.persistence.Lob;

@Value
public class FileDTO {
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String fileID;
    private String fileName;
    private String tag;
    private String accessLevel;
    private String comment;
    @Lob
    private MultipartFile data;
    private String date;

    public FileDTO(String id, String logoFileName, String tag, String accessLevel, String comment, MultipartFile logoFile, String date) {
        this.fileID = id;
        this.fileName = logoFileName;
        this.tag = tag;
        this.accessLevel = accessLevel;
        this.comment = comment;
        this.data = logoFile;
        this.date = date;
    }

}