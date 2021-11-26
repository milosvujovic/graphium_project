package uk.ac.cardiff.team5.graphium.web.controllers.sysAdminPage;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SysAdminPageController {
    @GetMapping("/sys-admin")
    public String sysAdminPage(Model model) {
        return "sys-admin-page";
    }
}
