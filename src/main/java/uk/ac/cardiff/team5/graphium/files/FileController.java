package uk.ac.cardiff.team5.graphium.files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.AuditEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.DBFile;
import uk.ac.cardiff.team5.graphium.service.AuditService;
import uk.ac.cardiff.team5.graphium.service.dto.FileDTO;
import uk.ac.cardiff.team5.graphium.service.UserService;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;

import java.security.Principal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@RestController
public class FileController {

    @Value("classpath:static/files/basic.txt")
    Resource defaultFile;

    private DBFileStore dbFileStore;

    @Autowired
    private AuditService auditService;
    @Autowired
    private UserService userService;
    @Autowired
    public FileController(DBFileStore aDbFileStore) {
        dbFileStore = aDbFileStore;
    }

    @GetMapping("file/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, Principal principal) {

        // Load file from database
        Optional<DBFile> dbFile = dbFileStore.findById(fileId);

        if (dbFile.isPresent()) {

            DBFile theFile = dbFile.get();
//                    Returns the file which can be downloaded or viewed.
            System.out.println(theFile.getFileType());
            String fileName;
            if (theFile.getFileType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")){
                fileName = theFile.getFileName() + ".docx";
            }else{
                fileName = theFile.getFileName() + ".pdf";
            }
            UserDTO currentUser = userService.getUser(principal.getName());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            AuditEntity auditEntity = new AuditEntity(dtf.format(now), principal.getName(), theFile.getFileId(), currentUser.getOrganisationId(),"DOWNLOAD");
            auditService.addAudit(auditEntity);





            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(theFile.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "filename=" + fileName)
                    .body(new ByteArrayResource(theFile.getData()));



        } else {
            return ResponseEntity.ok().body(defaultFile);

        }
    }


}
