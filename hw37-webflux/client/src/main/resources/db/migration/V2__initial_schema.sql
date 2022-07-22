create table adress
(
    id bigserial not null primary key,
    street varchar(50)
);

create table client
(
    id  bigserial not null primary key,
    name varchar(50),
    adress_id bigint,
    FOREIGN KEY (adress_id) REFERENCES adress (Id)
);

create table phone
(
    id  bigserial not null primary key,
    client_id bigint,
    number varchar(50),
    FOREIGN KEY (client_id) REFERENCES client (Id)
);
