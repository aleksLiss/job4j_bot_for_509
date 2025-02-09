create table users(
    id serial primary key,
    client_id int not null unique,
    first_name varchar not null unique,
    last_name varchar not null unique
);