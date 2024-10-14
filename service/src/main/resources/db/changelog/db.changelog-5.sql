--liquibase formatted sql

--changeset pers:1
INSERT INTO users (login, password, role)
values
       ('user1@m', '{noop}123', 'USER'),
       ('user2@m', '{noop}123', 'USER');
--rollback DROP TABLE users;

--changeset pers:2
INSERT INTO client (user_id, first_name, last_name, phone, created_time, status)
values  (3, 'Женек', 'Адванта', '89774957735', '2024-10-10T08:50:47', 'ACTIVE'),
 (4, 'Серега', 'Месси', '89774957736', '2024-10-10T08:50:47', 'ACTIVE');
--rollback DROP TABLE client;

--changeset pers:3
INSERT INTO card (client_id, balance, created_date, expire_date, status)
 values (1, 15000, '2024-10-10', '2026-10-10', 'ACTIVE'),
 (1, 400, '2022-10-10', '2027-05-10', 'ACTIVE'),
 (1, 0, '2022-10-10', '2027-05-10', 'ACTIVE'),
 (2, 5000, '2022-10-10', '2027-05-10', 'ACTIVE'),
 (3, 7000, '2021-10-10', '2026-05-10', 'ACTIVE');
 --rollback DROP TABLE card;

--changeset pers:4
INSERT INTO payment (shop_name, amount, pay_by_client_id, pay_by_card_no, time_of_pay, status)
 values  ('Vkusvill', 527, 1, 100000, '2027-05-10', 'SUCCESS'),
 ('Nike', 167, 2, 156909, '2027-05-10', 'SUCCESS'),
 ('It courses', 189, 1, 156909, '2027-05-10', 'SUCCESS'),
 ('Mobile', 656, 1, 100000, '2027-05-10', 'SUCCESS'),
 ('Wildberies', 279, 3, 213818, '2027-05-10', 'SUCCESS'),
 ('Wildberies', 420, 1, 156909, '2027-05-10', 'FAILED'),
 ('vk music', 728, 1, 100000, '2027-05-10', 'SUCCESS'),
 ('Nike', 292, 2, 156909, '2027-05-10', 'SUCCESS'),
 ('Adidas', 422, 1, 213818, '2027-05-10', 'SUCCESS'),
 ('Adidas', 243, 2, 156909, '2027-05-10', 'SUCCESS'),
 ('vk music', 488, 2, 156909, '2027-05-10', 'SUCCESS'),
 ('Ozon', 672, 1, 100000, '2027-05-10', 'SUCCESS'),
 ('LaModa', 563, 3, 213818, '2027-05-10', 'SUCCESS'),
 ('It courses', 579, 3, 100000, '2027-05-10', 'SUCCESS'),
 ('Mobile', 731, 3, 213818, '2027-05-10', 'SUCCESS');
 --rollback DROP TABLE payment;
