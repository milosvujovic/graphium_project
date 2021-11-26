package uk.ac.cardiff.team5.graphium.files;

import uk.ac.cardiff.team5.graphium.data.jpa.entity.DBFile;
import uk.ac.cardiff.team5.graphium.service.dto.FileDTO;

import java.util.List;

public interface FileServer {
    String saveFiles(FileDTO aFile, String username);
}
