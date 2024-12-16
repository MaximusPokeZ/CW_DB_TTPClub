create or replace function update_age()
returns trigger as $$
begin
    new.age := extract(year from age(current_date, new.birth_date));
    return new;
end;
$$ language plpgsql;;

create or replace trigger update_age_trigger
before insert or update of birth_date
on "user"
for each row
execute function update_age();;

