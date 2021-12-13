package uk.ac.cardiff.team5.graphium.web.controllers;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.OrganisationEntity;
import uk.ac.cardiff.team5.graphium.exception.EmailInUseException;
import uk.ac.cardiff.team5.graphium.exception.UsernameInUseException;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;
import uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms.UserRegistrationForm;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String Login() {
        TextEncryptor encryptor = Encryptors.delux("password", new String(Hex.encode("salt".getBytes(StandardCharsets.UTF_8))));
        System.out.println(encryptor.encrypt("mohammed.s.hussain@outlook.com"));
        return "login";
    }

    @GetMapping("/logout")
    public String Logout() {
        return "login";
    }
}
