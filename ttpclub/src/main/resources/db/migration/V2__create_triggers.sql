insert into equipment (name, type, description, price, quantity)
values 
    ('Table Tennis Table', 'Table', 'Standard table tennis table for professional and amateur play', 500.00, 10),
    ('Basket of Balls', 'Balls', 'Basket containing 50 table tennis balls', 50.00, 10),
    ('Table Tennis Racket', 'Racket', 'High-quality table tennis racket for all levels', 100.00, 20),
    ('Table Tennis Robot', 'Robot', 'Automated robot for training and improving skills', 700.00, 5);
   
   
create or replace function decrease_equipment_quantity() 
returns trigger as $$
begin
    if new.equipment_id is not null then
        update equipment 
        set quantity = quantity - 1
        where id = new.equipment_id;
    end if;

    return new;
end;
$$ language plpgsql;

create trigger trg_decrease_equipment_quantity
after insert on booking
for each row
execute function decrease_equipment_quantity();


create or replace function restore_equipment_quantity() 
returns trigger as $$
begin
    if old.equipment_id is not null then
        update equipment 
        set quantity = quantity + 1
        where id = old.equipment_id;
    end if;

    return old;
end;
$$ language plpgsql;

create trigger trg_restore_equipment_quantity
after delete on booking
for each row
execute function restore_equipment_quantity();