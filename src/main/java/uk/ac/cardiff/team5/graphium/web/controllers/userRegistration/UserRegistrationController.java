package uk.ac.cardiff.team5.graphium.web.controllers.userRegistration;

import org.springframework.beans.factory.annotation.Autowired;
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
import uk.ac.cardiff.team5.graphium.data.jpa.repository.OrganisationRepository;
import uk.ac.cardiff.team5.graphium.exception.EmailInUseException;
import uk.ac.cardiff.team5.graphium.exception.UsernameInUseException;
import uk.ac.cardiff.team5.graphium.service.EmailSenderService;
import uk.ac.cardiff.team5.graphium.service.UserService;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;
import uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms.UserRegistrationForm;

import javax.validation.Valid;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class UserRegistrationController {
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSenderService senderService;

    @GetMapping("/register")
    public String register(Model model) {
        UserRegistrationForm userRegistrationForm = new UserRegistrationForm();

        List<OrganisationEntity> organisationEntities = organisationRepository.findAll();
        model.addAttribute("organisationEntities", organisationEntities);

        model.addAttribute("userRegistrationForm", userRegistrationForm);

        return "user-registration";
    }

    @PostMapping("/register")
    public String userRegistration(final @Valid UserRegistrationForm userRegistrationForm, final BindingResult bindingResult, Model model, RedirectAttributes redirAttrs) {
        if(bindingResult.hasErrors()){
            List<OrganisationEntity> organisationEntities = organisationRepository.findAll();
            model.addAttribute("organisationEntities", organisationEntities);
            model.addAttribute("userRegistrationForm", userRegistrationForm);
            return "user-registration";
        }

        if (!userService.checkPasswordsMatch(userRegistrationForm.getPassword(), userRegistrationForm.getPasswordConf())) {
            bindingResult.rejectValue("password", "userRegistrationForm.password","Passwords do not match.");
            List<OrganisationEntity> organisationEntities = organisationRepository.findAll();
            model.addAttribute("organisationEntities", organisationEntities);
            model.addAttribute("userRegistrationForm", userRegistrationForm);
            return "user-registration";
        }

        UserDTO userDTO = new UserDTO(
            userRegistrationForm.getFirstName(),
            userRegistrationForm.getLastName(),
            Long.parseLong(userRegistrationForm.getOrganisationId()),
            userRegistrationForm.getUsername(),
            userRegistrationForm.getEmail(),
            userRegistrationForm.getPassword()
        );

        try {
            userService.register(userDTO);
        } catch (EmailInUseException e) {
            bindingResult.rejectValue("email", "userRegistrationForm.email","An account already exists for this email.");
            List<OrganisationEntity> organisationEntities = organisationRepository.findAll();
            model.addAttribute("organisationEntities", organisationEntities);
            model.addAttribute("userRegistrationForm", userRegistrationForm);
            return "user-registration";
        } catch (UsernameInUseException e) {
            bindingResult.rejectValue("username", "userRegistrationForm.username","An account already exists with this username.");
            List<OrganisationEntity> organisationEntities = organisationRepository.findAll();
            model.addAttribute("organisationEntities", organisationEntities);
            model.addAttribute("userRegistrationForm", userRegistrationForm);
            return "user-registration";
        }

        try {
            List<UserDTO> admins = userService.getOrgAdmin(userRegistrationForm.getOrganisationId());

            senderService.sendEmail(admins.get(0).getEmail(), "Your organisation has a new user: " + userRegistrationForm.getEmail(), "Check your admin panel on the website to approve or reject this new user.\n\nUser Details:\n" + "Full Name: " + userRegistrationForm.getFirstName() + " " + userRegistrationForm.getLastName() + "\n" + "Email Address: " + userRegistrationForm.getEmail());
        } catch (Exception e){
            System.out.println("Organisation admin not found - user cannot be verified");
        }

        redirAttrs.addFlashAttribute("message","Registration was successful");

        return "redirect:/login";

    }
}
