package uk.ac.cardiff.team5.graphium.api.controllers.csrf;

import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/csrf")
public class CSRFAPIController {
    @GetMapping("")
    public Map<String, String> get(HttpServletRequest request) {
        String token = new HttpSessionCsrfTokenRepository().loadToken(request).getToken();
        return Collections.singletonMap("token", token);
    }

}
