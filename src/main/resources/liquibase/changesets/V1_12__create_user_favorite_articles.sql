create table if not exists user_favorite_articles
(
    user_id    bigint not null references users (id) on delete cascade,
    article_id bigint not null references articles (id) on delete cascade,
    primary key (user_id, article_id)
);