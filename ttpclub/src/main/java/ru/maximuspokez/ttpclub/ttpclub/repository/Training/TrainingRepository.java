package ru.maximuspokez.ttpclub.ttpclub.repository.Training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maximuspokez.ttpclub.ttpclub.model.Training.Training;


@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
  Training findByTrainingType(Training.TrainingType trainingType);
  Training findByCoachId(Long id);
}
