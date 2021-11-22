package uk.ac.cardiff.team5.graphium.web.controllers.userRegistration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.DBFile;
import uk.ac.cardiff.team5.graphium.files.FileServer;
import uk.ac.cardiff.team5.graphium.service.dto.FileDTO;
import uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms.FileForm;

import javax.validation.Valid;
import java.util.List;
@Controller
public class UserUploadController {
    private FileServer fileServer ;

    public UserUploadController(FileServer anFileServer) {
        fileServer = anFileServer;
    }

    @GetMapping("file")
    public String file(Model model) {
        FileForm form = new FileForm();
        model.addAttribute("fileForm",form);
        return "file-upload.html";
    }
    @PostMapping("file")
    public String getFile(@Valid FileForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage","Only PDF and Word Documents allowed.");
            return "file-upload.html";
        }else{
            FileDTO newFile = new FileDTO(form.getFileId(), form.getLogoFileName(),form.getTag(),form.getAccessLevel(),form.getComment(),form.getLogoFile());
            String fileId = fileServer.saveFiles(newFile);
        }
        return "redirect:/display";
    }
    @GetMapping("display")
    public String displayListOfFiles(Model model) {
        List<DBFile> fileList;
        fileList = fileServer.getFiles();
        model.addAttribute("fileList", fileList);
        return "/files.html";

    }
}