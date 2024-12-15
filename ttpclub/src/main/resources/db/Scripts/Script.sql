create table "user" (
    id bigint generated always as identity primary key, 
    username varchar(50) not null unique,              
    email varchar(100) not null unique,                
    password_hash varchar(255) not null,  -- Хэш пароля
    created_at timestamp default current_timestamp not null,
    role varchar(20) not null check (role in ('player', 'coach', 'admin')), -- Роль пользователя
    rating integer default 0 not null  
);

create table UserRating (
    id bigint generated always as identity primary key,   
    user_id bigint not null,                              
    rating integer not null,                             
    rating_date timestamp default current_timestamp not null,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user"(id) on delete cascade
);


----------------------------------------------------------------------

create table Event (
    id bigint generated always as identity primary key, 
    name varchar(100) not null,                       
    type varchar(50) not null check (type in ('training', 'tournament')),                      
    description text null,                           
    start_time timestamp not null,                   
    end_time timestamp not null,                                         
    max_participants integer not null,               
    current_participants integer default 0 not null
);

create table Tournament (
    event_id bigint primary key,                         
    prize_pool numeric(10, 2) not null,                  
    is_team_based boolean default false not null,        
    max_participant_rating integer not null, 
    
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES Event(id) on delete cascade
);

create table Training (
    event_id bigint primary key,                        
    coach_id bigint not null,                            
    training_type varchar(50) not null,                  
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES Event(id) on delete cascade,
    CONSTRAINT fk_coach FOREIGN KEY (coach_id) REFERENCES "user"(id) on delete cascade
);


create table Equipment (
    id bigint generated always as identity primary key, 
    name varchar(100) not null,                        
    type varchar(50) not null,                        
    description text null,                            
    price numeric(10, 2) not null,                    
    quantity integer not null,                        
    created_at timestamp default current_timestamp not null 
);

create table Booking (
    id bigint generated always as identity primary key,
    user_id bigint not null,                                                        
    equipment_id bigint null,                        
    booking_date timestamp default current_timestamp not null, 
    start_time timestamp not null,                    
    end_time timestamp not null,                      
    status varchar(20) default 'pending' not null,   
    
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user"(id) on delete cascade,
    CONSTRAINT fk_equipment FOREIGN KEY (equipment_id) REFERENCES Equipment(id) on delete set null
);

create table Registration (
    id bigint generated always as identity primary key,
    user_id bigint not null,                           
    event_id bigint not null,                          
    registration_date timestamp default current_timestamp not null,
    status varchar(20) default 'pending' not null,
    
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user"(id) on delete cascade,
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES Event(id) on delete cascade
);

----------------------------------------------------------------------



