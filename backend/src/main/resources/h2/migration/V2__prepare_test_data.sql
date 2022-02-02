INSERT INTO `user` (first_name, last_name, birth_date,active, roles, username, password, index_number, user_type, position, position_type, academic_title, thesis_workload_limit, workload_increased)
VALUES ('Jakub', 'Test', date('1998-10-10'), 1, 'ROLE_STUDENT', '233331', '$2a$12$0Sfkw6LsoHq36OhtXWEPgeFnJ2MgUb1DilxxcQhpdZaKXQqoIYRa2',
        233331, 'ROLE_STUDENT', null, null, null, null ,null),
       ('Piotr', 'Pawlicki', date('1994-10-10'), 1, 'ROLE_STUDENT', '233332', '$2a$12$0Sfkw6LsoHq36OhtXWEPgeFnJ2MgUb1DilxxcQhpdZaKXQqoIYRa2',
        233332, 'ROLE_STUDENT', null, null, null, null ,null),
        ('Janusz', 'Kołodziej', date('1984-10-10'), 1, 'ROLE_STUDENT', '233333', '$2a$12$0Sfkw6LsoHq36OhtXWEPgeFnJ2MgUb1DilxxcQhpdZaKXQqoIYRa2',
        233333, 'ROLE_STUDENT', null, null, null, null ,null),
        ('Piotr', 'Baron', date('1970-10-10'), 1, 'ROLE_EMPLOYEE', 'piotr70', '$2a$12$0Sfkw6LsoHq36OhtXWEPgeFnJ2MgUb1DilxxcQhpdZaKXQqoIYRa2',
        null, 'ROLE_EMPLOYEE', 'TEACHING_STAFF', 'SENIOR_LECTURER', 'DOCTOR', 100 ,0);

Insert into `field` (name, degree, faculty, thesis_workload, education_cycle)
values ('Informatyka', 'Magister Inżynier', 'W8', 15, '2021'),
       ('Inżynieria Systemów', 'Inżynier', 'W8', 12, '2022'),
       ('Elektronika', 'Inżynier', 'W4', 10, '2023');

Insert into `field_user` (field_id, username)
values (1,'233331'),
       (2, '233331'),
       (1, '233332'),
       (3, '233333');

INSERT INTO `thesis`(asap_date, document_format, entering_date, shared_work, reserved,registered_by_student, thesis_status, username)
VALUES (date('2022-06-04'), 'PDF', date('2022-06-02'), 1, 1,0,'ASSIGNED' ,'piotr70'),
       (date('2022-06-25'), 'PDF', date('2022-06-23'), 0, 0,0,'REGISTERED' ,'piotr70' ),
       (date('2022-07-25'), 'PDF', date('2022-07-23'), 0, 1,0,'DONE' ,'piotr70' );

INSERT INTO `thesis_details` (`language`, thesis_type, thema, field_id, username, thesis_id)
VALUES ('polish', 'BSC', 'Aplikacja webowa dla sportowców',1,'233331', 1),
       ('polish', 'MASTERS', 'Analiza porównawcza baz SQL',1, null, 2),
       ('polish', 'BA', 'Klasyfikacja',3,'233333', 3);;

INSERT INTO `share` (thesis_id, username)
VALUES (1,'233331'),
       (1,'233332'),
       (3,'233333')