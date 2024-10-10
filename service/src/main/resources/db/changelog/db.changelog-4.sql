--liquibase formatted sql

--changeset pers:1
INSERT INTO users (login, password, role)
values ('admin@m', '{noop}123', 'SUPER_ADMIN'),
       ('user@m', '{noop}123', 'USER');
--rollback DROP TABLE users;

--changeset pers:2
INSERT INTO client (user_id, first_name, last_name, phone, created_time, status)
values (2, 'Petr', 'Petrov', '89774957733', '2024-10-10T08:50:47', 'ACTIVE');
--rollback DROP TABLE client;






