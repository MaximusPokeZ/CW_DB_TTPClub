package ru.maximuspokez.ttpclub.ttpclub.service.Registration.impl;

import org.springframework.stereotype.Service;
import ru.maximuspokez.ttpclub.ttpclub.model.Registration.Registration;
import ru.maximuspokez.ttpclub.ttpclub.repository.Registration.RegistrationRepository;
import ru.maximuspokez.ttpclub.ttpclub.service.Registration.RegistrationService;

import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {

  private final RegistrationRepository registrationRepository;

  public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
    this.registrationRepository = registrationRepository;
  }

  @Override
  public List<Registration> findAll() {
    return registrationRepository.findAll();
  }

  @Override
  public Registration getRegistrationById(Long id) {
    return registrationRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("Registration with ID " + id + " not found"));
  }

  @Override
  public Registration create(Registration registration) {
    return registrationRepository.save(registration);
  }

  @Override
  public Registration update(Long id, Registration registration) {
    Registration existingRegistration = getRegistrationById(id);
    if (existingRegistration == null) {
      throw new IllegalArgumentException("Registration with ID " + id + " not found");
    }

    existingRegistration.setEventId(registration.getEventId());
    existingRegistration.setRegistrationDate(registration.getRegistrationDate());
    existingRegistration.setStatus(registration.getStatus());
    existingRegistration.setUserId(registration.getUserId());

    return registrationRepository.save(existingRegistration);
  }

  @Override
  public void delete(Long id) {
    Registration registration = getRegistrationById(id);
    if (registration == null) {
      throw new IllegalArgumentException("Registration with ID " + id + " not found");
    }
    registrationRepository.delete(registration);
  }
}
