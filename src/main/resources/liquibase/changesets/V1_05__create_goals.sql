create table if not exists goals
(
    id          bigserial primary key,
    description text        not null,
    start_date  DATE        not null,
    end_date    DATE        not null,
    type        varchar(20) not null,
    user_id     bigint      not null,
    foreign key (user_id) references users (id) on delete cascade
);