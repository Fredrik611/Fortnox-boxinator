-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: shipping
-- ------------------------------------------------------
-- Server version	5.7.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `boxes`
--

DROP TABLE IF EXISTS `boxes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `boxes` (
  `idboxes` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `weight` int(11) NOT NULL,
  `color` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `price` int(11) NOT NULL,
  PRIMARY KEY (`idboxes`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boxes`
--

LOCK TABLES `boxes` WRITE;
/*!40000 ALTER TABLE `boxes` DISABLE KEYS */;
INSERT INTO `boxes` VALUES (1,'Fredrik Andersson',2,'(255,0,0)','aus',14),(2,'Oskar heh',2,'(30,255,0)','aus',14),(3,'cxvxcvxvccxv',34,'(30,255,0)','chi',136),(4,'påpåpåpåpå',13,'(200,86,86)','swe',16),(5,'Test Testersson',13,'(112,72,72)','bra',13),(6,'Test Testersson',13,'(112,72,72)','bra',13),(7,'Test Testersson',13,'(112,72,72)','bra',111),(8,'sdf',32,'(213,57,57)','chi',128),(9,'Tester',12,'(155,155,155)','swe',15),(10,'Tester',12,'(155,155,155)','swe',15),(11,'Tester',12,'(155,155,155)','swe',15),(12,'Tester',12,'(155,155,155)','swe',15),(13,'Tester',12,'(155,155,155)','swe',15);
/*!40000 ALTER TABLE `boxes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-27 17:23:18
