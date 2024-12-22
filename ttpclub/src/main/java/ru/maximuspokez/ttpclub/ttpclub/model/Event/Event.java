package ru.maximuspokez.ttpclub.ttpclub.model.Event;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "event")
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private EventType type;

  private String description;

  @Column(name = "start_time")
  private LocalDateTime startTime;

  @Column(name = "end_time")
  private LocalDateTime endTime;

  @Column(name = "max_participants")
  private Integer maxParticipants;

  public enum EventType {
    TRAINING,
    TOURNAMENT
  }


  public Event() {}
  public Event (String name, EventType type, LocalDateTime startTime, LocalDateTime endTime, String location, Integer maxParticipants) {
    this.name = name;
    this.type = type;
    this.startTime = startTime;
    this.endTime = endTime;
    this.maxParticipants = maxParticipants;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public EventType getType() {
    return type;
  }

  public void setType(EventType type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public Integer getMaxParticipants() {
    return maxParticipants;
  }

  public void setMaxParticipants(Integer maxParticipants) {
    this.maxParticipants = maxParticipants;
  }


  @Override
  public String toString() {
    return "Event{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", maxParticipants=" + maxParticipants +
            '}';
  }
}
