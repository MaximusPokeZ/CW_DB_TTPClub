package ru.maximuspokez.ttpclub.ttpclub.model.Training;

import jakarta.persistence.*;

@Entity
@Table(name = "training")
public class Training {

  @Id
  @Column(name = "event_id")
  private Long eventId;

  @Column(name = "coach_id", nullable = false)
  private Long coachId;

  @Column(name = "training_type", nullable = false, length = 50)
  @Enumerated(EnumType.STRING)
  private TrainingType trainingType;

  public Long getEventId() {
    return eventId;
  }

  public void setEventId(Long eventId) {
    this.eventId = eventId;
  }

  public Long getCoachId() {
    return coachId;
  }

  public void setCoachId(Long coachId) {
    this.coachId = coachId;
  }

  public TrainingType getTrainingType() {
    return trainingType;
  }

  public void setTrainingType(TrainingType trainingType) {
    this.trainingType = trainingType;
  }

  public Training() {}

  public enum TrainingType {
    INDIVIDUAL,
    GROUP
  }

  @Override
  public String toString() {
    return "Training{" +
            "eventId=" + eventId +
            ", coachId=" + coachId +
            ", trainingType='" + trainingType + '\'' +
            '}';
  }

}