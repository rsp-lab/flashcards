DROP TABLE IF EXISTS flashcards CASCADE;
CREATE TABLE flashcards
(
    id      BIGSERIAL primary key,
    first   varchar,
    second  varchar
);