package ru.maximuspokez.ttpclub.ttpclub.repository.Registration;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maximuspokez.ttpclub.ttpclub.model.Registration.Registration;


public interface RegistrationRepository extends JpaRepository<Registration, Long> {

}
