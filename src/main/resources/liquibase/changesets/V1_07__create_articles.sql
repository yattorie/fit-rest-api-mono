create table if not exists articles
(
    id          bigserial primary key,
    title       varchar(255) not null,
    image_url   varchar(255),
    content     text         not null,
    created_at  timestamp    not null,
    author_id   bigint       not null references users (id) on delete cascade,
    category_id bigint       not null references categories (id) on delete cascade
);