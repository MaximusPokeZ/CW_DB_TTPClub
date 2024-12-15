package ru.maximuspokez.ttpclub.ttpclub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maximuspokez.ttpclub.ttpclub.service.EventService;
import ru.maximuspokez.ttpclub.ttpclub.model.Event;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

  private final EventService eventService;

  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping
  public ResponseEntity<List<Event>> findAll() {
    List<Event> events = eventService.getEvents();
    return ResponseEntity.ok(events);
  }

  @PostMapping("create_event")
  public ResponseEntity<Event> createEvent(@RequestBody Event event) {
    return ResponseEntity.ok(eventService.createEvent(event));
  }

  @GetMapping("/{id}")
  public Event getEventById(@PathVariable Long id) {
    return eventService.getEventById(id);
  }

  @PutMapping("update_student")
  public Event updateEventById(@PathVariable Long id, @RequestBody Event event) {
    return eventService.updateEvent(id, event);
  }

  @DeleteMapping("delete_student/{id}")
  public void deleteEventById(@PathVariable Long id) {
    eventService.deleteEvent(id);
  }

}
