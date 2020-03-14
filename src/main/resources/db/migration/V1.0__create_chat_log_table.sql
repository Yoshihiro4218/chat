CREATE TABLE chat_log
(
    id      BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    message VARCHAR(1024)
);