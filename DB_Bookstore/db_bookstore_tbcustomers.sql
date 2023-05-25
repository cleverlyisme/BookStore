CREATE DATABASE  IF NOT EXISTS `db_bookstore`;
USE `db_bookstore`;

CREATE TABLE `tbcustomers` (
  `IDCustomer` char(15) NOT NULL,
  `Name` char(50) NOT NULL,
  `Phone` int NOT NULL,
  `Email` char(50) NOT NULL,
  `Address` char(50) NOT NULL,
  PRIMARY KEY (`IDCustomer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `tbcustomers` WRITE;
INSERT INTO `tbcustomers` VALUES ('01','Thomas',337223434,'tuananh@gmail.com','VN'),('02','Tom',989828930,'tom@gmail.com','America'),('03','Cleverly',167496868,'tuananhdao28@gmail.com','HN, Vn'),('04','Test1',123456789,'test@gmail.com','Hai Phong, VN'),('05','1',1,'1','1'),('06','2',1,'1','1'),('123123','123123123',123,'123','123');
