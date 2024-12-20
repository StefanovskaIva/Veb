package mk.finki.ukim.mk.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.enumerations.Role;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepository;
import mk.finki.ukim.mk.lab.repository.jpa.LocationRepository;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<User> users = null;
    public static List<Location> locations = null;
    public static List<Event> events = null;

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public DataHolder(LocationRepository locationRepository, EventRepository eventRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        locations = new ArrayList<>();
        events = new ArrayList<>();
        users = new ArrayList<>();

        if(this.locationRepository.count() == 0) {
            locations.add(new Location("Loc1", "Address 1", 111, "description Loc1"));
            locations.add(new Location("Loc2", "Address 2", 222, "description Loc2"));
            locations.add(new Location("Loc3", "Address 3", 333, "description Loc3"));
            locations.add(new Location("Loc4", "Address 4", 444, "description Loc4"));
            locations.add(new Location("Loc5", "Address 5", 555, "description Loc5"));

            this.locationRepository.saveAll(locations);
        }

        if(this.eventRepository.count() == 0) {
            events.add(new Event("Event1", "description Event1", 9, locations.get(0)));
            events.add(new Event("Event2", "description Event2", 8.30, locations.get(1)));
            events.add(new Event("Event3", "description Event3", 7.25, locations.get(3)));
            events.add(new Event("Event4", "description Event4", 10.00, locations.get(4)));
            events.add(new Event("Event5", "description Event5", 5.30, locations.get(2)));
            events.add(new Event("Event6", "description Event6", 1.45, locations.get(0)));
            events.add(new Event("Event7", "description Event7", 2.85, locations.get(3)));
            events.add(new Event("Event8", "description Event8", 6.36, locations.get(4)));
            events.add(new Event("Event9", "description Event9", 9.85, locations.get(2)));
            events.add(new Event("Event10", "description Event10", 4.60, locations.get(1)));

            this.eventRepository.saveAll(events);
        }

        if(this.userRepository.count() == 0) {
            users.add(new User("iva.stefanovska", passwordEncoder.encode("is"), "Iva", "Stefanovska", Role.USER));
            users.add(new User("marija.soleska", passwordEncoder.encode("ms"), "Marija", "Soleska", Role.USER));
            users.add(new User("anastasija.ruleva", passwordEncoder.encode("ar"), "Anastasija", "Ruleva", Role.USER));

            this.userRepository.saveAll(users);
        }


    }
}
