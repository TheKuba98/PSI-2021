CREATE TABLE field
(
    name            varchar(255) NOT NULL,
    degree          varchar(255) NOT NULL,
    faculty         varchar(255) NOT NULL,
    thesis_workload int(10)      NOT NULL,
    education_cycle varchar(255) NOT NULL,
    field_id        int(10)      NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (field_id)
);

CREATE TABLE field_user
(
    field_id int(10)      NOT NULL,
    username varchar(255) NOT NULL,
    PRIMARY KEY (field_id, username)
);
CREATE TABLE review
(
    username    varchar(255),
    grade       real         NOT NULL,
    opinion     varchar(255) NOT NULL,
    acting_dean varchar(255) NOT NULL,
    review_id   int(10)      NOT NULL AUTO_INCREMENT,
    thesis_id   int(10)      NOT NULL,
    PRIMARY KEY (review_id)
);
CREATE TABLE share
(
    thesis_id int(10)      NOT NULL,
    username  varchar(255) NOT NULL,
    PRIMARY KEY (thesis_id, username)
);
CREATE TABLE thesis
(
    asap_date       date,
    document_format varchar(255) NOT NULL,
    entering_date   date,
    shared_work     bit(1)       NOT NULL,
    reserved        bit(1),
    thesis_status   varchar(255) NOT NULL,
    username        varchar(255),
    thesis_id       int(10)      NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (thesis_id)
);

CREATE TABLE thesis_details
(
    thesis_details_id int(10)      NOT NULL AUTO_INCREMENT,
    language          varchar(255),
    thesis_type       varchar(255) NOT NULL,
    thema             varchar(255) NOT NULL,
    field_id          int(10)      NOT NULL,
    username          varchar(255) ,
    thesis_id         int(10)      NOT NULL,
    PRIMARY KEY (thesis_details_id)
);
CREATE TABLE `user`
(
    first_name            varchar(255) NOT NULL,
    last_name             varchar(255) NOT NULL,
    birth_date            date,
    active                bit(1)       NOT NULL,
    roles                 varchar(255),
    username              varchar(255) NOT NULL,
    password              varchar(255) NOT NULL,
    index_number          varchar(255) UNIQUE,
    user_type             varchar(255),
    position              varchar(255),
    position_type         varchar(255),
    academic_title        varchar(255),
    thesis_workload_limit int(10),
    workload_increased    bit(1),
    PRIMARY KEY (username)
);
ALTER TABLE field_user
    ADD CONSTRAINT FKField_User363790 FOREIGN KEY (username) REFERENCES `user` (username);
ALTER TABLE field_user
    ADD CONSTRAINT FKField_User917295 FOREIGN KEY (field_id) REFERENCES field (field_id);
ALTER TABLE share
    ADD CONSTRAINT FKShare187612 FOREIGN KEY (username) REFERENCES `user` (username);
ALTER TABLE share
    ADD CONSTRAINT FKShare364126 FOREIGN KEY (thesis_id) REFERENCES thesis (thesis_id);
ALTER TABLE review
    ADD CONSTRAINT FKReview196700 FOREIGN KEY (acting_dean) REFERENCES `user` (username);
ALTER TABLE review
    ADD CONSTRAINT FKReview452889 FOREIGN KEY (username) REFERENCES `user` (username);
ALTER TABLE review
    ADD CONSTRAINT FKReview452890 FOREIGN KEY (thesis_id) REFERENCES `thesis` (thesis_id);
ALTER TABLE thesis_details
    ADD CONSTRAINT FKThesisDeta573489 FOREIGN KEY (field_id) REFERENCES field (field_id);
ALTER TABLE thesis_details
    ADD CONSTRAINT FKThesisDeta434723 FOREIGN KEY (thesis_id) REFERENCES thesis (thesis_id);
ALTER TABLE thesis_details
    ADD CONSTRAINT FKThesisDeta117015 FOREIGN KEY (username) REFERENCES `user` (username);
ALTER TABLE thesis
    ADD CONSTRAINT FKThesis985096 FOREIGN KEY (username) REFERENCES `user` (username);
