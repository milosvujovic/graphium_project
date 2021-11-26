package uk.ac.cardiff.team5.graphium.web.controllers.userRegistration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uk.ac.cardiff.team5.graphium.files.FileServer;
import uk.ac.cardiff.team5.graphium.service.UserService;
import uk.ac.cardiff.team5.graphium.service.dto.FileDTO;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;
import uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms.FileForm;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
@Controller
public class UserUploadController {
    private FileServer fileServer ;
    private UserService userService;

    public UserUploadController(FileServer anFileServer, UserService aUserService) {
        fileServer = anFileServer;
        userService = aUserService;
    }
//      Displays form to upload files to the webpage.
    @GetMapping("file")
    public String file(Model model) {
        FileForm form = new FileForm();
        model.addAttribute("fileForm",form);
        return "file-upload.html";
    }

//    Post method for uploading a file
    @PostMapping("file")
    public String getFile(@Valid FileForm form, BindingResult bindingResult, Model model, Principal principal) throws IOException {
//      Error catching.
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage","Only PDF and Word Documents allowed.");
            return "file-upload.html";
        }else{
//            Saves the form as a FileDTO and then saves it to the database.
            LocalDate today = LocalDate.now();
            FileDTO newFile = new FileDTO(form.getFileId(), form.getLogoFileName(),form.getLogoFile().getContentType(),form.getTag(),form.getAccessLevel(),form.getComment(),form.getLogoFile().getBytes(), today.toString());
            String fileId = fileServer.saveFiles(newFile,principal.getName());
//            Displays the users files
            return "redirect:/myFiles";
        }
    }
//    Displays the users file
    @GetMapping("myFiles")
    public String displayUsersFiles(Model model, Principal principal) {
//        Deals with getting the user here
        UserDTO user = userService.getUser(principal.getName());
//        Adds the users details including the files to the model and returns the page.
        model.addAttribute("user", user);
        return "files.html";
    }
//   Lets the user view the file on the page
    @GetMapping("file/view/{fileId}")
    public String viewFile(@PathVariable(value = "fileId", required = true) String name, Model model){
        model.addAttribute("id" , name);
        return "/file-viewer.html";
    }
}