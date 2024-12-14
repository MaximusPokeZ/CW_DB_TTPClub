package ru.maximuspokez.ttpclub.ttpclub.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maximuspokez.ttpclub.ttpclub.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
  List<Event> findByNameContaining(String name);
  //TODO
}
