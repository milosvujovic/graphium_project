package uk.ac.cardiff.team5.graphium.web.controllers.sysAdminPanel;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SysAdminPanelController {
    @GetMapping("/sys-admin")
    public String sysAdminPanel(Model model) {
        return "sys-admin-panel";
    }
}
