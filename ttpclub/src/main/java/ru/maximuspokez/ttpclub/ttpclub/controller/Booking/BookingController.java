package ru.maximuspokez.ttpclub.ttpclub.controller.Booking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maximuspokez.ttpclub.ttpclub.model.Booking.Booking;
import ru.maximuspokez.ttpclub.ttpclub.service.Booking.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

  private final BookingService bookingService;

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping
  public ResponseEntity<List<Booking>> getBookings() {
    List<Booking> bookings = bookingService.getAllBookings();
    return ResponseEntity.ok(bookings);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
    Booking booking = bookingService.getBooking(id);
    if (booking == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(booking);
  }

  @PostMapping
  public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
    Booking createdBooking = bookingService.createBooking(booking);
    return ResponseEntity.ok(createdBooking);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
    Booking updatedBooking = bookingService.updateBooking(id, booking);
    return ResponseEntity.ok(updatedBooking);
  }

  @DeleteMapping("/{id}")
  public void deleteBooking(@PathVariable Long id) {
    bookingService.deleteBooking(id);
  }

}
