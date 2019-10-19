create table attribute (
    id int4 not null,
    label varchar(16),
    primary key (id)
);

create table movie (
    id int4 not null,
    filename varchar(64),
    name varchar(32),
    parent_id int4,
    type_id int4,
    primary key (id)
);

create table rating (
    id int4 not null,
    rate float4,
    obj_id int4,
    user_id int4,
    primary key (id)
);

create table role (
    user_id int4 not null,
    role varchar(16)
);

create table type (
    id int4 not null,
    type varchar(16),
    primary key (id)
);

create table type_attribute (
    id int4 not null,
    att_id int4,
    type_id int4,
    primary key (id)
);

create table usr (
    id int4 not null,
    password varchar(16),
    username varchar(16),
    primary key (id)
);

create table value (
    id int4 not null,
    value varchar(64),
    atr_id int4,
    entity_id int4,
    primary key (id)
);

alter table if exists movie add constraint obj_type foreign key (type_id) references type;

alter table if exists rating add constraint rat_obj foreign key (obj_id) references movie;

alter table if exists rating add constraint rat_usr foreign key (user_id) references usr;

alter table if exists role add constraint role_usr foreign key (user_id) references usr;

alter table if exists type_attribute add constraint typeatt_att foreign key (att_id) references attribute;

alter table if exists type_attribute add constraint typeatt_type foreign key (type_id) references type;

alter table if exists value add constraint val_att foreign key (atr_id) references attribute;

alter table if exists value add constraint val_objects foreign key (entity_id) references movie;