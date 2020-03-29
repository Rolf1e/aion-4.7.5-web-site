create table users
(
    id serial not null,
    username varchar(255) not null,
    password varchar(255) not null,
    token varchar(255) not null
);

create unique index users_id_uindex
    on users (id);

alter table users
    add constraint users_pk
        primary key (id);

