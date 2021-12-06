drop database if exists `graphium`;
create database if not exists `graphium`;
use `graphium`;

drop table if exists `organisation`;
drop table if exists `role`;
drop table if exists `users`;
drop table if exists `files`;
drop table if exists `partnerships`;
drop table if exists `insights`;
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
						`subject` varchar(100) NOT NULL,
                       `comment` varchar(100) NOT NULL,
                       `data` LONGBLOB not null,
                       `date` DATE not null
);
create table if not exists `partnerships`(
			`partner_id` int primary key auto_increment,
            `sharing_organisation_id` integer references `organisation`(`organisation_id`),
            `viewing_organisation_id` integer references `organisation`(`organisation_id`)
);
create table if not exists `insights` (
						`insight_id` integer auto_increment primary key,
						`date` DATE not null,
                        `user_id` bigint references `users`(`user_id`),
                        `file_id` varchar(100) references `files`(`file_id`),
                        `organisation_id` integer references `organisation`(`organisation_id`)
);

DELIMITER //
CREATE PROCEDURE getPossiblePartnerships(IN organisationID varchar(30))
BEGIN
SELECT organisation_id, organisation_name FROM organisation WHERE organisation_id NOT IN (SELECT viewing_organisation_id
FROM organisation
JOIN partnerships on partnerships.sharing_organisation_id = organisation.organisation_id
WHERE organisation.organisation_id = organisationID) and organisation.organisation_id != organisationID;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE createPartnership(IN organisationID int, IN usernameParameter varchar(30))
BEGIN
set @SharingOrganisationID = (Select organisation_id from users where username = usernameParameter);
INSERT INTO partnerships(sharing_organisation_id,viewing_organisation_id)  values(@SharingOrganisationID,organisationID);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getFilesForOrganisation(IN usernameParameter varchar(30))
BEGIN
set @OrganisationID = (Select organisation_id from users where username = usernameParameter);
SELECT files.file_id, files.file_name, files.file_type,files.tag,files.access_level, files.comment, files.date,users.username,files.subject
 FROM graphium.files JOIN users on files.user_id = users.user_id JOIN organisation on organisation.organisation_id = users.organisation_id
 where users.username = usernameParameter or (organisation.organisation_id = @OrganisationID and files.access_level != 'private');
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getPartnersFiles(IN usernameParameter varchar(30))
BEGIN
set @OrganisationID = (Select organisation_id from users where username = usernameParameter);
SELECT files.file_id, files.file_name, files.file_type,files.tag,files.access_level, files.comment, files.date,users.username,files.subject
FROM files
JOIN users on files.user_id = users.user_id
JOIN organisation on organisation.organisation_id = users.organisation_id
WHERE (files.access_level !=('myOrganisation')) and (files.access_level !=('private'))  and organisation.organisation_id IN (SELECT sharing_organisation_id
FROM organisation
JOIN partnerships on partnerships.sharing_organisation_id = organisation.organisation_id
WHERE viewing_organisation_id = @OrganisationID)
ORDER BY files.date;
END //
DELIMITER ;
DELIMITER //
CREATE PROCEDURE getAllFiles(IN usernameParameter varchar(30))
BEGIN

set @OrganisationID = (Select organisation_id from users where username = usernameParameter);
SELECT files.file_id, files.file_name, files.file_type,files.tag,files.access_level, files.comment, files.date,users.username, files.subject
FROM files
JOIN users on files.user_id = users.user_id
JOIN organisation on organisation.organisation_id = users.organisation_id
where files.access_level = 'public' or users.username = 'adavies' or ((files.access_level !=('myOrganisation')) and (files.access_level !=('private')) and (organisation.organisation_id in (SELECT sharing_organisation_id
FROM organisation
JOIN partnerships on partnerships.sharing_organisation_id = organisation.organisation_id
WHERE viewing_organisation_id = @OrganisationID)or files.access_level != 'private'))
ORDER BY files.date;
END //

insert into organisation (organisation_name) values ('Welsh Goverment');
insert into organisation (organisation_name) values ('Cardiff University');
insert into organisation (organisation_name) values ('Swansea University');
insert into organisation (organisation_name) values ('Office For National Stats');

insert into partnerships(sharing_organisation_id,viewing_organisation_id) values (2,1);
insert into partnerships(sharing_organisation_id,viewing_organisation_id) values (3,1);
insert into partnerships(sharing_organisation_id,viewing_organisation_id) values (4,1);
insert into partnerships(sharing_organisation_id,viewing_organisation_id) values (2,3);
insert into partnerships(sharing_organisation_id,viewing_organisation_id) values (3,2);

insert into role (role_name) values ('USER');
insert into role (role_name) values ('ORG_ADMIN');
insert into role (role_name) values ('SYS_ADMIN');

insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (1, 1, 'Keith', 'Smith', 'ksmith', 'ksmith@example.com', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (2, 1, 'Andrew', 'Davies', 'adavies', 'adavies@example.com', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (2, 1, 'Oles', 'AtTheWheel', 'olesAtTheWheel', 'olesAtTheWheel@example.com', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (3, 1, 'John', 'Smith', 'jsmith', 'ksmith@example.com', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (4, 1, 'Lewis', 'Cook', 'lcook', 'lcook@example.com', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (1, 2, 'Carl', 'Smith', 'csmith', 'csmith@example.com', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (2, 2, 'Aaron', 'Ramsdale', 'aramsdale', 'aramsdale@example.com', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (3, 2, 'Sarah', 'Smith', 'ssmith', 'ksmith@example.com', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (4, 2, 'Roberto', 'Martinez', 'lcook', 'rmartinez@example.com', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);

insert into users (role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (3, 'SystemAdmin', 'Example', 'sysadmin', 'sysadmin@example.com', '$2a$10$/vOrWfM8MJ.Dzpj3t5oGyeuNoERADR5LlEGrV6pwSr0Did8JikTTq', true, true, true);


insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(1,2,'Tax Report', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'urgent','private' ,'Finances', 'Published', '111', '2021-12-06');
insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(2,2,'Council Report on Tax', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Final','private' ,'Finances', 'Publish', '111', '2021-12-06');


insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(3,3,'Covid Report December 2021', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'draft','private' ,'Health', 'Pre Omicron', '111', '2021-12-06');
insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(4,3,'Covid Report November 2021', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Final','myOrganisation' ,'Health', 'Includes Christmas', '111', '2021-12-06');
insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(5,7,'Covid Back Up Plan', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Final','myPartners' ,'Health', 'Post XMAS', '111', '2021-12-06');

insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(6,4,'WeatherDataJan', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Final','myOrganisation' ,'Georgraphy', 'Includes Newport', '111', '2021-12-06');
insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(7,8,'WeatherDataFeb', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Final','myPartners' ,'Georgraphy', 'Just Cardiff', '111', '2021-12-06');
insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(8,4,'WeatherDataMar', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Final','myPartners' ,'Georgraphy', 'InaccurateData', '111', '2021-12-06');

insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(9,1,'Youth Sport Report', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Final','public' ,'Sport', 'Published with WRU, FAW', '111', '2021-12-06');
