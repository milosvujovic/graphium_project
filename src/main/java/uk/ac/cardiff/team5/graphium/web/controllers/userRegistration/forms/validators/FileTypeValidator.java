package uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms.validators;
import uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms.FileForm;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class FileTypeValidator implements ConstraintValidator<ValidFile, FileForm> {
    @Override
    public boolean isValid(FileForm fileForm, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("Testing the valid file");
        List<String> allowedFileTypes = Arrays.asList("application/vnd.openxmlformats-officedocument.wordprocessingml.document","application/pdf");
        Boolean isValid = allowedFileTypes.contains(fileForm.getLogoFile().getContentType());
        return isValid;

    }

}
