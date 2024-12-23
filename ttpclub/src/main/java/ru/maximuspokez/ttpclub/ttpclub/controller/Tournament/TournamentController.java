package ru.maximuspokez.ttpclub.ttpclub.controller.Tournament;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maximuspokez.ttpclub.ttpclub.model.Tournament.DTO.TournamentDto;
import ru.maximuspokez.ttpclub.ttpclub.model.Tournament.Tournament;
import ru.maximuspokez.ttpclub.ttpclub.service.Tournament.TournamentsService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tournaments")
public class TournamentController {

  private final TournamentsService tournamentsService;

  public TournamentController(TournamentsService tournamentsService) {
    this.tournamentsService = tournamentsService;
  }

  @GetMapping
  public ResponseEntity<List<TournamentDto>> findAll() {
    List<TournamentDto> tournaments = tournamentsService.getTournaments();
    List<TournamentDto> mutableTournaments = new ArrayList<>(tournaments);
    mutableTournaments.sort(Comparator.comparing(TournamentDto::getStartTime));
    return ResponseEntity.ok(mutableTournaments);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Tournament> findById(@PathVariable Long id) {
    Tournament tournament = tournamentsService.getTournamentById(id);
    return ResponseEntity.ok(tournament);
  }

  @GetMapping("get_rating/{rating}")
  public ResponseEntity<Tournament> getRating(@PathVariable Integer rating) {
    Tournament tournament = tournamentsService.getTournamentByRating(rating);
    return ResponseEntity.ok(tournament);
  }

  @PostMapping
  public ResponseEntity<Tournament> create(@RequestBody Tournament tournament) {
    Tournament createdTournament = tournamentsService.createTournament(tournament);
    return ResponseEntity.status(201).body(createdTournament);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Tournament> update(@PathVariable Long id, @RequestBody Tournament tournament) {
    Tournament updatedTournament = tournamentsService.updateTournament(id, tournament);
    return ResponseEntity.ok(updatedTournament);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    tournamentsService.deleteTournament(id);
  }
}
