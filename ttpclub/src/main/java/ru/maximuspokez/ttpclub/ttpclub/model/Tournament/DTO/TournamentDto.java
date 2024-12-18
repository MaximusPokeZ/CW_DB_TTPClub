package ru.maximuspokez.ttpclub.ttpclub.model.Tournament.DTO;

import ru.maximuspokez.ttpclub.ttpclub.model.Event.Event;
import ru.maximuspokez.ttpclub.ttpclub.model.Tournament.Tournament;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TournamentDto {
  private Long eventId;
  private String eventName;
  private BigDecimal prizePool;
  private boolean isTeamBased;
  private int maxParticipantRating;
  private LocalDateTime startTime;
  private Integer maxParticipants;

  public TournamentDto(Long eventId, String eventName, BigDecimal prizePool, boolean isTeamBased,
                       int maxParticipantRating, LocalDateTime startTime, Integer maxParticipants) {
    this.eventId = eventId;
    this.eventName = eventName;
    this.prizePool = prizePool;
    this.isTeamBased = isTeamBased;
    this.maxParticipantRating = maxParticipantRating;
    this.startTime = startTime;
    this.maxParticipants = maxParticipants;
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

  public BigDecimal getPrizePool() {
    return prizePool;
  }

  public void setPrizePool(BigDecimal prizePool) {
    this.prizePool = prizePool;
  }

  public boolean isTeamBased() {
    return isTeamBased;
  }

  public void setTeamBased(boolean teamBased) {
    isTeamBased = teamBased;
  }

  public int getMaxParticipantRating() {
    return maxParticipantRating;
  }

  public void setMaxParticipantRating(int maxParticipantRating) {
    this.maxParticipantRating = maxParticipantRating;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public Integer getMaxParticipants() {
    return maxParticipants;
  }

  public void setMaxParticipants(Integer maxParticipants) {
    this.maxParticipants = maxParticipants;
  }
}