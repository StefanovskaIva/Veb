package mk.finki.ukim.mk.lab.repository.jpa;

import mk.finki.ukim.mk.lab.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByLocation_Id(Long locationId);

    @Query("SELECT e FROM Event e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Event> searchEvents(@Param("keyword") String keyword);

}
