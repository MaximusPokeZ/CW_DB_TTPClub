package ru.maximuspokez.ttpclub.ttpclub.repository.Tournament;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maximuspokez.ttpclub.ttpclub.model.Tournament.Tournament;

@Repository
@Primary
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
  Tournament findByMaxParticipantRating(Integer rating);
}
