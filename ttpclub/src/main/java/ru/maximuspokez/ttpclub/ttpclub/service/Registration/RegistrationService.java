package ru.maximuspokez.ttpclub.ttpclub.service.Registration;


import ru.maximuspokez.ttpclub.ttpclub.model.Registration.Registration;
import ru.maximuspokez.ttpclub.ttpclub.model.User.User;

import java.util.List;

public interface RegistrationService {
  List<Registration> findAll();
  List<User> findAllUsersByEventId(Long eventId);
  Registration getRegistrationById(Long id);
  Registration create(Registration registration);
  Registration update(Long id, Registration registration);
  void delete(Long id);
  void delete(Long userId, Long eventId);
}
