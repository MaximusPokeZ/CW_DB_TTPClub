package ru.maximuspokez.ttpclub.ttpclub.service.Tournament.impl;

import org.springframework.stereotype.Service;
import ru.maximuspokez.ttpclub.ttpclub.model.Tournament.DTO.TournamentDto;
import ru.maximuspokez.ttpclub.ttpclub.model.Tournament.Tournament;
import ru.maximuspokez.ttpclub.ttpclub.repository.Tournament.TournamentRepository;
import ru.maximuspokez.ttpclub.ttpclub.service.Tournament.TournamentsService;

import java.util.List;

@Service
public class TournamentsServiceImpl implements TournamentsService {

  private final TournamentRepository tournamentRepository;

  public TournamentsServiceImpl(TournamentRepository tournamentRepository) {
    this.tournamentRepository = tournamentRepository;
  }

  @Override
  public List<TournamentDto> getTournaments() {
    List<Tournament> tournaments = tournamentRepository.findAll();

    return tournaments.stream().map(this::convertToDto).toList();
  }

  private TournamentDto convertToDto(Tournament tournament) {
    return new TournamentDto(
            tournament.getEventId(),
            tournament.getEvent().getName(),
            tournament.getPrizePool(),
            tournament.isTeamBased(),
            tournament.getMaxParticipantRating(),
            tournament.getEvent().getStartTime(),
            tournament.getEvent().getMaxParticipants()
    );
  }

  @Override
  public Tournament getTournamentById(Long id) {
    return tournamentRepository.findById(id).orElseThrow(()
            -> new IllegalArgumentException("Tournament with ID " + id + " not found"));
  }

  @Override
  public Tournament getTournamentByRating(Integer rating) {
    Tournament tournament =  tournamentRepository.findByMaxParticipantRating(rating);
    if (tournament == null) {
      throw new IllegalArgumentException("Tournament with ID " + rating + " not found");
    }
    return tournament;
  }

  @Override
  public Tournament createTournament(Tournament tournament) {
    return tournamentRepository.save(tournament);
  }

  @Override
  public Tournament updateTournament(Long id, Tournament tournament) {
    Tournament existingTournament = tournamentRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("Tournament with ID " + id + " not found"));

    existingTournament.setPrizePool(tournament.getPrizePool());
    existingTournament.setTeamBased(tournament.isTeamBased());
    existingTournament.setMaxParticipantRating(tournament.getMaxParticipantRating());
    existingTournament.setEvent(tournament.getEvent());

    return tournamentRepository.save(existingTournament);
  }

  @Override
  public void deleteTournament(Long id) {
    if (!tournamentRepository.existsById(id)) {
      throw new IllegalArgumentException("Tournament with ID " + id + " not found");
    }
    tournamentRepository.deleteById(id);
  }

}
