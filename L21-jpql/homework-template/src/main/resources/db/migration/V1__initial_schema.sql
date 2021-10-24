-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

/*
-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence hibernate_sequence start with 1 increment by 1;

create table client
(
    id   bigint not null primary key,
    name varchar(50),
    adress_id bigint,
    phone_id bigint,
    FOREIGN KEY (adress_id) REFERENCES adress (Id)
);
*/
