package uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms.validators.FileTypeValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface ValidFile {
    String message() default "You can only upload pdfs or word documents";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
