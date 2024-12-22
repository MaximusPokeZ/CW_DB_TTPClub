package ru.maximuspokez.ttpclub.ttpclub.controller.Training;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maximuspokez.ttpclub.ttpclub.model.Tournament.Tournament;
import ru.maximuspokez.ttpclub.ttpclub.model.Training.DTO.TrainingDTO;
import ru.maximuspokez.ttpclub.ttpclub.model.Training.Training;
import ru.maximuspokez.ttpclub.ttpclub.service.Training.TrainingService;

import java.util.List;

@RestController
@RequestMapping("api/v1/trainings")
public class TrainingController {

  private final TrainingService trainingService;
  public TrainingController(TrainingService trainingService) {
    this.trainingService = trainingService;
  }

  @GetMapping
  public ResponseEntity<List<TrainingDTO>> getTrainings() {
    List<TrainingDTO> trainings = trainingService.getTrainings();
    return ResponseEntity.ok(trainings);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TrainingDTO> findById(@PathVariable Long id) {
    TrainingDTO training = trainingService.getTraining(id);
    return ResponseEntity.ok(training);
  }

  @GetMapping("/coach_id/{id}")
  public ResponseEntity<TrainingDTO> findByCoachId(@PathVariable Long id) {
    TrainingDTO training = trainingService.getTraining(id);
    return ResponseEntity.ok(training);
  }

  @PostMapping
  public ResponseEntity<TrainingDTO> create(@RequestBody Training training) {
    TrainingDTO trainingDTO = trainingService.createTraining(training);
    return ResponseEntity.ok(trainingDTO);
  }

  @PutMapping("{id}")
  public ResponseEntity<TrainingDTO> update(@PathVariable Long id, @RequestBody Training training) {
    TrainingDTO updateTraining = trainingService.updateTraining(id, training);
    return ResponseEntity.ok(updateTraining);
  }

  @DeleteMapping("{id}")
  public void delete(@PathVariable Long id) {
    trainingService.deleteTraining(id);
  }

}
