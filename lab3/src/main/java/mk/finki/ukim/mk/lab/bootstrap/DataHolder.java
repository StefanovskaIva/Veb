package mk.finki.ukim.mk.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepository;
import mk.finki.ukim.mk.lab.repository.jpa.LocationRepository;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public DataHolder(LocationRepository locationRepository, EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    @PostConstruct
    public void init() {
        Location loc1 = locationRepository.save(new Location("Loc1", "Address 1", "111", "description Loc1"));
        Location loc2 = locationRepository.save(new Location("Loc2", "Address 2", "222", "description Loc2"));
        Location loc3 = locationRepository.save(new Location("Loc3", "Address 3", "333", "description Loc3"));
        Location loc4 = locationRepository.save(new Location("Loc4", "Address 4", "444", "description Loc4"));
        Location loc5 = locationRepository.save(new Location("Loc5", "Address 5", "555", "description Loc5"));

        Event event1 = eventRepository.save(new Event("Event1", "description Event1", 9, loc1));
        Event event2 = eventRepository.save(new Event("Event2", "description Event2", 8.30, loc5));
        Event event3 = eventRepository.save(new Event("Event3", "description Event3", 7.25, loc2));
        Event event4 = eventRepository.save(new Event("Event4", "description Event4", 10.00, loc3));
        Event event5 = eventRepository.save(new Event("Event5", "description Event5", 5.30, loc4));
        Event event6 = eventRepository.save(new Event("Event6", "description Event6", 1.45, loc1));
        Event event7 = eventRepository.save(new Event("Event7", "description Event7", 2.85, loc2));
        Event event8 = eventRepository.save(new Event("Event8", "description Event8", 6.36, loc3));
        Event event9 = eventRepository.save(new Event("Event9", "description Event9", 9.85, loc5));
        Event event10 = eventRepository.save(new Event("Event10", "description Event10", 4.60, loc4));
    }
}
