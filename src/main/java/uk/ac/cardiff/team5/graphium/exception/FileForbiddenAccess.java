package uk.ac.cardiff.team5.graphium.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Can't access File.")
public class FileForbiddenAccess extends RuntimeException{
    public FileForbiddenAccess(String s) {
        super(s);
    }
}

