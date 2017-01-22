
CREATE TABLE `user` (
id int primary key comment "user id",
mobile int(11) unique not null comment "mobile/user name",
password varchar(32) not null comment "password",
nickname varchar(32) default "" comment "nickname"
status int(1) not null comment "0-OK/1-forbid/...",
create_dt datetime default now() comment "create date time",
update_dt datetime default now() comment "update date time"
);

CREATE TABLE `job` (
id int primary key comment "job id",
publisher_id int not null comment "user id",
job_name varchar(16) not null comment "job name",
number int not null comment "招聘人数",
address varchar(32) not null comment "地址",
create_dt datetime not null default now() comment "create date time",
update_dt datetime not null default now() comment "update date time",
status int(1) not null default 0 comment "0-OK/1-delete"
);

CREATE TABLE `job_list` (
id int primary key comment "job rel id",
name varchar(32) not null comment "job name",
);
