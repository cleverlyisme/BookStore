CREATE DATABASE  IF NOT EXISTS `db_bookstore`;
USE `db_bookstore`;

CREATE TABLE `tbaccounts` (
  `username` char(30) NOT NULL,
  `password` char(20) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `tbaccounts` WRITE;
INSERT INTO `tbaccounts` VALUES ('2313','123'),('admin','admin'),('admin123','admin123'),('test','test'),('test123','test123');
UNLOCK TABLES;
