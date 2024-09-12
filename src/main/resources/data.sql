drop table if exists app_role cascade;
drop table if exists app_user cascade;
drop table if exists user_role cascade;
drop sequence if exists app_role_seq;
drop sequence if exists app_user_seq;
create sequence APP_USER_seq start with 1 increment by 50;
create sequence APP_ROLE_SEQ start with 1 increment by 50;
create table APP_USER
(
    enabled       boolean      not null,
    id            integer      not null,
    created_at    timestamp(6),
    password      varchar(255) not null,
    username      varchar(50)  not null,
    NATIONAL_CODE varchar(15),
    NAME          varchar(50),
    FAMILY        varchar(100),
    primary key (id)
);
create table APP_ROLE
(
    ID   integer      not null,
    NAME varchar(255) not null,
    primary key (ID)
);
create table USER_ROLE
(
    ROLE_ID integer not null,
    USER_ID integer not null
);
alter table if exists USER_ROLE add constraint AppRoleFk foreign key (ROLE_ID) references APP_ROLE;
alter table if exists USER_ROLE add constraint AppUserFk foreign key (USER_ID) references APP_USER;

INSERT INTO APP_USER (ID, USERNAME, PASSWORD, ENABLED, NATIONAL_CODE, NAME, FAMILY)
VALUES (1, 'mina', '$2a$10$J0agIOHT8ZNHiFPiTHYjjeAFu6CPEAxG67iFRahL0kAuI950BEmZi', 1, '0083747893', 'mina', 'kh');

INSERT INTO APP_ROLE (ID, name) VALUES (1, 'ADMIN');
INSERT INTO APP_ROLE (ID, name) VALUES (2, 'USER');

INSERT INTO USER_ROLE (ROLE_ID, USER_ID) VALUES (1, 1);
INSERT INTO USER_ROLE (ROLE_ID, USER_ID) VALUES (2, 1);

