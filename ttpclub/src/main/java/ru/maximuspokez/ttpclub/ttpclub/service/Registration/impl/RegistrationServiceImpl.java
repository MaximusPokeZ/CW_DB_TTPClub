package ru.maximuspokez.ttpclub.ttpclub.service.Registration.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.maximuspokez.ttpclub.ttpclub.model.Registration.Registration;
import ru.maximuspokez.ttpclub.ttpclub.model.User.User;
import ru.maximuspokez.ttpclub.ttpclub.repository.Registration.RegistrationRepository;
import ru.maximuspokez.ttpclub.ttpclub.repository.User.UserRepository;
import ru.maximuspokez.ttpclub.ttpclub.service.Registration.RegistrationService;

import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {

  private final RegistrationRepository registrationRepository;

  private final UserRepository userRepository;

  public RegistrationServiceImpl(RegistrationRepository registrationRepository, UserRepository userRepository) {
    this.registrationRepository = registrationRepository;
    this.userRepository = userRepository;
  }

  @Override
  public List<Registration> findAll() {
    return registrationRepository.findAll();
  }

  @Override
  public List<User> findAllUsersByEventId(Long eventId) {
    List<Registration> registrations = registrationRepository.findAllByEventId(eventId);
    return registrations.stream()
            .map(registration -> userRepository.findById(registration.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User with ID " + registration.getUserId() + " not found")))
            .toList();
  }

  @Override
  public Registration getRegistrationById(Long id) {
    return registrationRepository.findById(id).orElse(null);
  }

  @Override
  public Registration create(Registration registration) {
    boolean exists = registrationRepository.existsByUserIdAndEventId(registration.getUserId(), registration.getEventId());
    if (exists) {
      throw new IllegalStateException("User is already registered for this event");
    }
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

  @Override
  public void delete(Long userId, Long eventId)
  {
    Registration registration = registrationRepository.findByUserIdAndEventId(userId, eventId);
    if (registration == null) {
      throw new IllegalArgumentException("Registration with ID " + userId + " not found");
    }
    registrationRepository.delete(registration);
  }
}
