create table recent_directions
(
    id        int auto_increment
        primary key,
    chat      int not null,
    direction int not null
);

create table user
(
    chat       int                               not null
        primary key,
    lang       varchar(2)                        not null,
    reg_number varchar(11) default '00000000000' not null,
    enabled    tinyint(1)  default 1             not null,
    activity   int         default 0             not null
);
