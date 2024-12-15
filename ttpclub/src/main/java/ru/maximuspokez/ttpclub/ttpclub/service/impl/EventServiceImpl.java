package ru.maximuspokez.ttpclub.ttpclub.service.impl;

import org.springframework.stereotype.Service;
//import ru.maximuspokez.ttpclub.ttpclub.repository.EventRepository;
import ru.maximuspokez.ttpclub.ttpclub.model.Event;
import ru.maximuspokez.ttpclub.ttpclub.repository.EventReposityDAO;
import ru.maximuspokez.ttpclub.ttpclub.service.EventService;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

  private final EventReposityDAO eventRepository;

  public EventServiceImpl (EventReposityDAO eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Override
  public List<Event> getEvents() {
    return eventRepository.getEvents();
  }

  @Override
  public Event getEventById(Long id) {
    return eventRepository.getEventById(id);
  }

  @Override
  public Event createEvent(Event event) {
    return eventRepository.createEvent(event);
  }

  @Override
  public Event updateEvent(Long id, Event event) {
   return eventRepository.updateEvent(id, event);
  }

  @Override
  public void deleteEvent (Long id) {
    deleteEvent(id);
  }
}
