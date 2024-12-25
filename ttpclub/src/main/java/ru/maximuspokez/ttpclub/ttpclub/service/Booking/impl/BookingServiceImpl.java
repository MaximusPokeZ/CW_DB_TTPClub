package ru.maximuspokez.ttpclub.ttpclub.service.Booking.impl;

import org.springframework.stereotype.Service;
import ru.maximuspokez.ttpclub.ttpclub.model.Booking.Booking;
import ru.maximuspokez.ttpclub.ttpclub.repository.Booking.BookingRepository;
import ru.maximuspokez.ttpclub.ttpclub.service.Booking.BookingService;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

  private final BookingRepository bookingRepository;
  public BookingServiceImpl(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }


  @Override
  public List<Booking> getAllBookings() {
    return bookingRepository.findAll();
  }

  @Override
  public Booking getBooking(Long id) {
    return bookingRepository.findById(id).orElse(null);
  }

  @Override
  public Booking createBooking(Booking booking) {
    return bookingRepository.save(booking);
  }

  @Override
  public Booking updateBooking(Long id, Booking booking) {
    Booking oldBooking = bookingRepository.findById(id).orElse(null);
    if (oldBooking == null) {
      throw new IllegalArgumentException("Booking with ID " + id + " not found");
    }
    return bookingRepository.save(booking);
  }

  @Override
  public void deleteBooking(Long id) {
    Booking oldBooking = bookingRepository.findById(id).orElse(null);
    if (oldBooking == null) {
      throw new IllegalArgumentException("Booking with ID " + id + " not found");
    }
    bookingRepository.delete(oldBooking);
  }

  @Override
  public void updateBookingStatus(Long id, String status) {
    Booking oldBooking = bookingRepository.findById(id).orElse(null);
    if (oldBooking == null) {
      throw new IllegalArgumentException("Booking with ID " + id + " not found");
    }
    oldBooking.setStatus(status);
    bookingRepository.save(oldBooking);
  }
}
