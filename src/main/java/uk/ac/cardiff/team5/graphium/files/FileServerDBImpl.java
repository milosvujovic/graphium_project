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
    public String saveFiles(FileDTO aFile) {
        System.out.println("Saving the file");

        String fileId = null;
        try {
            DBFile dbFile = new DBFile(null,aFile.getFileName(),aFile.getData().getContentType() ,aFile.getTag(),aFile.getAccessLevel() ,aFile.getComment(),aFile.getData().getBytes(), aFile.getDate());
            //           Gather the user here
//            Basic one at the moment
            UserEntity user =  userRepository.findByUsername("user");
            user.addFile(dbFile);
            userRepository.save(user);
            fileId = dbFile.getFileId();
            System.out.println(fileId);

        } catch (IOException e) {
            e.printStackTrace();//TODO replace with own exception
        }
        return fileId;
    }

    @Override
    public List<DBFile> getFiles() {
        return dbFileStore.findAll();
    }
}
