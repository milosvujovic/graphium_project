package uk.ac.cardiff.team5.graphium.files;

import uk.ac.cardiff.team5.graphium.data.jpa.entity.DBFile;
import uk.ac.cardiff.team5.graphium.service.dto.FileDTO;

import java.time.LocalDate;

public interface FileServer {
    String saveFiles(FileDTO aFile, String username);

    void modifyFiles(byte[] bytes, String fileId, LocalDate today, String contentType);
}
