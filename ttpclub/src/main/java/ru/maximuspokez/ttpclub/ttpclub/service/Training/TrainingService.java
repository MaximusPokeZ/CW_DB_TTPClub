package ru.maximuspokez.ttpclub.ttpclub.service.Training;

import ru.maximuspokez.ttpclub.ttpclub.model.Training.DTO.TrainingDTO;
import ru.maximuspokez.ttpclub.ttpclub.model.Training.Training;

import java.util.List;

public interface TrainingService {
  List<TrainingDTO> getTrainings();
  TrainingDTO getTraining(Long id);
  TrainingDTO getTrainingByTrainingType(Training.TrainingType trainingType);
  TrainingDTO getTrainingByCoachId(Long id);
  TrainingDTO createTraining(Training training);
  TrainingDTO updateTraining(Long id, Training training);
  void deleteTraining(Long id);
}
