package ru.maximuspokez.ttpclub.ttpclub.model.Tournament;

import jakarta.persistence.*;
import ru.maximuspokez.ttpclub.ttpclub.model.Event.Event;

import java.math.BigDecimal;

@Entity
@Table(name = "tournament")
public class Tournament {

  @Id
  @Column(name = "event_id")
  private Long eventId;

  @Column(name = "prize_pool", nullable = false)
  private BigDecimal prizePool;

  @Column(name = "is_team_based", nullable = false)
  private boolean isTeamBased;

  @Column(name = "max_participant_rating", nullable = false)
  private int maxParticipantRating;

  @OneToOne
  @MapsId
  @JoinColumn(name = "event_id")
  private Event event;

  public Long getEventId() {
    return eventId;
  }

  public void setEventId(Long eventId) {
    this.eventId = eventId;
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

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }
}
