package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepository;
import mk.finki.ukim.mk.lab.repository.jpa.LocationRepository;
import mk.finki.ukim.mk.lab.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public EventServiceImpl(EventRepository eventRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> searchEvents(String text) {
        if (text == null || text.trim().isEmpty()) {
            return eventRepository.findAll();
        }
        return eventRepository.searchEvents(text.trim());
    }

    @Override
    public Optional<Event> save(String name, String description, double popularityScore, Long locationId) {
        Location location = locationRepository.findById(locationId).orElse(null);
        if (location != null) {
            Event event = new Event(name, description, popularityScore, location);
            return Optional.of(eventRepository.save(event));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Event> update(Long id, String name, String description, double popularityScore, Long locationId) {
        Event event = eventRepository.findById(id).orElse(null);
        Location location = locationRepository.findById(locationId).orElse(null);

        if (event != null) {
            event.setName(name);
            event.setDescription(description);
            event.setPopularityScore(popularityScore);
            event.setLocation(location);

            return Optional.of(eventRepository.save(event));
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public void changeEventRating(Long id, String type) {
        eventRepository.findById(id).ifPresent(event -> {
            if ("increment".equalsIgnoreCase(type)) {
                event.setPopularityScore(event.getPopularityScore() + 1);
            } else if ("decrease".equalsIgnoreCase(type)) {
                event.setPopularityScore(event.getPopularityScore() - 1);
            }
            eventRepository.save(event); // Persist changes
        });
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public List<Event> findAllByLocationId(Long locationId) {
        return eventRepository.findAllByLocation_Id(locationId);
    }
}
