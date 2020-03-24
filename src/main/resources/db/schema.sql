CREATE DATABASE lalafood;

use lalafood;

CREATE TABLE Orders (
	id BIGINT AUTO_INCREMENT,
	distance double NOT NULL,
	status varchar(50) NOT NULL,
	PRIMARY KEY(id)
) engine = INNODB;