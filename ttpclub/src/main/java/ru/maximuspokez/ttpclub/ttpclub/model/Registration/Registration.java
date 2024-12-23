package ru.maximuspokez.ttpclub.ttpclub.model.Registration;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;

@Entity
@Table(name = "registration")
public class Registration {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "event_id", nullable = false)
  private Long eventId;

  @Column(name = "registration_date", nullable = false, updatable = false)
  private LocalDateTime registrationDate;

  @Column(name = "status", nullable = false)
  private String status = "pending";

  public Registration() {}

  public Registration(Long userId, Long eventId, LocalDateTime registrationDate, String status) {
    this.userId = userId;
    this.eventId = eventId;
    this.registrationDate = registrationDate;
    this.status = status;
  }

  @PrePersist
  protected void onCreate() {
    this.registrationDate = LocalDateTime.now();
  }

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

  public Long getEventId() {
    return eventId;
  }

  public void setEventId(Long eventId) {
    this.eventId = eventId;
  }

  public LocalDateTime getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(LocalDateTime registrationDate) {
    this.registrationDate = registrationDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
