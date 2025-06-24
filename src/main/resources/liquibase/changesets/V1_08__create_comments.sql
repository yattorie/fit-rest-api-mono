create table if not exists comments
(
    id         bigserial primary key,
    content    text   not null,
    created_at timestamp default current_timestamp,
    author_id  bigint not null,
    article_id bigint not null,
    foreign key (author_id) references users (id) on delete cascade,
    foreign key (article_id) references articles (id) on delete cascade
);

