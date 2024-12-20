package ru.maximuspokez.ttpclub.ttpclub.service.Tournament.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maximuspokez.ttpclub.ttpclub.model.Event.Event;
import ru.maximuspokez.ttpclub.ttpclub.model.Tournament.DTO.TournamentDto;
import ru.maximuspokez.ttpclub.ttpclub.model.Tournament.Tournament;
import ru.maximuspokez.ttpclub.ttpclub.repository.Event.EventRepository;
import ru.maximuspokez.ttpclub.ttpclub.repository.Tournament.TournamentRepository;
import ru.maximuspokez.ttpclub.ttpclub.service.Tournament.TournamentsService;

import java.util.List;

@Service
public class TournamentsServiceImpl implements TournamentsService {

  private final TournamentRepository tournamentRepository;

  private final EventRepository eventRepository;

  public TournamentsServiceImpl(TournamentRepository tournamentRepository, EventRepository eventRepository) {
    this.tournamentRepository = tournamentRepository;
    this.eventRepository = eventRepository;
  }

  @Override
  public List<TournamentDto> getTournaments() {
    List<Tournament> tournaments = tournamentRepository.findAll();

    return tournaments.stream().map(this::convertToDto).toList();
  }

  private TournamentDto convertToDto(Tournament tournament) {
    return new TournamentDto(
            tournament.getEventId(),
            eventRepository.findById(tournament.getEventId()).get().getName(),
            tournament.getPrizePool(),
            tournament.isTeamBased(),
            tournament.getMaxParticipantRating(),
            eventRepository.findById(tournament.getEventId()).get().getStartTime(),
            eventRepository.findById(tournament.getEventId()).get().getMaxParticipants()
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

  @Transactional
  @Override
  public Tournament createTournament(Tournament tournament) {
    Event event = eventRepository.findById(tournament.getEventId())
            .orElseThrow(() -> new EntityNotFoundException("Event not found"));
    return tournamentRepository.save(tournament);
  }

  @Override
  public Tournament updateTournament(Long id, Tournament tournament) {
    Tournament existingTournament = tournamentRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("Tournament with ID " + id + " not found"));

    existingTournament.setPrizePool(tournament.getPrizePool());
    existingTournament.setTeamBased(tournament.isTeamBased());
    existingTournament.setMaxParticipantRating(tournament.getMaxParticipantRating());

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
