create table if not exists weight_entries
(
    id      bigserial primary key,
    date    DATE,
    value   double precision,
    user_id bigint not null,
    foreign key (user_id) references users (id) on delete cascade
);