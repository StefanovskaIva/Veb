package mk.finki.ukim.mk.lab.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController {
    @RequestMapping("/custom-error")
    public String handleError(org.springframework.ui.Model model) {
        model.addAttribute("message", "An unexpected error occurred.");
        return "error";
    }
}


