package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final AuthService authService;
    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    //@RequestMapping(method = RequestMethod.GET, value = "/login")
    @GetMapping
    public String getLoginPage() {
        // Return the name of the Thymeleaf template that will be used to render the login page
        return "login";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        try {
            User user = authService.login(request.getParameter("username"), request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            return "redirect:/events";
        } catch (RuntimeException ex) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", ex.getMessage());
            return "login";
        }
    }

}