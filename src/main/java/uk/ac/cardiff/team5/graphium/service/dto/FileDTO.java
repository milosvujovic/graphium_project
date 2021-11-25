package uk.ac.cardiff.team5.graphium.service.dto;
import lombok.Value;
import org.hibernate.annotations.GenericGenerator;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.DBFile;

import javax.persistence.GeneratedValue;
import javax.persistence.Lob;

@Value
public class FileDTO {
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String fileID;
    private String fileName;
    private String type;
    private String tag;
    private String accessLevel;
    private String comment;
    @Lob
    private byte[] data;
    private String date;

    public FileDTO(String id, String logoFileName,String type ,String tag, String accessLevel, String comment, byte[] logoFile, String date) {
        this.fileID = id;
        this.fileName = logoFileName;
        this.type = type;
        this.tag = tag;
        this.accessLevel = accessLevel;
        this.comment = comment;
        this.data = logoFile;
        this.date = date;
    }
//    Converts a DBFile to a FileDTO
    public FileDTO(DBFile file){
        this.fileID = file.getFileId();
        this.fileName = file.getFileName();
        this.type = file.getFileType();
        this.tag = file.getTag();
        this.accessLevel = file.getAccessLevel();
        this.comment = file.getComment();
        this.data = file.getData();
        this.date = file.getDate();
    }

}