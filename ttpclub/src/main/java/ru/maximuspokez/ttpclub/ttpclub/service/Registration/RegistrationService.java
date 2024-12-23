package ru.maximuspokez.ttpclub.ttpclub.service.Registration;


import ru.maximuspokez.ttpclub.ttpclub.model.Registration.Registration;

import java.util.List;

public interface RegistrationService {
  List<Registration> findAll();
  Registration getRegistrationById(Long id);
  Registration create(Registration registration);
  Registration update(Long id, Registration registration);
  void delete(Long id);
}
