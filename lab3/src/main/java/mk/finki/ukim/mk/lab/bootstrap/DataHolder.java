package mk.finki.ukim.mk.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Event> eventList = new ArrayList<>();
    public static List<Location> locationList = new ArrayList<>();

    @PostConstruct
    public void init() {
        locationList.add(new Location("Loc1", "Address 1", "111", "description Loc1"));
        locationList.add(new Location("Loc2", "Address 2", "222", "description Loc2"));
        locationList.add(new Location("Loc3", "Address 3", "333", "description Loc3"));
        locationList.add(new Location("Loc4", "Address 4", "444", "description Loc4"));
        locationList.add(new Location("Loc5", "Address 5", "555", "description Loc5"));

        eventList.add(new Event("Event1", "description Event1", 9, locationList.get(0)));
        eventList.add(new Event("Event2", "description Event2", 8.30, locationList.get(1)));
        eventList.add(new Event("Event3", "description Event3", 7.25,locationList.get(2)));
        eventList.add(new Event("Event4", "description Event4", 10.00, locationList.get(3)));
        eventList.add(new Event("Event5", "description Event5", 5.30, locationList.get(4)));
        eventList.add(new Event("Event6", "description Event6", 1.45,locationList.get(0)));
        eventList.add(new Event("Event7", "description Event7", 2.85, locationList.get(1)));
        eventList.add(new Event("Event8", "description Event8", 6.36, locationList.get(2)));
        eventList.add(new Event("Event9", "description Event9", 9.85, locationList.get(3)));
        eventList.add(new Event("Event10", "description Event10", 4.60,locationList.get(4)));
    }
}
