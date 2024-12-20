package ru.maximuspokez.ttpclub.ttpclub.service.Event.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maximuspokez.ttpclub.ttpclub.model.Event.Event;
import ru.maximuspokez.ttpclub.ttpclub.repository.Event.EventRepository;
import ru.maximuspokez.ttpclub.ttpclub.service.Event.EventService;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository;

  public EventServiceImpl(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Override
  public List<Event> getEvents() {
    return eventRepository.findAll();
  }

  @Override
  public Event getEventById(Long id) {
    return eventRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("Event with ID " + id + " not found"));
  }

  @Override
  public Event createEvent(Event event) {
    return eventRepository.save(event);
  }

  @Override
  public Event updateEvent(Long id, Event event) {
    Optional<Event> existingEvent = eventRepository.findById(id);
    if (existingEvent.isEmpty()) {
      throw new IllegalArgumentException("Event with ID " + id + " not found");
    }
    Event updatedEvent = existingEvent.get();
    updatedEvent.setName(event.getName());
    updatedEvent.setType(event.getType());
    updatedEvent.setDescription(event.getDescription());
    updatedEvent.setStartTime(event.getStartTime());
    updatedEvent.setEndTime(event.getEndTime());
    updatedEvent.setMaxParticipants(event.getMaxParticipants());
    return eventRepository.save(updatedEvent);
  }

  @Override
  public void deleteEvent(Long id) {
    if (!eventRepository.existsById(id)) {
      throw new IllegalArgumentException("Event with ID " + id + " not found");
    }
    eventRepository.deleteById(id);
  }
}
