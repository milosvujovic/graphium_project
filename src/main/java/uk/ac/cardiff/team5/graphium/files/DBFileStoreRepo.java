package uk.ac.cardiff.team5.graphium.files;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.DBFile;

public interface DBFileStoreRepo extends JpaRepository<DBFile, String>, DBFileStore {
    DBFile getById(String s);
}
