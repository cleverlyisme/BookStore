CREATE DATABASE  IF NOT EXISTS `db_bookstore`;
USE `db_bookstore`;

CREATE TABLE `tbbills` (
  `IDBill` int NOT NULL,
  `IDCustomer` char(15) NOT NULL,
  `IDBook` char(15) NOT NULL,
  `Date` date NOT NULL,
  `Amount` int NOT NULL,
  `Total` double NOT NULL,
  KEY `IDCustomer_idx` (`IDCustomer`),
  KEY `IDBook_idx` (`IDBook`),
  CONSTRAINT `IDBook` FOREIGN KEY (`IDBook`) REFERENCES `tbbooks` (`IDBook`),
  CONSTRAINT `IDCustomer` FOREIGN KEY (`IDCustomer`) REFERENCES `tbcustomers` (`IDCustomer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `tbbills` WRITE;
INSERT INTO `tbbills` VALUES (2,'01','02','2022-09-12',1,190),(3,'01','03','2022-09-12',1,150),(4,'01','01','2021-10-12',1,200),(5,'01','02','2021-10-12',1,190),(6,'01','02','2022-10-12',1,190),(7,'01','03','2022-10-12',1,150),(8,'01','01','2022-10-12',1,200),(9,'01','02','2022-10-14',1,190),(9,'01','01','2022-10-13',1,200),(10,'01','02','2018-10-16',3,570),(11,'04','04','2022-10-16',1,100),(12,'04','04','2022-10-16',1,100),(13,'04','04','2022-10-16',2,200),(14,'02','02','2022-10-17',1,190),(15,'02','01','2022-10-17',1,200),(16,'02','01','2022-10-18',1,200);
UNLOCK TABLES;
