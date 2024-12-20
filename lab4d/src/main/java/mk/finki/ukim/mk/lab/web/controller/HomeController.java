package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final EventService eventService;

    public HomeController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String homePage(Model model, Principal principal) {
        List<Event> events = eventService.listAll();
        model.addAttribute("events", events);

        if (principal == null) {
            // No user logged in, set default username to 'admin'
            model.addAttribute("username", "admin");
            return "/guest"; // Render guest.html
        }

        // Redirect logged-in users to /events
        return "redirect:/events";
    }

}


