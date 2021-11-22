package uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms.validators.ValidFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidFile
public class FileForm {
    private String fileId;
    private String logoFileName;
    private MultipartFile logoFile;
    private String tag;
    private String accessLevel;
    private String comment;
}