package ru.maximuspokez.ttpclub.ttpclub.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class Event {
  private Long id;
  private String name;
  private String type;
  private String description;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Integer maxParticipants;
  private Integer currentParticipants = 0;

  public Event() {}
  public Event (String name, String type, LocalDateTime startTime, LocalDateTime endTime, String location, Integer maxParticipants) {
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
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

  public Integer getCurrentParticipants() {
    return currentParticipants;
  }

  public void setCurrentParticipants(Integer currentParticipants) {
    this.currentParticipants = currentParticipants;
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
            ", currentParticipants=" + currentParticipants +
            '}';
  }
}
