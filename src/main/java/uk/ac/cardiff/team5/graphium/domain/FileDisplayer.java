package uk.ac.cardiff.team5.graphium.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileDisplayer {
    private String fileId;
    private String fileName;
    private String fileType;
    private String tag;
    private String accessLevel;
    private String comment;
    private byte[] data;
    private String date;
    private String username;
}
