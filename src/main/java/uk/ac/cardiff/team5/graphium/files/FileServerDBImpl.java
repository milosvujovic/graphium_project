package uk.ac.cardiff.team5.graphium.files;

import org.springframework.stereotype.Service;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.DBFile;
import uk.ac.cardiff.team5.graphium.service.dto.FileDTO;

import java.io.IOException;
import java.util.List;

@Service
public class FileServerDBImpl implements FileServer{

    private DBFileStore dbFileStore;

    public FileServerDBImpl(DBFileStore aDbFileStore){
        dbFileStore = aDbFileStore;
    }

    @Override
    public String saveFiles(FileDTO aFile) {


        String fileId = null;
        try {
            System.out.println(aFile.getData().getContentType());
            DBFile dbFile = new DBFile(aFile.getFileID(),aFile.getFileName(),aFile.getData().getContentType() ,aFile.getTag(),aFile.getAccessLevel() ,aFile.getComment(),aFile.getData().getBytes());
            dbFile = dbFileStore.save(dbFile);
            fileId = dbFile.getFileId();

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
