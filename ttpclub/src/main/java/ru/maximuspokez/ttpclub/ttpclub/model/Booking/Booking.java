package ru.maximuspokez.ttpclub.ttpclub.model.Booking;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "equipment_id", nullable = false)
  private Long equipmentId;

  @Column(name = "booking_date", nullable = false, updatable = false)
  private LocalDateTime bookingDate = LocalDateTime.now();

  @Column(name = "start_time", nullable = false)
  private LocalDateTime startTime;

  @Column(name = "end_time", nullable = false)
  private LocalDateTime endTime;

  @Column(name = "status", nullable = false)
  private String status = "pending";

  public Booking() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getEquipmentId() {
    return equipmentId;
  }

  public void setEquipmentId(Long equipmentId) {
    this.equipmentId = equipmentId;
  }

  public LocalDateTime getBookingDate() {
    return bookingDate;
  }

  public void setBookingDate(LocalDateTime bookingDate) {
    this.bookingDate = bookingDate;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
