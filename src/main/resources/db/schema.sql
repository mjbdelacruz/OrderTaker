DROP TABLE IF EXISTS `Orders`;

CREATE TABLE `Orders` (
	`id` BIGINT AUTO_INCREMENT,
	`distance` double NOT NULL,
	`status` varchar(50) NOT NULL,
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;