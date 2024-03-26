--liquibase formatted sql

--changeset pers:1
create TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    login    VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255)       NOT NULL,
    role     VARCHAR(20)        NOT NULL
);
--rollback DROP TABLE users;

--changeset pers:2
create TABLE IF NOT EXISTS client
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    first_name   VARCHAR(128)                                      NOT NULL,
    last_name    VARCHAR(128)                                      NOT NULL,
    phone        VARCHAR(20)                                UNIQUE NOT NULL,
    balance      NUMERIC(10, 2)                                    NOT NULL,
    created_time TIMESTAMP                                         NOT NULL,
    status       VARCHAR(56)                                       NOT NULL
);
--rollback DROP TABLE client;

--changeset pers:3

create TABLE IF NOT EXISTS card
(
    id BIGINT DEFAULT nextval('card_id_seq') PRIMARY KEY,
    client_id    BIGINT REFERENCES client (id) ON DELETE CASCADE NOT NULL,
    balance      NUMERIC(10, 2)                                     NOT NULL,
    created_date DATE                                               NOT NULL,
    expire_date  DATE                                               NOT NULL,
    status       VARCHAR(56)                                        NOT NULL
);

ALTER SEQUENCE card_id_seq RESTART WITH 100000 INCREMENT BY 56909;
--rollback DROP TABLE card;

--changeset pers:4
create TABLE IF NOT EXISTS payment
(
    id               BIGSERIAL PRIMARY KEY,
    shop_name        VARCHAR(128)                                       NOT NULL,
    amount           NUMERIC(10, 2)                                     NOT NULL,
    pay_by_client_id BIGINT REFERENCES client (id) ON DELETE CASCADE NOT NULL,
    pay_by_card_no   BIGINT REFERENCES card (id)                        NOT NULL,
    time_of_pay      TIMESTAMP                                          NOT NULL,
    status           VARCHAR(20)                                        NOT NULL
);
--rollback DROP TABLE payment;

--changeset pers:5
create TABLE IF NOT EXISTS transfer
(
    id               BIGSERIAL PRIMARY KEY,
    card_no_from     BIGINT REFERENCES card (id) NOT NULL,
    card_no_to       BIGINT REFERENCES card (id) NOT NULL,
    amount           NUMERIC(10, 2)              NOT NULL,
    recipient        VARCHAR(120)                NOT NULL,
    message          VARCHAR(120),
    time_of_transfer TIMESTAMP                   NOT NULL,
    status           VARCHAR(20)                 NOT NULL
);
--rollback DROP TABLE transfer;

--changeset pers:6
create TABLE IF NOT EXISTS replenishment
(
    id                    BIGSERIAL PRIMARY KEY,
    client_to             BIGINT REFERENCES client (id) ON DELETE CASCADE NOT NULL,
    card_no_to            BIGINT REFERENCES card (id)                        NOT NULL,
    amount                NUMERIC(10, 2)                                     NOT NULL,
    time_of_replenishment TIMESTAMP                                          NOT NULL,
    status                VARCHAR(20)                                        NOT NULL
);
--rollback DROP TABLE replenishment;




