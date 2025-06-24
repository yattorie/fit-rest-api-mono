create table if not exists users
(
    id         bigserial primary key,
    username   varchar(255) not null unique,
    email      varchar(255) not null unique,
    password   varchar(255) not null,
    role       varchar(20)  not null,
    weight     double precision,
    height     double precision,
    birth_date DATE,
    gender     varchar(10)
);