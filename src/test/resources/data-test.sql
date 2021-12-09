
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
