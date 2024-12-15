package ru.maximuspokez.ttpclub.ttpclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maximuspokez.ttpclub.ttpclub.model.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
  List<Event> findByNameContaining(String name);
  //TODO
}
