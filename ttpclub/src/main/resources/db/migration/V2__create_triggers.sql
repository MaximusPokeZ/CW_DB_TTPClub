create or replace function insert_into_tournament_or_training()
returns trigger as $$
begin
  if (new.type = 'TOURNAMENT') then
    insert into tournament (event_id, prize_pool, is_team_based, max_participant_rating)
    values (new.id, 0.0, false, 0);

  elsif (new.type = 'TRAINING') then
    insert into training (event_id, coach_id, training_type)
    values (new.id, null, 'GENERAL');
  end if;

  return new;
end;
$$ language plpgsql;


create trigger after_event_insert
after insert on "event"
for each row
execute function insert_into_tournament_or_training();
