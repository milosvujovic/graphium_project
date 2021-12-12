package uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms.validators.ValidFile;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidFile
public class FileForm {
    private String fileId;
    @Size(min = 1, max = 20, message = "The file name must contain between 1 and 20 characters")
    private String fileName;
    private MultipartFile file;
    private String tag;
    private String accessLevel;
    @Size(max = 50, message = "The comment must be less than 50 characters")
    private String comment;
    @Size(min = 1, max = 20, message = "The subject must contain between 1 and 20 characters")
    private String subject;
}