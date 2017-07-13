CREATE DATABASE  IF NOT EXISTS `portafoglio` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `portafoglio`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: portafoglio
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `portafoglio`
--

DROP TABLE IF EXISTS `portafoglio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `portafoglio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `importo` float DEFAULT NULL,
  `data` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portafoglio`
--

LOCK TABLES `portafoglio` WRITE;
/*!40000 ALTER TABLE `portafoglio` DISABLE KEYS */;
INSERT INTO `portafoglio` VALUES (1,'spesa',-62.79,'2012-02-04'),(2,'occhiali',-30.45,'2012-03-08'),(3,'dentista',-65.97,'2012-04-14'),(4,'stipendio',1864.35,'2012-05-21'),(5,'multa',-36.64,'2012-06-30'),(6,'occhiali',-74.56,'2012-07-17'),(7,'aumento',164.64,'2012-08-12'),(8,'stipendio',1943.64,'2012-09-09'),(9,'latte',-2.57,'2012-10-23'),(10,'spesa',-131.24,'2013-01-15'),(11,'multa',-61.53,'2013-02-18'),(12,'tagliando',-93.24,'2013-04-02'),(13,'regalo',-49.99,'2013-04-12'),(14,'bicicletta',-78.45,'2013-05-15'),(15,'dentista',-74.34,'2013-06-29'),(16,'stipendio',1648.56,'2013-07-24'),(17,'spesa',-35.64,'2013-08-13'),(18,'latte',-2.31,'2013-09-02'),(19,'assicurazione',-487.35,'2012-05-28'),(20,'spesa',-81.34,'2013-10-23'),(21,'aumento',61.23,'2013-11-19'),(22,'regalo',-231.41,'2013-12-24'),(23,'regalo',-311.13,'2013-12-25'),(24,'occhiali',-51.88,'2014-01-02'),(25,'stipendio',1023.31,'2014-01-13'),(26,'latte',-1.84,'2014-02-01'),(27,'multa',-194.25,'2014-02-20'),(28,'affitto',-620.49,'2014-03-03'),(29,'assicurazione',-724.12,'2012-06-03');
/*!40000 ALTER TABLE `portafoglio` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-13 10:55:23
