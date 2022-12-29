DROP SCHEMA IF EXISTS `pafws` ;
CREATE SCHEMA IF NOT EXISTS `pafws`;
USE `pafws` ;

create table if not exists pafws.task(
	task_id int auto_increment not null,
    description varchar(255),
    priority int check(priority in (1, 2, 3)),
    due_date date,
    
    primary key(task_id)
);

-- (1:M) rs
-- foreign key constraint should be written in the MANY table
-- FK field/column is non-unique and references the ONE table's PK
create table if not exists pafws.user(
	user_id varchar(128) not null,
    username varchar(128) not null,
    name varchar(128),
    task_id int,
    
    primary key (user_id),
    constraint fk_task_id
		foreign key(task_id) references pafws.task(task_id)
);

-- if column has been specified, must have matching number of values args
-- key in null as value if required
insert into user(user_id, username, name) values('c4ec9cc8','user1','karen goh');
insert into user(user_id, username, name) values('ff3da016','user2','fred tan');
insert into user(user_id, username, name) values('d9fc1d18','user3',null);
insert into user(user_id, username, name) values('69f11c94','user4','john Lee');

drop user 'fred'@'%';
create user 'fred'@'%' identified by 'fred';
grant all privileges on pafws.* to 'fred'@'%';
flush privileges;

