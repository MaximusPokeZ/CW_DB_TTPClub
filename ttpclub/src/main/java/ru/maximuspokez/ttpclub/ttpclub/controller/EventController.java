package ru.maximuspokez.ttpclub.ttpclub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maximuspokez.ttpclub.ttpclub.interfaces.EventService;
import ru.maximuspokez.ttpclub.ttpclub.model.Event;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

  private final EventService eventService;

  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  public ResponseEntity<List<Event>> findAll() {
    List<Event> events = eventService.getEvents();
    return ResponseEntity.ok(events);
  }

}
