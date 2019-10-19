create table attribute (
    id int4 not null,
    label varchar(16),
    primary key (id)
);

create table obj_entity (
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
    typename varchar(16),
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

alter table if exists obj_entity add constraint obj_type foreign key (type_id) references type on delete cascade on update cascade;

alter table if exists rating add constraint rat_obj foreign key (obj_id) references obj_entity on delete cascade on update cascade;

alter table if exists rating add constraint rat_usr foreign key (user_id) references usr on delete cascade on update cascade;

alter table if exists role add constraint role_usr foreign key (user_id) references usr on delete cascade on update cascade;

alter table if exists type_attribute add constraint typeatt_att foreign key (att_id) references attribute on delete cascade on update cascade;

alter table if exists type_attribute add constraint typeatt_type foreign key (type_id) references type on delete cascade on update cascade;

alter table if exists value add constraint val_att foreign key (atr_id) references attribute on delete cascade on update cascade;

alter table if exists value add constraint val_objects foreign key (entity_id) references obj_entity on delete cascade on update cascade;

/* Sequence increment 50*/

create sequence public.primary_gen

    minvalue 1
    maxvalue 9223372036854775807
    no cycle
    start 1
    cache 1;

alter table public.primary_gen owner to postgres;

/* Attributes
 * userId     1
 * refToObj   2
 * review     3
 * rate       4
 */

insert into attribute(id, label) values ((select nextval('primary_gen')), 'userId');

insert into attribute(id, label) values ((select nextval('primary_gen')), 'refToObject');

insert into attribute(id, label) values ((select nextval('primary_gen')), 'review');

insert into attribute(id, label) values ((select nextval('primary_gen')), 'rate');

/* Types
 * favorite     5
 * message      6
 * rating       7
 */

insert into type(id, typename) values ((select nextval('primary_gen')), 'favoriteList');

insert into type(id, typename) values ((select nextval('primary_gen')), 'message');

insert into type(id, typename) values ((select nextval('primary_gen')), 'rating');

/* Type-Attributes */

insert into type_attribute(id, att_id, type_id) values ((select nextval('primary_gen')), 1, 5);

insert into type_attribute(id, att_id, type_id) values ((select nextval('primary_gen')), 2, 5);

insert into type_attribute(id, att_id, type_id) values ((select nextval('primary_gen')), 1, 6);

insert into type_attribute(id, att_id, type_id) values ((select nextval('primary_gen')), 3, 6);

insert into type_attribute(id, att_id, type_id) values ((select nextval('primary_gen')), 1, 7);

insert into type_attribute(id, att_id, type_id) values ((select nextval('primary_gen')), 4, 7);

/* Administartor */

insert into usr(id, password, username) values ((select nextval('primary_gen')), 'root', 'root');