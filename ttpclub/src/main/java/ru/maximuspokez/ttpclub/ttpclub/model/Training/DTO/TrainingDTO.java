package ru.maximuspokez.ttpclub.ttpclub.model.Training.DTO;

import java.time.LocalDateTime;

public class TrainingDTO {
  private Long eventId;
  private String eventName;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Integer maxParticipants;
  private String type;

  public TrainingDTO(Long eventId, String eventName, LocalDateTime startTime,  LocalDateTime endTime, Integer maxParticipants, String type) {
    this.eventId = eventId;
    this.eventName = eventName;
    this.startTime = startTime;
    this.endTime = endTime;
    this.maxParticipants = maxParticipants;
    this.type = type;
  }

  public Long getEventId() {
    return eventId;
  }

  public void setEventId(Long eventId) {
    this.eventId = eventId;
  }

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
