package mk.finki.ukim.mk.lab.repository.inmemory;

import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.SavedBooking;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemEventRepository {

    private final EventRepository eventRepository; // Inject JPA Repository
    private List<SavedBooking> savedBookings = new ArrayList<>();

    public MemEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public List<Event> searchEvents(String text) {
        if (text == null || text.trim().isEmpty()) {
            return eventRepository.findAll(); // Return all events if search text is empty
        }
        return eventRepository.searchEvents(text.trim());
    }

    public List<SavedBooking> getSavedBookings() {
        return savedBookings;
    }

    public Optional<Event> save(String name, String description, double popularityScore, Location location) {
        Event newEvent = new Event(name, description, popularityScore, location);
        return Optional.of(eventRepository.save(newEvent));
    }

    public void changeEventRating(Long id, String type) {
        eventRepository.findById(id).ifPresent(event -> {
            event.changePopularity(type);
            eventRepository.save(event);
        });
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    public void addBooking(String evName, int numTickets) {
        boolean bookingExists = false;
        for (SavedBooking booking : savedBookings) {
            if (booking.getEventName().equals(evName)) {
                booking.setNumberOfTickets(booking.getNumberOfTickets() + numTickets);
                bookingExists = true;
                break;
            }
        }
        if (!bookingExists) {
            savedBookings.add(new SavedBooking(evName, numTickets));
        }
    }
}
