create table if not exists recipes
(
    id                bigserial primary key,
    title             varchar(255)     not null,
    image_url         varchar(255),
    description       text,
    instructions      text,
    cook_time_minutes integer          not null,
    calories          integer          not null,
    protein           double precision not null,
    fat               double precision not null,
    carbs             double precision not null
);