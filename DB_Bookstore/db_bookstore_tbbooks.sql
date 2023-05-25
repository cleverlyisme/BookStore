CREATE DATABASE  IF NOT EXISTS `db_bookstore`;
USE `db_bookstore`;

CREATE TABLE `tbbooks` (
  `IDBook` char(15) NOT NULL,
  `Title` char(50) NOT NULL,
  `Author` char(50) NOT NULL,
  `Category` char(50) NOT NULL,
  `Publisher` char(50) NOT NULL,
  `PublishedAt` date NOT NULL,
  `Price` double NOT NULL,
  `Amount` int NOT NULL,
  PRIMARY KEY (`IDBook`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `tbbooks` WRITE;
INSERT INTO `tbbooks` VALUES ('01','Adventures of Sherlock Homles E.1','Arthur Conan Doyle','Detective','NXB Kim Dong','2020-10-12',200,1),('02','Hercules Poirot','Christie Agatha','Detective','NXB Tuoi Tre','2010-07-28',190,4),('03','Doraemon E.1','Fujiko','Comedy','NXB Kim Dong','2012-06-28',150,6),('04','Conan','Aoyama Gosho','Detective','NXB Kim Dong','2010-08-14',100,11),('05','One Piece','Echiro Oda','Comedy','Comedy','2010-01-12',300,11),('123','13213','123','Detective','123','1231-10-12',123,123);
UNLOCK TABLES;
