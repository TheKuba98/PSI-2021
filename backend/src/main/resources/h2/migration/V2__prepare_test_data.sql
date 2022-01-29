INSERT INTO `user` (first_name, last_name, birth_date,active, roles, username, password, index_number, user_type, position, position_type, academic_title, thesis_workload_limit, workload_increased)
VALUES ('Jakub', 'Test', date('10-10-1998'), 1, 'ROLE_STUDENT', '233331', '$2a$12$gtzZlXTB1DzAexdRMQ0ECe5u4OtDcFGePr4ZfClaWhQuJai32aLVy',
233331, 'ROLE_STUDENT', null, null, null, null ,null);

Insert into `field` (name, degree, faculty, thesis_workload, education_cycle)
values ('Informatyka', 'Magister Inżynier', 'W8', 15, '2021'),
 ('Inżynieria Systemów', 'Inżynier', 'W8', 12, '2022'),
 ('Elektronika', 'Inżynier', 'W4', 10, '2023');

Insert into `field_user` (field_id, username)
values (1,'233331'),
 (2, '233331');