package ru.maximuspokez.ttpclub.ttpclub.service.Training.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.maximuspokez.ttpclub.ttpclub.model.Event.Event;
import ru.maximuspokez.ttpclub.ttpclub.model.Tournament.DTO.TournamentDto;
import ru.maximuspokez.ttpclub.ttpclub.model.Tournament.Tournament;
import ru.maximuspokez.ttpclub.ttpclub.model.Training.DTO.TrainingDTO;
import ru.maximuspokez.ttpclub.ttpclub.model.Training.Training;
import ru.maximuspokez.ttpclub.ttpclub.repository.Event.EventRepository;
import ru.maximuspokez.ttpclub.ttpclub.repository.Tournament.TournamentRepository;
import ru.maximuspokez.ttpclub.ttpclub.repository.Training.TrainingRepository;
import ru.maximuspokez.ttpclub.ttpclub.repository.User.UserRepository;
import ru.maximuspokez.ttpclub.ttpclub.service.Training.TrainingService;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {

  private final TrainingRepository trainingRepository;

  private final EventRepository eventRepository;

  private final UserRepository userRepository;

  public TrainingServiceImpl(TrainingRepository trainingRepository, EventRepository eventRepository, UserRepository userRepository) {
    this.trainingRepository = trainingRepository;
    this.eventRepository = eventRepository;
    this.userRepository = userRepository;
  }

  @Override
  public List<TrainingDTO> getTrainings() {
    List<Training> trainings = trainingRepository.findAll();

    return trainings.stream().map(this::convertToDto).toList();
  }

  private TrainingDTO convertToDto(Training training) {
    Event event = eventRepository.findById(training.getEventId()).get();
    return new TrainingDTO(
            training.getEventId(),
            event.getName(),
            event.getStartTime(),
            event.getEndTime(),
            event.getMaxParticipants(),
            training.getTrainingType().toString(),
            userRepository.findById(training.getCoachId()).get().getUsername()
    );
  }

  @Override
  public TrainingDTO getTraining(Long id) {
    Training training =  trainingRepository.findById(id).orElseThrow(()
            -> new IllegalArgumentException("Training with ID " + id + " not found"));
    return convertToDto(training);
  }

  @Override
  public TrainingDTO getTrainingByTrainingType(Training.TrainingType trainingType) {
    Training training =  trainingRepository.findByTrainingType(trainingType);
    if (training == null) {
      throw new IllegalArgumentException("Training with trainingType " + trainingType + " not found");
    }
    return convertToDto(training);
  }

  @Override
  public TrainingDTO getTrainingByCoachId(Long id) {
    Training training =  trainingRepository.findByCoachId(id);
    if (training == null) {
      throw new IllegalArgumentException("Training with coach " + id + " not found");
    }
    return convertToDto(training);
  }

  @Override
  public TrainingDTO createTraining(Training training) {
    Event event = eventRepository.findById(training.getEventId())
            .orElseThrow(() -> new EntityNotFoundException("Event not found"));
    return convertToDto(trainingRepository.save(training));
  }

  @Override
  public TrainingDTO updateTraining(Long id, Training training) {
    Training existingTraining = trainingRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("Training with ID " + id + " not found"));

    existingTraining.setTrainingType(training.getTrainingType());
    existingTraining.setCoachId(training.getCoachId());

    return convertToDto(trainingRepository.save(training));
  }

  @Override
  public void deleteTraining(Long id) {
    if (!trainingRepository.existsById(id)) {
      throw new IllegalArgumentException("Training with ID " + id + " not found");
    }
    trainingRepository.deleteById(id);
  }
}
