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
                       `email` varchar(150),
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
                       `tag` ENUM('urgent', 'draft', 'final') NOT NULL,
                       `access_level` ENUM('myOrganisation', 'myPartners', 'private', 'public') NOT NULL,
						`subject` varchar(20) NOT NULL,
                       `comment` varchar(50) NOT NULL,
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
SQL SECURITY INVOKER
BEGIN
SELECT organisation_id, organisation_name FROM organisation WHERE organisation_id NOT IN (SELECT viewing_organisation_id
FROM organisation
JOIN partnerships on partnerships.sharing_organisation_id = organisation.organisation_id
WHERE organisation.organisation_id = organisationID) and organisation.organisation_id != organisationID;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getCurrentSharingPartnerships(IN organisationID varchar(30))
SQL SECURITY INVOKER
BEGIN
SELECT organisation_id, organisation_name FROM organisation WHERE organisation_id IN (SELECT viewing_organisation_id
FROM organisation
JOIN partnerships on partnerships.sharing_organisation_id = organisation.organisation_id
WHERE organisation.organisation_id = organisationID) and organisation.organisation_id != organisationID;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getCurrentViewingPartnerships(IN organisationID varchar(30))
SQL SECURITY INVOKER
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
SQL SECURITY INVOKER
BEGIN
set @SharingOrganisationID = (Select organisation_id from users where username = usernameParameter);
INSERT INTO partnerships(sharing_organisation_id,viewing_organisation_id)  values(@SharingOrganisationID,organisationID);
END //
DELIMITER ;

DELIMITER //

CREATE PROCEDURE getFilesForOrganisation(IN usernameParameter varchar(30))
SQL SECURITY INVOKER
BEGIN
set @OrganisationID = (Select organisation_id from users where username = usernameParameter);
SELECT files.file_id, files.file_name, files.file_type,files.tag,files.access_level, files.comment, files.date,users.username,files.subject,organisation.organisation_name
 FROM graphium.files JOIN users on files.user_id = users.user_id JOIN organisation on organisation.organisation_id = users.organisation_id
 where users.username = usernameParameter or (organisation.organisation_id = @OrganisationID and files.access_level != 'private');
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getPartnersFiles(IN usernameParameter varchar(30))
SQL SECURITY INVOKER
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
SQL SECURITY INVOKER
BEGIN
set @OrganisationID = (Select organisation_id from users where username = usernameParameter);
SELECT files.file_id, files.file_name, files.file_type,files.tag,files.access_level, files.comment, files.date,users.username, files.subject, organisation.organisation_name
FROM files
JOIN users on files.user_id = users.user_id
JOIN organisation on organisation.organisation_id = users.organisation_id
where files.access_level = 'public' or users.username = usernameParameter or ((organisation.organisation_id = @OrganisationID and files.access_level !='private') or (organisation.organisation_id in (SELECT sharing_organisation_id
FROM organisation
JOIN partnerships on partnerships.sharing_organisation_id = organisation.organisation_id
WHERE viewing_organisation_id = @OrganisationID) and files.access_level = 'myPartners'));
END //

DELIMITER //
CREATE PROCEDURE getPublicFiles()
SQL SECURITY INVOKER
BEGIN
SELECT files.file_id, files.file_name, files.file_type,files.tag,files.access_level, files.comment, files.date,users.username,files.subject,organisation.organisation_name FROM graphium.files JOIN users on files.user_id = users.user_id  JOIN organisation on organisation.organisation_id = users.organisation_id where files.access_level = 'public' ORDER BY files.date;
END //

DELIMITER //
CREATE PROCEDURE hasAccesToTheFile()
BEGIN
SELECT files.file_id, files.file_name, files.file_type,files.tag,files.access_level, files.comment, files.date,users.username, files.subject, organisation.organisation_name
FROM files
JOIN users on files.user_id = users.user_id
JOIN organisation on organisation.organisation_id = users.organisation_id
where files.access_level = 'public' or users.username = usernameParameter or ((organisation.organisation_id = @OrganisationID and files.access_level !='private') or (organisation.organisation_id in (SELECT sharing_organisation_id
FROM organisation
JOIN partnerships on partnerships.sharing_organisation_id = organisation.organisation_id
WHERE viewing_organisation_id = @OrganisationID) and files.access_level = 'myPartners'));
END //

DELIMITER //
CREATE FUNCTION `hasAccessToFiles`(usernameP varchar(45),
fileID long) RETURNS boolean
BEGIN
set @OrganisationID = (Select organisation_id from users where username = usernameP);
return (select distinct count(*)
where fileID in (
SELECT files.file_id
FROM files
JOIN users on files.user_id = users.user_id
JOIN organisation on organisation.organisation_id = users.organisation_id
where files.access_level = 'public' or users.username = usernameP or ((organisation.organisation_id = @OrganisationID and files.access_level !='private') or (organisation.organisation_id in (SELECT sharing_organisation_id
FROM organisation
JOIN partnerships on partnerships.sharing_organisation_id = organisation.organisation_id
WHERE viewing_organisation_id = @OrganisationID) and files.access_level = 'myPartners'))));

IF @amount = '0' THEN
return false;
ELSE
RETURN true;
END IF;
END //

DELIMITER //
CREATE FUNCTION `hasAccessToModfiyFile`(usernameP varchar(45),
fileID long) RETURNS boolean
BEGIN
set @creatorID = (select users.username from files join users on users.user_id = files.user_id where file_id = fileID);
IF @creatorID = usernameP THEN
return true;
ELSE
RETURN false;
END IF;
END //

DELIMITER //
CREATE FUNCTION partnershipExists(orgID varchar(45),usernameP varchar(45)) RETURNS boolean
BEGIN
set @OrganisationID = (Select organisation_id from users where username = usernameP);
set @numberOfInstances = (select count(*) from partnerships where sharing_organisation_id = @OrganisationID and viewing_organisation_id = orgID);
IF @numberOfInstances = '1' THEN
return true;
ELSE
RETURN false;
END IF;
END //

DELIMITER //
CREATE FUNCTION canVerifyUser(usernameP varchar(45), orgAdmin varchar(45)) RETURNS boolean
BEGIN
set @OrgIDAdmin = (Select organisation_id from users where username = orgAdmin);
set @OrgIDUser = (Select organisation_id from users where username = usernameP);
set @numberOfInstances = (select count(*) from users where usernameP in (select users.username where @OrgIDAdmin = users.organisation_id and @OrgIDUser = users.organisation_id and organisation_approved = false));
IF @numberOfInstances = '1' THEN
return true;
ELSE
RETURN false;
END IF;
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

insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (1, 1, 'Keith', 'Smith', 'ksmith', '781f5b6d6da3b4011ebc3cd6ce37896e8c9dd51dee9b1f996765c9355d15cbe52f85d9c011bff9baff270ae7a59b14f547a1', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (2, 1, 'Andrew', 'Davies', 'adavies', '5318d34d0801a280ab17d9845cff67d3794c81264d9b016f366a31f0071a3484107e1e2651b7f98f5fcdd55f1f1301d8372b36', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (2, 1, 'Oles', 'AtTheWheel', 'olesatthewheel', 'c2844135f0931ca6f0285fd034b928c607123419d78bf4c4d7454512624a02fc11b1aca4061994f888097897b5706f7a9e8c8eecef4b9e340ee8', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (3, 1, 'John', 'Smith', 'jsmith', '8b4ac1133d70e9ae17c0fcf13b22adf3c9077bc0058ed36b107856352754fdc6356337c3d65888ffea58e680609376539601', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (4, 1, 'Lewis', 'Cook', 'lcook', '434a11b1abde92c74ed353b0e4a5d43b427f5bdf9f228274e32c29f9560036916dba66108f448deeb9d269f9adb91bc5f2', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);

insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (1, 2, 'Carl', 'Smith', 'csmith', '1fba6e932635386112597b7e5c995f99e2fd9f4615e9b0b52d06907ac22ba7627d1a1ccbe47de0919733d89759e820cee6b5', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (2, 2, 'Aaron', 'Ramsdale', 'aramsdale', 'e0b2cdf3b2a184df6592e03d3e738c226d08252091fb0c0671f7ea305e234a0a54a5645f9bbc089e60d2e82cdfb24c349c566891c7', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (3, 2, 'Sarah', 'Smith', 'ssmith', 'd98c72e6b214dc3fc42c70b0b4eb3dfaab23c553157dab0279eb7c65f11a9d5a1b01d26fbdc5303c989ff7fd6dbb9e081c52', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (4, 2, 'Roberto', 'Martinez', 'rmartinez', '8eb4b756915b44668e8c4614a5f013c44c37f26696d3c82a278d008827d5aef59ff3f1801ddee3d69b7b2280589e8cbb5e895ab30f', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);

insert into users (role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (3, 'SystemAdmin', 'Example', 'sysadmin', '7def18a22ed2bdb17ab471841e0a0a867fadf28378c015de00feaf2aeb3ea3e1b2fca0b94fbc60435390786a798890cd8dbe416c', '$2a$10$/vOrWfM8MJ.Dzpj3t5oGyeuNoERADR5LlEGrV6pwSr0Did8JikTTq', true, true, true);

insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (1, 1, 'Wiktoria', 'Jankowicz', 'wjankowicz', '010af7c38e5897ef6c32af63538dae6de594611bc63de21ca0019af33c5a041565ea655b67af48e13ef4547b94fd1906a0816abf4136', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, false, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (2, 1, 'Mustafa', 'Zahir', 'mzahir', 'e13dd92a0edc362240b4bf83572f78efb6e50fcf7acf096cc703d923e1a61557d06ca6d4762a275f94bbe096c9af7dc81a08', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, false, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (3, 1, 'Selma', 'Bayrami', 'sbayrami', '0d7d6c4ea22fe1319fa46aa3512e2020d92c97a3503280321ad9535b023e3730bbc896c81f7ec5906fde0cfb8c48dd0ce43512cb', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, false, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (4, 1, 'Adeola', 'Emegwa', 'aemegwa', 'c433989941eb9fd3c2387e3f3563df1fa5f72c53f8d1848ce2121607306e139db1ff98fe63c6be514cffb4bc41660e2e2bdd1a', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, false, true);

insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(1,2,'Tax Report', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'urgent','private' ,'Finances', 'Published', '111', '2021-12-06');
insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(2,2,'Council Report on Tax', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Final','private' ,'Finances', 'Publish', '111', '2021-12-06');


insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(3,3,'Covid Report December 2021', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'draft','private' ,'Health', 'Pre Omicron', '111', '2021-12-06');
insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(4,3,'Covid Report November 2021', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Final','myOrganisation' ,'Health', 'Includes Christmas', '111', '2021-12-06');
insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(5,7,'Covid Back Up Plan', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Final','myPartners' ,'Health', 'Post XMAS', '111', '2021-12-06');

insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(6,4,'WeatherDataJan', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Final','myOrganisation' ,'Georgraphy', 'Includes Newport', '111', '2021-12-06');
insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(7,8,'WeatherDataFeb', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Final','myPartners' ,'Georgraphy', 'Just Cardiff', '111', '2021-12-06');
insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(8,4,'WeatherDataMar', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Final','myPartners' ,'Georgraphy', 'InaccurateData', '111', '2021-12-06');

insert into files(file_id, user_id, file_name, file_type, tag, access_level, subject, comment, data, date) values(9,1,'Youth Sport Report', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 'Final','public' ,'Sport', 'Published with WRU, FAW', '111', '2021-12-06');
