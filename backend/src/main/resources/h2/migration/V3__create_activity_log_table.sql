CREATE TABLE activity_log
(
    activity_id   int(10)      NOT NULL AUTO_INCREMENT,
    username varchar(255),
    method varchar(50),
    url varchar(250),
    remote_addr varchar (100),
    activity_date timestamp ,
    http_status int,
    PRIMARY KEY (activity_id)
);