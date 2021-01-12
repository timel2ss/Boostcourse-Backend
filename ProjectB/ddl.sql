-- ddl.sql
CREATE SCHEMA guestbook;
USE guestbook;

CREATE TABLE guestbook (
  id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  content TEXT NOT NULL,
  regdate DATETIME,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 CREATE TABLE log(
 	id bigint(20) unsigned NOT NULL auto_increment,
 	ip varchar(255) NOT NULL,
 	method varchar(10) NOT NULL,
 	regdate datetime,
 	PRIMARY KEY (id)
 );