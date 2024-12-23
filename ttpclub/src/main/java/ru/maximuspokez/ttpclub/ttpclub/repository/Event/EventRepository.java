package ru.maximuspokez.ttpclub.ttpclub.repository.Event;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maximuspokez.ttpclub.ttpclub.model.Event.Event;

import java.util.List;

@Repository
@Primary
public interface EventRepository extends JpaRepository<Event, Long> {
}
