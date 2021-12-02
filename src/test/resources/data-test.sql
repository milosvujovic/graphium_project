insert into organisation (organisation_name) values ('ExampleOrg1');
insert into organisation (organisation_name) values ('ExampleOrg2');
insert into organisation (organisation_name) values ('ExampleOrg3');

insert into partnerships(sharing_organisation_id,viewing_organisation_id) values (1,2);
insert into partnerships(sharing_organisation_id,viewing_organisation_id) values (1,3);

insert into role (role_name) values ('USER');
insert into role (role_name) values ('ORG_ADMIN');
insert into role (role_name) values ('SYS_ADMIN');


insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (1, 1, 'User', 'Example', 'user', 'user@example.com', '$2a$10$jy7T4nLKfalHAp/3Dv2qae0Le9.xsSUWnWq9WA2XXiyX2orgIEh9C', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (2, 2, 'OrgAdmin', 'Example', 'orgadmin', 'orgadmin@example.com', '$2a$10$/vOrWfM8MJ.Dzpj3t5oGyeuNoERADR5LlEGrV6pwSr0Did8JikTTq', true, true, true);
insert into users (organisation_id, role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (3, 1, 'OrgUser', 'Example', 'orguser', 'orguser@example.com', '$2a$10$/vOrWfM8MJ.Dzpj3t5oGyeuNoERADR5LlEGrV6pwSr0Did8JikTTq', true, true, true);
insert into users (role_id, first_name, last_name, username, email, password, active, organisation_approved, email_verified) values (3, 'SystemAdmin', 'Example', 'sysadmin', 'sysadmin@example.com', '$2a$10$/vOrWfM8MJ.Dzpj3t5oGyeuNoERADR5LlEGrV6pwSr0Did8JikTTq', true, true, true);

INSERT INTO files (`file_id`, `user_id`, `file_name`, `file_type`, `tag`, `access_level`, `comment`, `data`, `date`) VALUES ('33b516bd-5b12-43b1-b6a9-ab45cd391fe6', '1', 'posters Design', 'application/pdf', 'urgent', 'public', 'This is the final copy.', '000', '2021-12-02');
INSERT INTO files (`file_id`, `user_id`, `file_name`, `file_type`, `tag`, `access_level`, `comment`, `data`, `date`) VALUES ('38d53bac-b33a-4373-bd62-02be76c5af82', '4', 'Guide', 'application/pdf', 'draft', 'public', 'This is the final copy.', '000', '2021-11-30');

