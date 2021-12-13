package uk.ac.cardiff.team5.graphium.web.controllers.userRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.AuditEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.DBFile;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.OrganisationEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.UserEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.OrganisationRepository;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.UserRepository;
import uk.ac.cardiff.team5.graphium.domain.FileDisplayer;
import uk.ac.cardiff.team5.graphium.domain.User;
import uk.ac.cardiff.team5.graphium.files.DBFileStoreRepo;
import uk.ac.cardiff.team5.graphium.files.FileServer;
import uk.ac.cardiff.team5.graphium.service.AuditService;
import uk.ac.cardiff.team5.graphium.service.UserService;
import uk.ac.cardiff.team5.graphium.service.dto.AuditDTO;
import uk.ac.cardiff.team5.graphium.service.dto.FileDTO;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;
import uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms.FileForm;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileUploadController {
    private FileServer fileServer;
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DBFileStoreRepo dbFileStoreRepo;

    @Autowired
    private AuditService auditService;

    public FileUploadController(FileServer anFileServer, UserService aUserService) {
        fileServer = anFileServer;
        userService = aUserService;
    }

    //      Displays form to upload files to the webpage.
    @GetMapping({"upload", "file"})
    public String file(Model model, Principal principal) {

        UserEntity currentUser = userRepository.findByUsername(principal.getName());
        OrganisationEntity currentOrganisation = currentUser.getOrganisation();
        String myString = currentOrganisation.getOrganisationName();
        model.addAttribute("organisationName",myString);

        FileForm form = new FileForm();
        model.addAttribute("fileForm", form);
        return "file-upload.html";
    }

    //    Post method for uploading a file
    @PostMapping("upload")
    public String getFile(@Valid FileForm form, BindingResult bindingResult, Model model, Principal principal) throws IOException {
//      Error catching.
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("file")){
                model.addAttribute("errorMessage","Only PDF and Word Documents allowed.");
            }
            return "file-upload.html";
        } else {
//            Saves the form as a FileDTO and then saves it to the database.
            LocalDate today = LocalDate.now();
            FileDTO newFile = new FileDTO(form.getFileId(), form.getFileName(), form.getFile().getContentType(), form.getTag(), form.getAccessLevel(), form.getComment(), form.getFile().getBytes(), today.toString(), form.getSubject());
            String fileId = fileServer.saveFiles(newFile, principal.getName());
//            Displays the users files
            return "redirect:/files";
        }
    }

    //    Displays all files available to the user
    @GetMapping({"files", "myFiles"})
    public String displayFiles(Model model, Principal principal) {
        UserEntity currentUser = userRepository.findByUsername(principal.getName());
        OrganisationEntity currentOrganisation = currentUser.getOrganisation();
        String myString = currentOrganisation.getOrganisationName();
        model.addAttribute("organisationName",myString);
        List<AuditEntity> usersAudits = new ArrayList<AuditEntity>();
        List<DBFile> usersFiles = currentUser.getFiles();
        for(int i=0;i<usersFiles.size();i++){
            List<AuditEntity> currentFileAudits = auditService.getAuditByFileId(usersFiles.get(i).getFileId());
            for(int j=0; j<currentFileAudits.size();j++){
                AuditEntity currentFileAudit = currentFileAudits.get(j);
                usersAudits.add(currentFileAudit);
            }
        }

        for(int i=0;i<usersAudits.size();i++){
            System.out.println(usersAudits.get(i).getFileId() + " ACTION: " + usersAudits.get(i).getAction()+ " @ " + usersAudits.get(i).getDate() + " by " + usersAudits.get(i).getUsername());
        }

        model.addAttribute("audits", usersAudits);
        // find current user DONE
        // find all files uploaded by current user DONE
        // find all insights from that list of files DONE
        // add to model DONE

        return "files.html";
    }

    //   Lets the user view the file on the page
    @GetMapping("file/view/{fileId}")
    public String viewFile(@PathVariable(value = "fileId", required = true) String name, Model model, Principal principal) {
        if (userService.hasAccessToFile(principal.getName(), name)) {
            model.addAttribute("id", name);

            // Makes Log
            UserDTO currentUser = userService.getUser(principal.getName());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            AuditEntity auditEntity = new AuditEntity(dtf.format(now), principal.getName(), name, currentUser.getOrganisationId(),"VIEWED","NULL");
            auditService.addAudit(auditEntity);

            return "/file-viewer.html";
        } else {
            return "error/403.html";
        }
    }


    //      Displays form to upload files to the webpage.
    @GetMapping("/file/modify/{fileID}")
    public String fileReUpload(Model model, @PathVariable(value = "fileID", required = true) String fileID, Principal principal) {
        if (userService.canModifyFile(principal.getName(), fileID)) {
            FileForm form = new FileForm();
            form.setFileId(fileID);
            model.addAttribute("fileForm", form);
            return "file-reupload.html";
        } else {
            return "error/403.html";
        }
    }
    //    Post method for uploading a file
    @PostMapping("/file/reupload")
    public String fileReUploader(@Valid FileForm submittedForm, BindingResult bindingResult, Model model, Principal principal) throws IOException {
//      Error catching.
        if (bindingResult.hasErrors()) {
            FileForm form = new FileForm();
            form.setFileId(submittedForm.getFileId());
            model.addAttribute("fileForm",form);
            model.addAttribute("errorMessage","Only PDF and Word Documents allowed.");
            return "file-reupload.html";
        }else{
            LocalDate today = LocalDate.now();
            fileServer.modifyFiles(submittedForm.getFile().getBytes(), submittedForm.getFileId(), today, submittedForm.getFile().getContentType());
//            Displays the users files
            return "redirect:/files";
        }
    }

}
