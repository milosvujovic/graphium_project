package uk.ac.cardiff.team5.graphium.files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.DBFile;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.UserEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.UserRepository;
import uk.ac.cardiff.team5.graphium.service.dto.FileDTO;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

@Service
public class FileServerDBImpl implements FileServer{

    private UserRepository userRepository;
    private DBFileStore dbFileStore;

    public FileServerDBImpl(DBFileStore aDbFileStore, UserRepository aUserRepository){
        userRepository = aUserRepository;
        dbFileStore = aDbFileStore;
    }

    @Override
    public String saveFiles(FileDTO aFile, String username) {
        String fileId = null;
//        Converts FileDTO to a DBFile
        DBFile dbFile = new DBFile(null,aFile.getFileName(),aFile.getType() ,aFile.getTag(),aFile.getAccessLevel() ,aFile.getComment(),aFile.getData(), aFile.getDate());

        UserEntity user =  userRepository.findByUsername(username);

//        Adds file to the user
        user.addFile(dbFile);
//        Updates user in the database
        userRepository.save(user);
//      Returns filesId
        fileId = dbFile.getFileId();
        return fileId;
    }
}
