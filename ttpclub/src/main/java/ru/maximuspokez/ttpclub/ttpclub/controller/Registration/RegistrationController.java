package ru.maximuspokez.ttpclub.ttpclub.controller.Registration;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maximuspokez.ttpclub.ttpclub.model.Registration.Registration;
import ru.maximuspokez.ttpclub.ttpclub.model.User.DTO.UserDto;
import ru.maximuspokez.ttpclub.ttpclub.model.User.User;
import ru.maximuspokez.ttpclub.ttpclub.service.Registration.RegistrationService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

  private final RegistrationService registrationService;

  public RegistrationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  @GetMapping
  public ResponseEntity<List<Registration>> getAllRegistrations() {
    List<Registration> registrations = registrationService.findAll();
    return ResponseEntity.ok(registrations);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getRegistrationById(@PathVariable Long id) {
    try {
      Optional<Registration> registration = Optional.ofNullable(registrationService.getRegistrationById(id));

      if (registration.isPresent()) {
        return ResponseEntity.ok(registration.get());
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registration not found");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("An unexpected error occurred: " + e.getMessage());
    }
  }


  @GetMapping("/event/{id}")
  public ResponseEntity<List<UserDto>> getAllUsersByEventId(@PathVariable Long id) {
    List<User> users = registrationService.findAllUsersByEventId(id);
    return ResponseEntity.ok(users.stream().map(UserDto::new).collect(Collectors.toList()));
  }

  @PostMapping
  public ResponseEntity<Registration> register (@RequestBody Registration registration) {
    try {
      Registration reg = registrationService.create(registration);
      return ResponseEntity.ok(reg);
    } catch (Exception e) {
      return ResponseEntity.status(409).build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Registration> updateRegistration(@PathVariable Long id, @RequestBody Registration registration) {
    Registration reg = registrationService.update(id, registration);
    return ResponseEntity.ok(reg);
  }

  @DeleteMapping("/{id}")
  public void deleteRegistration(@PathVariable Long id) {
    registrationService.delete(id);
  }

  @DeleteMapping("/delete/{userId}/{eventId}")
  public void deleteRegistrationByUserId(@PathVariable Long userId, @PathVariable Long eventId) {
    registrationService.delete(userId, eventId);
  }
}
