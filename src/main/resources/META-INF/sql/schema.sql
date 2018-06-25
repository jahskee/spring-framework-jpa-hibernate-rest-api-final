DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) NOT NULL,
  `isbn` varchar(10) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `publisher` varchar(100) DEFAULT NULL,
  `price_decimal` decimal(6,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
);