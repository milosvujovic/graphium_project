package uk.ac.cardiff.team5.graphium.api.controllers.csrf;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/csrf")
public class CSRFAPIController {
    @GetMapping("")
    public String get(HttpServletRequest request) {
        CsrfToken token = new HttpSessionCsrfTokenRepository().loadToken(request);
        return token.getToken();
    }

}
