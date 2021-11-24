package uk.ac.cardiff.team5.graphium.files;

import uk.ac.cardiff.team5.graphium.data.jpa.entity.DBFile;

import java.util.List;
import java.util.Optional;

public interface DBFileStore {
    DBFile save(DBFile file);
    Optional<DBFile> findById(String id);
    List<DBFile> findAll();
}
