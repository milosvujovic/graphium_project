package uk.ac.cardiff.team5.graphium.web.controllers.userRegistration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uk.ac.cardiff.team5.graphium.web.controllers.userRegistration.forms.UserRegistrationForm;

@Controller
public class UserRegistration {
    @GetMapping("/register")
    public String register(Model model) {
        UserRegistrationForm userRegistrationForm = new UserRegistrationForm();

        model.addAttribute("userRegistrationForm", userRegistrationForm);

        return "user-registration";
    }

    @PostMapping("/register")
    public String userRegistration(UserRegistrationForm userRegistrationForm, Model model) {
        System.out.println(userRegistrationForm.getFirstName());
        return "redirect:register";
    }
}
