package ru.maximuspokez.ttpclub.ttpclub.repository;

import ru.maximuspokez.ttpclub.ttpclub.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventReposityDAO {
  private final List<Event> events = new ArrayList<>();

  public List<Event> getEvents() {
    return events;
  }

  public Event getEventById(Long id) {
    return events.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
  }

  public Event createEvent(Event event) {
    events.add(event);
    return event;
  }

  public Event updateEvent(Long id, Event event) {
    Event existingEvent = getEventById(id);
    if (existingEvent == null) {
      return null;
    }
    existingEvent.setName(event.getName());
    existingEvent.setDescription(event.getDescription());
    existingEvent.setStartTime(event.getStartTime());
    existingEvent.setEndTime(event.getEndTime());
    return existingEvent;
  }

  public void deleteEvent (Long id) {
    Event existingEvent = getEventById(id);
    if (existingEvent == null) {
      return;
    }
    events.remove(existingEvent);
  }
}
