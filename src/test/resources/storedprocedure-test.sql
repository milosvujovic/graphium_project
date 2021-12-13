CREATE PROCEDURE getFilesForOrganisation(IN usernameParameter varchar(30))
BEGIN
set @OrganisationID = (Select organisation_id from users where username = usernameParameter);
SELECT files.file_id, files.file_name, files.file_type,files.tag,files.access_level, files.comment, files.date,users.username,files.subject,organisation.organisation_name
FROM files JOIN users on files.user_id = users.user_id JOIN organisation on organisation.organisation_id = users.organisation_id
where users.username = usernameParameter or (organisation.organisation_id = @OrganisationID and files.access_level != 'private');
END $$

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
END $$


CREATE PROCEDURE getAllFiles(IN usernameParameter varchar(30))
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
END $$


CREATE PROCEDURE getPublicFiles()
BEGIN
SELECT files.file_id, files.file_name, files.file_type,files.tag,files.access_level, files.comment, files.date,users.username,files.subject,organisation.organisation_name FROM files JOIN users on files.user_id = users.user_id  JOIN organisation on organisation.organisation_id = users.organisation_id where files.access_level = 'public' ORDER BY files.date;
END $$


CREATE PROCEDURE getPossiblePartnerships(IN organisationID varchar(30))
SQL SECURITY INVOKER
BEGIN
SELECT organisation_id, organisation_name FROM organisation WHERE organisation_id NOT IN (SELECT viewing_organisation_id
                                                                                          FROM organisation
                                                                                                   JOIN partnerships on partnerships.sharing_organisation_id = organisation.organisation_id
                                                                                          WHERE organisation.organisation_id = organisationID) and organisation.organisation_id != organisationID;
END $$

CREATE PROCEDURE getCurrentSharingPartnerships(IN organisationID varchar(30))
SQL SECURITY INVOKER
BEGIN
SELECT organisation_id, organisation_name FROM organisation WHERE organisation_id IN (SELECT viewing_organisation_id
FROM organisation
JOIN partnerships on partnerships.sharing_organisation_id = organisation.organisation_id
WHERE organisation.organisation_id = organisationID) and organisation.organisation_id != organisationID;
END $$

CREATE PROCEDURE getCurrentViewingPartnerships(IN organisationID varchar(30))
SQL SECURITY INVOKER
BEGIN
SELECT organisation_id, organisation_name FROM organisation WHERE organisation_id IN (
    SELECT sharing_organisation_id
    FROM organisation
             JOIN partnerships on partnerships.sharing_organisation_id = organisation.organisation_id
    WHERE partnerships.viewing_organisation_id = organisationID) and organisation.organisation_id != organisationID;
END $$
