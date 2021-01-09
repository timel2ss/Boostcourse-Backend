-- ddl.sql
create schema BusinessCard;
use BusinessCard;

create table BusinessCard (
	name varchar(20) not null,
	phone varchar(20) not null,
	companyName varchar(20) not null,
	createDate date,
	primary key(phone)
);