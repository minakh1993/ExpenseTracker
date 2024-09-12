drop table if exists expense_user cascade;
drop sequence if exists expense_user_seq;
create sequence expense_user_seq start with 1 increment by 50;
create table expense_user
(
    enabled       boolean      not null,
    id            integer      not null,
    created_at    timestamp(6),
    password      varchar(255) not null,
    username      varchar(50) not null,
    NATIONAL_CODE varchar(15),
    NAME          varchar(50),
    FAMILY        varchar(100),
    primary key (id)
);
INSERT INTO EXPENSE_USER (ID, USERNAME, PASSWORD, ENABLED, NATIONAL_CODE, NAME, FAMILY)
VALUES (1, 'mina', '$2a$10$J0agIOHT8ZNHiFPiTHYjjeAFu6CPEAxG67iFRahL0kAuI950BEmZi', 1, '0083747893', 'mina', 'kh');