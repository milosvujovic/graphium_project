create database if not exists graphium;
use graphium;

drop table if exists user_role;
drop table if exists users;
drop table if exists role;

create table users (
                       user_id bigint auto_increment primary key,
                       organisation_id integer references organisation(organisation_id),
                       first_name varchar(30),
                       last_name varchar(30),
                       username varchar(30),
                       email varchar(50),
                       password varchar(100)
);

create table role (
                      role_id integer auto_increment primary key,
                      role_name varchar(50)
);

create table user_role (
                           user_role_id integer auto_increment primary key,
                           user_id integer references users(user_id),
                           role_id integer references role(role_id)
);

create table organisation (
                              organisation_id integer auto_increment primary key,
                              organisation_name varchar(127)
);

insert into organisation (organisation_name) values ('ExampleOrg1');
insert into organisation (organisation_name) values ('ExampleOrg2');
insert into organisation (organisation_name) values ('ExampleOrg3');

insert into role (role_name) values ('USER');
insert into users (organisation_id, first_name, last_name, username, email, password) values (1, 'User', 'Example', 'user', 'user@example.com', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C');
insert into user_role (user_id, role_id) values (1,1);

insert into role (role_name) values ('ADMIN');
insert into users (organisation_id, first_name, last_name, username, email, password) values (2, 'Admin', 'Example', 'admin', 'admin@example.com', '$2a$10$/vOrWfM8MJ.Dzpj3t5oGyeuNoERADR5LlEGrV6pwSr0Did8JikTTq');
insert into user_role (user_id, role_id) values (2,1);
insert into user_role (user_id, role_id) values (2,2);
