create table if not exists user_favorite_recipes
(
    user_id   bigint not null references users (id) on delete cascade,
    recipe_id bigint not null references recipes (id) on delete cascade,
    primary key (user_id, recipe_id)
);