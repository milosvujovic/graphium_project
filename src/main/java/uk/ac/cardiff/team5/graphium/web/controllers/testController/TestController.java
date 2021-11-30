package uk.ac.cardiff.team5.graphium.web.controllers.testController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/test")
    public String test(Model model) {
        return "test-page";
    }
    @GetMapping("/tab")
    public String tab(Model model) {
        return "MyTab.html";
    }
}
