package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.service.impl.EventServiceImpl;
import mk.finki.ukim.mk.lab.service.impl.LocationServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventServiceImpl eventService;
    private final LocationServiceImpl locationService;

    public EventController(EventServiceImpl eventService, LocationServiceImpl locationService) {
        this.eventService = eventService;
        this.locationService = locationService;
    }

    // Event list page with optional filters
    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String searchName,
                                @RequestParam(required = false) Double minRating,
                                Model model) {
        List<Event> events;

        if (searchName != null && !searchName.isEmpty()) {
            events = eventService.searchEvents(searchName);
        } else {
            events = eventService.listAll();
        }

        if (minRating != null) {
            events = events.stream()
                    .filter(event -> event.getPopularityScore() >= minRating)
                    .toList();
        }

        List<Location> locations = locationService.listAll();
        model.addAttribute("events", events.isEmpty() ? null : events);
        model.addAttribute("locations", locations.isEmpty() ? null : locations);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Gets the username of the currently authenticated user
        model.addAttribute("username", username);

        return "listEvents";
    }


    // Delete event by ID
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteById(id);
        return "redirect:/events";
    }

    // Add event form
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add-form")
    public String addEventPage(Model model) {
        model.addAttribute("locations", locationService.listAll());
        model.addAttribute("event", new Event());
        return "add-event";
    }

    // Edit event form
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit-form/{id}")
    public String editEventPage(Model model, @PathVariable Long id) {
        Event event = eventService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid event ID"));
        model.addAttribute("locations", locationService.listAll());
        model.addAttribute("event", event);
        return "add-event";
    }

    // Event details page
    @GetMapping("/event-details/{id}")
    public String eventDetailsPage(Model model, @PathVariable Long id) {
        Event event = eventService.findById(id).orElseThrow(() -> new IllegalArgumentException("Event not found"));
        model.addAttribute("event", event);
        return "event-details";
    }

    // Filter events by location
    @GetMapping("/by-location")
    public String getEventsByLocation(@RequestParam(required = false) Long locationId, Model model) {
        List<Event> events;
        if (locationId != null) {
            events = eventService.findAllByLocationId(locationId);
        } else {
            events = eventService.listAll(); // Or fetch all events if no location filter
        }

        List<Location> locations = locationService.listAll();
        model.addAttribute("events", events.isEmpty() ? null : events);
        model.addAttribute("locations", locations.isEmpty() ? null : locations);

        return "listEvents"; // Ensure this view exists in src/main/resources/templates
    }


    // Change event rating
    @PostMapping("/change-rating/{id}")
    public String changeEventRating(@PathVariable Long id, HttpServletRequest req) {
        if (req.getParameter("increment") != null) {
            eventService.changeEventRating(id, req.getParameter("increment"));
        } else if (req.getParameter("decrease") != null) {
            eventService.changeEventRating(id, req.getParameter("decrease"));
        }
        return "redirect:/events/event-details/" + id;
    }

    // Handle event booking (for POST requests)
    @PostMapping("/event-booking")
    public String bookEvent(@RequestParam Long rad, @RequestParam int numTickets, Model model) {
        Event event = eventService.findById(rad)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Event ID: " + rad));

        model.addAttribute("eventName", event.getName());
        model.addAttribute("numOfTickets", numTickets);
        model.addAttribute("hostName", "Default Host");
        model.addAttribute("hostAddress", "127.0.0.1");

        return "bookingConfirmation";
    }

    // Redirect or fallback for GET requests to booking
    @GetMapping("/event-booking")
    public String handleGetForEventBooking() {
        return "redirect:/events";
    }

    // Save or update event
    @PostMapping("/add")
    public String saveEvent(@RequestParam(required = false) Long id,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam double popularityScore,
                            @RequestParam Long location) {
        if (id != null) {
            eventService.update(id, name, description, popularityScore, location);
        } else {
            eventService.save(name, description, popularityScore, location);
        }
        return "redirect:/events";
    }
}
