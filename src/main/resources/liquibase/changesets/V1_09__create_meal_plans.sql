create table if not exists meal_plans
(
    id          bigserial primary key,
    title       varchar(255) not null,
    description text         not null,
    user_id     bigint       references users (id) on delete set null
);