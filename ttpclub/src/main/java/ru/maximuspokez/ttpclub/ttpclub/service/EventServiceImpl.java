package ru.maximuspokez.ttpclub.ttpclub.service;

import org.springframework.stereotype.Service;
import ru.maximuspokez.ttpclub.ttpclub.interfaces.EventRepository;
import ru.maximuspokez.ttpclub.ttpclub.interfaces.EventService;
import ru.maximuspokez.ttpclub.ttpclub.model.Event;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository;

  public EventServiceImpl (EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Override
  public List<Event> getEvents() {
    return eventRepository.findAll();
  }

  @Override
  public Event getEventById(Long id) {
    return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
  }

  @Override
  public Event createEvent(Event event) {
    return eventRepository.save(event);
  }

  @Override
  public Event updateEvent(Long id, Event event) {
    Event currentEvent = getEventById(id);
    currentEvent.setName(event.getName());
    currentEvent.setDescription(event.getDescription());
    //TODO
    return eventRepository.save(currentEvent);
  }

  @Override
  public void deleteEvent (Long id) {
    if (!eventRepository.existsById(id)) {
      throw new RuntimeException("Event not found with id: " + id);
    }
    eventRepository.deleteById(id);
  }
}
