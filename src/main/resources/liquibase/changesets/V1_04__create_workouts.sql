create table if not exists workouts
(
    id         bigserial primary key,
    name       varchar(255),
    duration   bigint,
    difficulty varchar(20)
);