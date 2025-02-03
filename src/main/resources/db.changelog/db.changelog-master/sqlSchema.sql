create extension if not exists "uuid-ossp";

create schema users;

create table users.photos (
    id          uuid default uuid_generate_v1() not null,
    filename    text,
    primary key (id)
);

create table users.contacts (
    id        uuid default uuid_generate_v1() not null,
    email     text not null,
    number    text not null,
    primary key (id)
);

create table users.details (
    id             uuid references users.contacts(id) on delete cascade,
    surname        text,
    name           text,
    second_name    text,
    birth_date     date,
    photo_id       uuid references users.photos(id) on delete cascade,
    primary key (id)
);

--Для тестирования в POSTMAN
insert into users.contacts values ('f17918e2-e18a-11ef-9bd3-0242ac180002','example@exaple.com', '+7(777)-777-77-77');
insert into users.details values ('f17918e2-e18a-11ef-9bd3-0242ac180002',
 'Иванов', 'Иван', 'Иванович', '1999-01-01', null);
