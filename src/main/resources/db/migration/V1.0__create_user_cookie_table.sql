CREATE TABLE user
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    cookie_value VARCHAR(256) NOT NULL,
    user_name    VARCHAR(32)  NOT NULL,
    issued_at    DATETIME     NOT NULL,
    created_at   DATETIME     NOT NULL,
    updated_at   DATETIME     NOT NULL,
    UNIQUE (cookie_value)
);