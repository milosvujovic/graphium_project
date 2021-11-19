package uk.ac.cardiff.team5.graphium.web.controllers.userRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uk.ac.cardiff.team5.graphium.data.jpa.entity.UserEntity;
import uk.ac.cardiff.team5.graphium.data.jpa.repository.UserRepository;
import uk.ac.cardiff.team5.graphium.domain.User;
import uk.ac.cardiff.team5.graphium.exception.EmailInUseException;
import uk.ac.cardiff.team5.graphium.exception.UsernameInUseException;
import uk.ac.cardiff.team5.graphium.service.UserService;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;
import uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms.UserRegistrationForm;

import javax.validation.Valid;

@Controller
public class UserRegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        UserRegistrationForm userRegistrationForm = new UserRegistrationForm();

        model.addAttribute("userRegistrationForm", userRegistrationForm);

        return "user-registration";
    }

    @PostMapping("/register")
    public String userRegistration(final @Valid UserRegistrationForm userRegistrationForm, final BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("userRegistrationForm", userRegistrationForm);
            return "user-registration";
        }

        UserDTO userDTO = new UserDTO(
            userRegistrationForm.getFirstName(),
            userRegistrationForm.getLastName(),
            userRegistrationForm.getUsername(),
            userRegistrationForm.getEmail(),
            userRegistrationForm.getPassword()
        );

        try {
            userService.register(userDTO);
        } catch (EmailInUseException e) {
            bindingResult.rejectValue("email", "userRegistrationForm.email","An account already exists for this email.");
            model.addAttribute("userRegistrationForm", userRegistrationForm);
            return "user-registration";
        } catch (UsernameInUseException e) {
            bindingResult.rejectValue("username", "userRegistrationForm.username","An account already exists with this username.");
            model.addAttribute("userRegistrationForm", userRegistrationForm);
            return "user-registration";
        }

        return "redirect:/";
    }
}
