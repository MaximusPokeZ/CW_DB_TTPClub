package ru.maximuspokez.ttpclub.ttpclub.interfaces;

import ru.maximuspokez.ttpclub.ttpclub.model.Event;

import java.util.List;

public interface EventService {
  List<Event> getEvents();
  Event getEventById(Long id);
  Event createEvent(Event event);
  Event updateEvent(Long id, Event event);
  void deleteEvent(Long id);
}
