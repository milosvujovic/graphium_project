drop database if exists `testgraphium`;
create database if not exists `testgraphium`;
use `testgraphium`;

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
CREATE PROCEDURE getCurrentSharingPartnerships(IN organisationID varchar(30))
BEGIN
SELECT organisation_id, organisation_name FROM organisation WHERE organisation_id IN (SELECT viewing_organisation_id
                                                                                      FROM organisation
                                                                                               JOIN partnerships on partnerships.sharing_organisation_id = organisation.organisation_id
                                                                                      WHERE organisation.organisation_id = organisationID) and organisation.organisation_id != organisationID;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getCurrentViewingPartnerships(IN organisationID varchar(30))
BEGIN
SELECT organisation_id, organisation_name FROM organisation WHERE organisation_id IN (
    SELECT sharing_organisation_id
    FROM organisation
             JOIN partnerships on partnerships.sharing_organisation_id = organisation.organisation_id
    WHERE partnerships.viewing_organisation_id = organisationID) and organisation.organisation_id != organisationID;
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
SELECT files.file_id, files.file_name, files.file_type,files.tag,files.access_level, files.comment, files.date,users.username,files.subject,organisation.organisation_name
FROM graphium.files JOIN users on files.user_id = users.user_id JOIN organisation on organisation.organisation_id = users.organisation_id
where users.username = usernameParameter or (organisation.organisation_id = @OrganisationID and files.access_level != 'private');
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getPartnersFiles(IN usernameParameter varchar(30))
BEGIN
set @OrganisationID = (Select organisation_id from users where username = usernameParameter);
SELECT files.file_id, files.file_name, files.file_type,files.tag,files.access_level, files.comment, files.date,users.username,files.subject,organisation.organisation_name
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
SELECT files.file_id, files.file_name, files.file_type,files.tag,files.access_level, files.comment, files.date,users.username, files.subject, organisation.organisation_name
FROM files
         JOIN users on files.user_id = users.user_id
         JOIN organisation on organisation.organisation_id = users.organisation_id
where files.access_level = 'public' or users.username = 'adavies' or ((files.access_level !=('myOrganisation')) and (files.access_level !=('private')) and (organisation.organisation_id in (SELECT sharing_organisation_id
                                                                                                                                                                                             FROM organisation
                                                                                                                                                                                                      JOIN partnerships on partnerships.sharing_organisation_id = organisation.organisation_id
                                                                                                                                                                                             WHERE viewing_organisation_id = @OrganisationID)or files.access_level != 'private'))
ORDER BY files.date;
END //