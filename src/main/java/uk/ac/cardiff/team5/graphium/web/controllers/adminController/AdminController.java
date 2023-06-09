package uk.ac.cardiff.team5.graphium.web.controllers.adminController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.cardiff.team5.graphium.service.AdminService;
import uk.ac.cardiff.team5.graphium.service.UserService;
import uk.ac.cardiff.team5.graphium.service.dto.UserDTO;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;
    private UserService userService;

    public AdminController(AdminService adminService, UserService aUserService) {
        this.adminService = adminService;
        this.userService = aUserService;
    }

    @GetMapping({"/", "", "/home"})
    public String home() {
        return "admin-index";
    }

    @GetMapping("/verify")
    public String verify(Model model, Principal principal) {
        List<UserDTO> unverified = adminService.verify(userService.getUser(principal.getName()).getOrganisationId());
        model.addAttribute("userList", unverified);
        return "admin-verify";
    }

    @PostMapping("/verified/{userName}")
    public String verifyUser(@PathVariable(value = "userName", required = true) String userName, Principal principal) {
        if (adminService.canVerifyUser(userName, principal.getName())) {
            adminService.verifyUser(userName);
        }
        return "redirect:/admin/verify";
    }

    @GetMapping("/partner")
    public String possiblePartners(Model model, Principal principal) {
        return "admin-partner";
    }

    @PostMapping("/partner/{orgID}")
    public String recordPartner(@PathVariable(value = "orgID", required = true) String orgId, Model model, Principal principal) {
        if (!adminService.partnershipAlreadyExists(orgId, principal.getName())) {
            adminService.recordPartnership(orgId, principal.getName());
        }
        return "redirect:/admin/partner";
    }

    @GetMapping("/members")
    public String getMembers(Model model, Principal principal) {
        List<UserDTO> members = adminService.getOrganisationMembers(principal.getName());
        model.addAttribute("members", members);
        return "admin-members";
    }
}
