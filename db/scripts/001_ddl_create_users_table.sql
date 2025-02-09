create table users(
    id serial primary key,
    first_name varchar not null unique,
    last_name varchar not null unique
);