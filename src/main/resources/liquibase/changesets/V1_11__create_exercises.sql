create table if not exists exercises
(
    id         bigserial primary key,
    name       varchar(255) not null,
    sets       integer      not null,
    reps       integer      not null,
    workout_id bigint       not null references workouts (id) on delete cascade
);