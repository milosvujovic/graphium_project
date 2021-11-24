package uk.ac.cardiff.team5.graphium.web.exceptions;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // 413 MultipartException - file size too big
    @ExceptionHandler({MultipartException.class, FileSizeLimitExceededException.class,java.lang.IllegalStateException.class})
    public ResponseEntity<Object> handleSizeExceededException(final WebRequest request, final MultipartException ex) {
        //log.warn("413 Status Code. File size too large {}", ex.getMessage());

        final ApiError apiError = message(HttpStatus.PAYLOAD_TOO_LARGE, ex);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.PAYLOAD_TOO_LARGE, request);
    }
}
