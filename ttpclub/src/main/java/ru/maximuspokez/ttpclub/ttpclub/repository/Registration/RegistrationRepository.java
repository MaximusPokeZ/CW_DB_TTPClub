package ru.maximuspokez.ttpclub.ttpclub.repository.Registration;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maximuspokez.ttpclub.ttpclub.model.Registration.Registration;

import java.util.List;


public interface RegistrationRepository extends JpaRepository<Registration, Long> {
  boolean existsByUserIdAndEventId(Long userId, Long eventId);
  List<Registration> findAllByEventId(Long eventId);
  Registration findByUserIdAndEventId(Long userId, Long eventId);
}
