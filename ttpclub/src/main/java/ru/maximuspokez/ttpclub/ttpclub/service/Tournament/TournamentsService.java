package ru.maximuspokez.ttpclub.ttpclub.service.Tournament;

import ru.maximuspokez.ttpclub.ttpclub.model.Tournament.DTO.TournamentDto;
import ru.maximuspokez.ttpclub.ttpclub.model.Tournament.Tournament;

import java.util.List;

public interface TournamentsService {
  List<TournamentDto> getTournaments();
  Tournament getTournamentById(Long id);
  Tournament getTournamentByRating(Integer rating);
  Tournament createTournament(Tournament tournament);
  Tournament updateTournament(Long id, Tournament tournament);
  void deleteTournament(Long id);
}
