create table if not exists user_favorite_workouts
(
    user_id    bigint not null references users (id) on delete cascade,
    workout_id bigint not null references workouts (id) on delete cascade,
    primary key (user_id, workout_id)
);