drop table if exists app_role cascade;
drop table if exists app_user cascade;
drop table if exists category cascade;
drop table if exists expense cascade;
drop table if exists user_role cascade;
drop table if exists user_sub_category cascade;
drop sequence if exists app_role_seq;
drop sequence if exists app_user_seq;
drop sequence if exists category_seq;
drop sequence if exists expense_seq;
drop sequence if exists user_sub_category_seq;
create sequence app_role_seq start with 10 increment by 50;
create sequence app_user_seq start with 10 increment by 50;
create sequence category_seq start with 10 increment by 50;
create sequence expense_seq start with 10 increment by 50;
create sequence user_sub_category_seq start with 10 increment by 50;

create table INT_LOCK
(
    LOCK_KEY     char(255),
    REGION       varchar(255),
    CLIENT_ID    char(36),
    CREATED_DATE TIMESTAMP NOT NULL,
    constraint INT_LOCK_PK primary key (LOCK_KEY, REGION)
);

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
    primary key (id),
    constraint UK_USER_1 unique (NATIONAL_CODE),
    constraint UK_USER_2 unique (username)
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


create table CATEGORY
(
    ID   integer     not null,
    NAME varchar(50) not null,
    primary key (ID),
    constraint UK_CATEGORY_1 unique (NAME)
);
create table USER_SUB_CATEGORY
(
    CATEGORY_ID  integer      not null,
    ID           integer      not null,
    USER_ID      integer      not null,
    SUB_CATEGORY varchar(100) not null,
    primary key (ID),
    constraint UK_SUBCATEGORY_1 unique (USER_ID, CATEGORY_ID, SUB_CATEGORY)
);
create table EXPENSE
(
    AMOUNT      numeric(38, 2) not null,
    ID          integer        not null,
    SUB_CAT_ID  integer        not null,
    DESCRIPTION varchar(255)   not null,
    primary key (ID)
);
alter table if exists EXPENSE add constraint FK_SUB_CAT foreign key (SUB_CAT_ID) references USER_SUB_CATEGORY;
alter table if exists USER_SUB_CATEGORY add constraint FK_CATEGORY foreign key (CATEGORY_ID) references CATEGORY;
alter table if exists USER_SUB_CATEGORY add constraint FK_USER foreign key (USER_ID) references APP_USER;

--initial data
INSERT INTO APP_USER (ID, USERNAME, PASSWORD, ENABLED, NATIONAL_CODE, NAME, FAMILY)
VALUES (1, 'mina', '$2a$10$J0agIOHT8ZNHiFPiTHYjjeAFu6CPEAxG67iFRahL0kAuI950BEmZi', 1, '0083747893', 'mina', 'kh');

INSERT INTO APP_ROLE (ID, name)
VALUES (1, 'ADMIN');
INSERT INTO APP_ROLE (ID, name)
VALUES (2, 'USER');

INSERT INTO USER_ROLE (ROLE_ID, USER_ID)
VALUES (1, 1);
INSERT INTO USER_ROLE (ROLE_ID, USER_ID)
VALUES (2, 1);

INSERT INTO CATEGORY (ID, NAME)
VALUES (1, 'FOOD');
INSERT INTO CATEGORY (ID, NAME)
VALUES (2, 'HEALTH');
INSERT INTO CATEGORY (ID, NAME)
VALUES (3, 'ENTERTAINMENT');