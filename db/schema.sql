create table if not exists accounts (
    id serial primary key,
    name text,
    phone text,
    seat integer
);

create table if not exists hall (
    id serial primary key,
    client integer references accounts(id),
    occupied integer
);

insert into hall(id, occupied) values (11, 0);
insert into hall(id, occupied) values (12, 0);
insert into hall(id, occupied) values (13, 0);
insert into hall(id, occupied) values (14, 0);
insert into hall(id, occupied) values (15, 0);
insert into hall(id, occupied) values (16, 0);
insert into hall(id, occupied) values (17, 0);
insert into hall(id, occupied) values (18, 0);
insert into hall(id, occupied) values (21, 0);
insert into hall(id, occupied) values (22, 0);
insert into hall(id, occupied) values (23, 0);
insert into hall(id, occupied) values (24, 0);
insert into hall(id, occupied) values (25, 0);
insert into hall(id, occupied) values (26, 0);
insert into hall(id, occupied) values (27, 0);
insert into hall(id, occupied) values (28, 0);
insert into hall(id, occupied) values (31, 0);
insert into hall(id, occupied) values (32, 0);
insert into hall(id, occupied) values (33, 0);
insert into hall(id, occupied) values (34, 0);
insert into hall(id, occupied) values (35, 0);
insert into hall(id, occupied) values (36, 0);
insert into hall(id, occupied) values (37, 0);
insert into hall(id, occupied) values (38, 0);
insert into hall(id, occupied) values (41, 0);
insert into hall(id, occupied) values (42, 0);
insert into hall(id, occupied) values (43, 0);
insert into hall(id, occupied) values (44, 0);
insert into hall(id, occupied) values (45, 0);
insert into hall(id, occupied) values (46, 0);
insert into hall(id, occupied) values (47, 0);
insert into hall(id, occupied) values (48, 0);
insert into hall(id, occupied) values (51, 0);
insert into hall(id, occupied) values (52, 0);
insert into hall(id, occupied) values (53, 0);
insert into hall(id, occupied) values (54, 0);
insert into hall(id, occupied) values (55, 0);
insert into hall(id, occupied) values (56, 0);
insert into hall(id, occupied) values (57, 0);
insert into hall(id, occupied) values (58, 0);