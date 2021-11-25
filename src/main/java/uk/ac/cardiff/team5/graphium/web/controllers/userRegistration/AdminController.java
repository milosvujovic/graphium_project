package uk.ac.cardiff.team5.graphium.web.controllers.userRegistration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uk.ac.cardiff.team5.graphium.service.AdminService;
import uk.ac.cardiff.team5.graphium.service.UserService;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;

import java.util.List;

@Controller
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/verify")
    public String verify(Model model) {
        List<UserDTO> unverified =adminService.verify(Long.valueOf(1));
        System.out.println(unverified);
        return "index.html";

    }
}
