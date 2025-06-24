create table if not exists meal_entries
(
    id           bigserial primary key,
    type         varchar(20) not null,
    recipe_id    bigint      not null references recipes (id) on delete cascade,
    meal_plan_id bigint      not null references meal_plans (id) on delete cascade
);