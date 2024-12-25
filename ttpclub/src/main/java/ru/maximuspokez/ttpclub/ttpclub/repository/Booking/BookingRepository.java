package ru.maximuspokez.ttpclub.ttpclub.repository.Booking;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maximuspokez.ttpclub.ttpclub.model.Booking.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
