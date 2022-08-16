-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dealership_demo
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cars` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `brand` varchar(255) NOT NULL,
  `model` varchar(255) NOT NULL,
  `stock` int NOT NULL,
  `region_id` bigint NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKremgq7ftbcpj1nab32x62cpm` (`region_id`),
  CONSTRAINT `FKremgq7ftbcpj1nab32x62cpm` FOREIGN KEY (`region_id`) REFERENCES `regions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES (10,'Volkswagen','Golf 4',5,4,''),(11,'Volkswagen','Golf 4',3,3,''),(12,'Volkswagen','Golf 5',10,4,''),(13,'Skoda','Octavia',11,4,''),(14,'Skoda','Octavia',2,3,''),(15,'Renault','Thalia',4,4,''),(16,'Renault','Thalia',4,2,''),(17,'Audi','R8',9,2,'This car is an Audi R8.'),(18,'Audi','R8',32,1,'American Audi R8 description.'),(19,'Audi','A4',11,1,''),(20,'Audi','A4',11,2,''),(21,'Toyota','Yaris GR',11,5,'Toyota Yaris GR car from Japan.'),(22,'Toyota','GR Supra 5',1,5,''),(23,'Toyota','Yaris GR',2,1,''),(28,'Skoda','Octavia',2,2,''),(29,'Tesla','Model S',10,5,''),(30,'Tesla','Model X',3,5,''),(31,'Tesla','Model 3',20,1,''),(32,'Renault','Thalia',8,3,''),(33,'Ford','Mustang Shelby GT500',3,1,'This is a very good car, yes.'),(34,'Ford','Mustang Shelby GT500',2,3,''),(35,'Ford','GT',3,2,''),(37,'Mercedes','Benz',3,3,'');
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regions`
--

DROP TABLE IF EXISTS `regions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1m9qnhbk56c8iskxvfupln9me` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regions`
--

LOCK TABLES `regions` WRITE;
/*!40000 ALTER TABLE `regions` DISABLE KEYS */;
INSERT INTO `regions` VALUES (1,'America'),(3,'Germany'),(4,'Hungary'),(5,'Japan'),(2,'United-Kingdom');
/*!40000 ALTER TABLE `regions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `admin` tinyint(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `region_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4muym4ujsr1xfh4qc3wsmmrhe` (`region_id`),
  CONSTRAINT `FK4muym4ujsr1xfh4qc3wsmmrhe` FOREIGN KEY (`region_id`) REFERENCES `regions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,1,'$2a$10$H1aStOUvCMaamfM2XLDa1eSZXtTFZ8AUI/aQdkrY7f74sDZdH7bhe','dealership-admin',4),(3,0,'$2a$10$NJCw/WvHmJ0E/KMWVHTpKeyYMGwnLQkywksyrbjBGwkWV9UpeNBdS','hungarian-user-1',4),(4,0,'$2a$10$hF7TIUEoK1qLwMqGHL.V8emvDxIIPo8VxP/yXYHlxqCGc/jJmbZvy','german-user-1',3),(5,0,'$2a$10$NkIJC96N75Vcua22PZRkzuiRfiaUt2DPbmFQ0QmF7CpLq2QpaG9Sa','japan-user-1',5),(6,0,'$2a$10$gDZxKHgqcDtGS8woUFLaP..u0RFcncUAV3bWAoBbbEv53UPyjTiKu','japan-user-2',5),(8,0,'$2a$10$aBVt8cIeRbqkukvXqThixOZ8cJ2DAAPuuaZ9ZhaxJFyACDcqDn34q','american-user-1',1),(9,0,'$2a$10$HAvZZp2JTuG4/Jch3h4QuejlPLFQTcxgMb9OXcNwFw2HEL3PAW3zC','uk-user-1',2),(10,0,'$2a$10$CZxM/qAZWYaBRS23oaJCfu9VSgvDQoWbROaLOxWlV59ySe64H8uc.','american-user-2',1),(11,0,'$2a$10$vXvbih2m8Lylzy25e5Vf/.12k6YhrOqOnDuPD2EjxYKnAnrK0WNGW','uk-user-2',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-16 15:52:25
