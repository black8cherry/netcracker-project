/* Tables */

create table attribute (
    id int4 not null,
    label varchar(16),
    label_type varchar(16),
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
    parent_id int4,
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
    password varchar(128),
    username varchar(16),
    primary key (id)
);

create table value (
    id int4 not null,
    value varchar(2048),
    atr_id int4,
    entity_id int4,
    primary key (id)
);

/* References */

alter table if exists obj_entity add constraint obj_type foreign key (type_id) references type on delete cascade on update cascade;

alter table if exists rating add constraint rat_obj foreign key (obj_id) references obj_entity on delete cascade on update cascade;

alter table if exists rating add constraint rat_usr foreign key (user_id) references usr on delete cascade on update cascade;

alter table if exists role add constraint role_usr foreign key (user_id) references usr on delete cascade on update cascade;

alter table if exists type_attribute add constraint typeatt_att foreign key (att_id) references attribute on delete cascade on update cascade;

alter table if exists type_attribute add constraint typeatt_type foreign key (type_id) references type on delete cascade on update cascade;

alter table if exists value add constraint val_att foreign key (atr_id) references attribute on delete cascade on update cascade;

alter table if exists value add constraint val_objects foreign key (entity_id) references obj_entity on delete cascade on update cascade;

/* Sequences */

create sequence public.attribute_gen
    minvalue 1
    maxvalue 9223372036854775807
    no cycle
    start 1
    cache 1;

create sequence public.obj_entity_gen
    minvalue 1
    maxvalue 9223372036854775807
    no cycle
    start 1
    cache 1;

create sequence public.type_gen
    minvalue 1
    maxvalue 9223372036854775807
    no cycle
    start 1
    cache 1;

create sequence public.user_gen
    minvalue 1
    maxvalue 9223372036854775807
    no cycle
    start 1
    cache 1;

create sequence public.value_gen
    minvalue 1
    maxvalue 9223372036854775807
    no cycle
    start 1
    cache 1;

create sequence public.type_attribute_gen
    minvalue 1
    maxvalue 9223372036854775807
    no cycle
    start 1
    cache 1;

alter table public.attribute_gen owner to postgres;

alter table public.obj_entity_gen owner to postgres;

alter table public.type_gen owner to postgres;

alter table public.user_gen owner to postgres;

alter table public.value_gen owner to postgres;

alter table public.type_attribute_gen owner to postgres;

/* Attributes
 * userId     1
 * refToObj   2
 * review     3
 * rate       4
 * username   5
 */

insert into attribute(id, label, label_type) values ((select nextval('attribute_gen')), 'userId', 'numerical');

insert into attribute(id, label, label_type) values ((select nextval('attribute_gen')), 'refToObject', 'numerical');

insert into attribute(id, label, label_type) values ((select nextval('attribute_gen')), 'review', 'char');

insert into attribute(id, label, label_type) values ((select nextval('attribute_gen')), 'rate', 'numerical');

insert into attribute(id, label, label_type) values ((select nextval('attribute_gen')), 'username', 'char');

/* Types
 * video        1
 * favorite     2
 * message      3
 * rating       4
 */

insert into type(id, typename) values ((select nextval('type_gen')), 'video');

insert into type(id, typename) values ((select nextval('type_gen')), 'favoriteList');

insert into type(id, typename) values ((select nextval('type_gen')), 'message');

insert into type(id, typename) values ((select nextval('type_gen')), 'rating');

/* Type-Attribute structure */

        /* - favorite type */

insert into type_attribute(id, att_id, type_id) values ((select nextval('type_attribute_gen')), 1, 2);

insert into type_attribute(id, att_id, type_id) values ((select nextval('type_attribute_gen')), 2, 2);

        /* - message type */

insert into type_attribute(id, att_id, type_id) values ((select nextval('type_attribute_gen')), 1, 3);

insert into type_attribute(id, att_id, type_id) values ((select nextval('type_attribute_gen')), 2, 3);

insert into type_attribute(id, att_id, type_id) values ((select nextval('type_attribute_gen')), 3, 3);

insert into type_attribute(id, att_id, type_id) values ((select nextval('type_attribute_gen')), 5, 3);

        /* - rating type */

insert into type_attribute(id, att_id, type_id) values ((select nextval('type_attribute_gen')), 1, 4);

insert into type_attribute(id, att_id, type_id) values ((select nextval('type_attribute_gen')), 4, 4);

/* Administrator account */

insert into usr(id, password, username) values ((select nextval('user_gen')), 'root', 'root');

insert into role(user_id, role) values (1, 'ADMIN');

insert into obj_entity(id, type_id) values ((select nextval('obj_entity_gen')), 2);

insert into value(id, "value", atr_id, entity_id)  values ((select nextval('value_gen')), '1', 1, 1);

/* Crypt */

create extension if not exists pgcrypto;

update usr set password = crypt(password, gen_salt('bf', 8));

