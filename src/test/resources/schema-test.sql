drop database if exists `testgraphium`;
create database if not exists `testgraphium`;
use `testgraphium`;

drop table if exists `organisation`;
drop table if exists `role`;
drop table if exists `users`;
drop table if exists `files`;
drop table if exists `partnerships`;
drop table if exists `insights`;
create table `organisation` (
                                              `organisation_id` integer auto_increment primary key,
                                              `organisation_name` varchar(127)
);



create table `role` (
                                      `role_id` integer auto_increment primary key,
                                      `role_name` varchar(50)
);

create table `users` (
                                       `user_id` bigint auto_increment primary key,
                                       `organisation_id` integer references `organisation`(`organisation_id`),
                                       `role_id` integer references `role`(`role_id`),
                                       `first_name` varchar(30),
                                       `last_name` varchar(30),
                                       `username` varchar(30),
                                       `email` varchar(150),
                                       `password` varchar(100),
                                       `active` boolean default true,
                                       `organisation_approved` boolean default false,
                                       `email_verified` boolean default false
);

create table `files` (
                                       `file_id` varchar(100) primary key,
                                       `user_id` bigint references `users`(`user_id`),
                                       `file_name` varchar(100) NOT NULL,
                                       `file_type` varchar(100) NOT NULL,
                                       `tag` varchar(100) NOT NULL,
                                       `access_level` varchar(100) NOT NULL,
                                       `subject` varchar(100) NOT NULL,
                                       `comment` varchar(100) NOT NULL,
                                       `data` LONGBLOB not null,
                                       `date` DATE not null
);
create table `partnerships`(
                                             `partner_id` int primary key auto_increment,
                                             `sharing_organisation_id` integer references `organisation`(`organisation_id`),
                                             `viewing_organisation_id` integer references `organisation`(`organisation_id`)
);
create table `insights` (
                                          `insight_id` integer auto_increment primary key,
                                          `date` DATE not null,
                                          `user_id` bigint references `users`(`user_id`),
                                          `file_id` varchar(100) references `files`(`file_id`),
                                          `organisation_id` integer references `organisation`(`organisation_id`)
);
