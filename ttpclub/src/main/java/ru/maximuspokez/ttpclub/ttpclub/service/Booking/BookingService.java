package ru.maximuspokez.ttpclub.ttpclub.service.Booking;

import ru.maximuspokez.ttpclub.ttpclub.model.Booking.Booking;

import java.util.List;

public interface BookingService {
  List<Booking> getAllBookings();
  Booking getBooking(Long id);
  Booking createBooking(Booking booking);
  Booking updateBooking(Long id, Booking booking);
  void deleteBooking(Long id);
  void updateBookingStatus(Long id, String status);
}
