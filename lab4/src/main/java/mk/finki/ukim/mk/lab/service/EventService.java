package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.SavedBooking;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> listAll();
    List<Event> searchEvents(String text);
    void deleteById(Long id);
    void changeEventRating(Long id, String type);
    Optional<Event> save(String name, String description, double popularityScore, Long locationId);
    Optional<Event> update(Long id, String name, String description, double popularityScore, Long locationId);
    Optional<Event> findById(Long id);
    List<Event> findAllByLocationId(Long locationId);
}
