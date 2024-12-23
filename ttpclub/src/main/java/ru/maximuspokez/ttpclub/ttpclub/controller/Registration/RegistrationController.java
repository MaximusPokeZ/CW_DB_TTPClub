package ru.maximuspokez.ttpclub.ttpclub.controller.Registration;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maximuspokez.ttpclub.ttpclub.model.Registration.Registration;
import ru.maximuspokez.ttpclub.ttpclub.service.Registration.RegistrationService;

import java.util.List;

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
  public ResponseEntity<Registration> getRegistrationById(@PathVariable Long id) {
    Registration registration = registrationService.getRegistrationById(id);
    return ResponseEntity.ok(registration);
  }

  @PostMapping
  public ResponseEntity<Registration> register (@RequestBody Registration registration) {
    Registration reg = registrationService.create(registration);
    return ResponseEntity.ok(reg);
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
}
