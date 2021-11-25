drop database if exists `graphium`;
create database if not exists `graphium`;
use `graphium`;

drop table if exists `organisation`;
drop table if exists `role`;
drop table if exists `users`;
drop table if exists `files`;

create table if not exists `organisation` (
						`organisation_id` integer auto_increment primary key,
						`organisation_name` varchar(127)
);

create table if not exists `role` (
                      `role_id` integer auto_increment primary key,
                      `role_name` varchar(50)
);

create table if not exists `users` (
                       `user_id` bigint auto_increment primary key,
                       `organisation_id` integer references `organisation`(`organisation_id`),
                       `role_id` integer references `role`(`role_id`),
                       `first_name` varchar(30),
                       `last_name` varchar(30),
                       `username` varchar(30),
                       `email` varchar(50),
                       `password` varchar(100),
                       `active` boolean default true,
                       `organisation_approved` boolean default false,
                       `email_verified` boolean default false
);

create table if not exists `files` (
                       `file_id` varchar(100) primary key,
                       `user_id` bigint references `users`(`user_id`),
                       `file_name` varchar(100) NOT NULL,
                       `file_type` varchar(100) NOT NULL,
                       `tag` varchar(100) NOT NULL,
                       `access_level` varchar(100) NOT NULL,
                       `comment` varchar(100) NOT NULL,
                       `data` LONGBLOB not null,
                       `date` DATE not null
);

insert into organisation (organisation_name) values ('ExampleOrg1');
insert into organisation (organisation_name) values ('ExampleOrg2');
insert into organisation (organisation_name) values ('ExampleOrg3');

insert into role (role_name) values ('USER');
insert into role (role_name) values ('ORG_ADMIN');
insert into role (role_name) values ('SYS_ADMIN');


insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (1, 1, 'User', 'Example', 'user', 'user@example.com', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);

insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (2, 2, 'OrgAdmin', 'Example', 'orgadmin', 'orgadmin@example.com', '$2a$10$/vOrWfM8MJ.Dzpj3t5oGyeuNoERADR5LlEGrV6pwSr0Did8JikTTq', true, true, true);

insert into users (role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (3, 'SystemAdmin', 'Example', 'sysadmin', 'sysadmin@example.com', '$2a$10$/vOrWfM8MJ.Dzpj3t5oGyeuNoERADR5LlEGrV6pwSr0Did8JikTTq', true, true, true);
