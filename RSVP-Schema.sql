DROP SCHEMA IF EXISTS `rsvp` ;
CREATE SCHEMA IF NOT EXISTS `rsvp`;
USE `rsvp` ;

create table if not exists rsvp.rsvp(
	rsvp_id int auto_increment not null,
    name varchar(255),
    email varchar(255),
    confirm_date date,
    comments text,
    
    primary key(rsvp_id)
);


-- if column has been specified, must have matching number of values args
-- key in null as value if required
insert into rsvp(name, email, confirm_date, comments) values('karen goh', 'hi1@email.com', CURDATE(), 'hihihii');
insert into rsvp(name, email, confirm_date, comments) values('fred tan',null, CURDATE(),'ohohoho');
insert into rsvp(name, email, confirm_date, comments) values(null, 'hi3@email.com', CURDATE(),'asdads');
insert into rsvp(name, email, confirm_date, comments) values('john Lee', 'hi4@email.com', null,'asdadsssssss');


