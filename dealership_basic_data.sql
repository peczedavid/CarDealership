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
-- Table structure for table `car_regions`
--

DROP TABLE IF EXISTS `car_regions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car_regions` (
  `car_id` bigint NOT NULL,
  `region_id` int NOT NULL,
  PRIMARY KEY (`car_id`,`region_id`),
  KEY `FK175s7x989qv8k2bc1t7g88ui4` (`region_id`),
  CONSTRAINT `FK175s7x989qv8k2bc1t7g88ui4` FOREIGN KEY (`region_id`) REFERENCES `regions` (`id`),
  CONSTRAINT `FK4frld08ux1r3trdui2i2kxb6q` FOREIGN KEY (`car_id`) REFERENCES `cars` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_regions`
--

LOCK TABLES `car_regions` WRITE;
/*!40000 ALTER TABLE `car_regions` DISABLE KEYS */;
INSERT INTO `car_regions` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(11,2),(12,2),(6,4),(7,4),(9,4),(10,4),(23,4);
/*!40000 ALTER TABLE `car_regions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cars` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `brand` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `stock` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES (1,'Tesla','Model S',0),(2,'Tesla','Model 3',0),(3,'Tesla','Model X',4),(4,'Ford','Focus RS',0),(5,'Ford','Mustang Shelby GT500',0),(6,'Volkswagen','Golf 4',0),(7,'Volkswagen','Golf 5',0),(9,'Audi','R8',0),(10,'Audi','A4',0),(11,'Toyota','Yaris GR',0),(12,'Toyota','GR Supra 5',0),(23,'Volkswagen','Golf 6',0);
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regions`
--

DROP TABLE IF EXISTS `regions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regions`
--

LOCK TABLES `regions` WRITE;
/*!40000 ALTER TABLE `regions` DISABLE KEYS */;
INSERT INTO `regions` VALUES (1,'America'),(2,'Japan'),(4,'Germany');
/*!40000 ALTER TABLE `regions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_regions`
--

DROP TABLE IF EXISTS `user_regions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_regions` (
  `user_id` bigint NOT NULL,
  `region_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`region_id`),
  KEY `FKknsinx0duqer0xsnqjmjc9heh` (`region_id`),
  CONSTRAINT `FKknsinx0duqer0xsnqjmjc9heh` FOREIGN KEY (`region_id`) REFERENCES `regions` (`id`),
  CONSTRAINT `FKmtfgfn1s2lfkpgicunq4sw80c` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_regions`
--

LOCK TABLES `user_regions` WRITE;
/*!40000 ALTER TABLE `user_regions` DISABLE KEYS */;
INSERT INTO `user_regions` VALUES (1,1),(7,1),(8,1),(9,1),(2,2),(3,2),(4,2),(5,4),(6,4);
/*!40000 ALTER TABLE `user_regions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1),(1,2),(2,2),(3,2),(4,2),(5,2),(6,2),(7,2),(8,2),(9,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin@dealership.com','$2a$10$MPqWpN.eRCUg.1Z3TvaOyuz6jnK62fNfYO1x6.kUhk4pXqEzfUuvq','admin'),(2,'japan-user-1@dealership.com','$2a$10$XMaEflAnEMNS1rVRx0yXr.XewTavXBtbe51XK1/EcIdkZbwSOhJDa','japan-user-1'),(3,'japan-user-2@dealership.com','$2a$10$PKnXwidmGw6Nj1SDqT7cw.gwYDIiWRXg8LrlROGWTu3d9YxkG8TNK','japan-user-2'),(4,'japan-user-3@dealership.com','$2a$10$D/rRwEam6o..lFbRa0iFU.WyJcmxMhvr2lnvF37QH1pHI78OOtDLK','japan-user-3'),(5,'german-user-1@dealership.com','$2a$10$i0LCvKAERMkpE2yyoRqoEum.9H.tNCY5QWdFgtm1xIV1eTnB2Vsre','german-user-1'),(6,'german-user-2@dealership.com','$2a$10$wTfzk1XderbIeTaSPEI7Auz4hrN.WIMJYc41ONhX52uSoVNvn3HSW','german-user-2'),(7,'american-user-1@dealership.com','$2a$10$NBHDSjo6J84EZKV3lKTsCeGCPZOr9DodC/MSZpmhZU8ypp7OfwAlm','american-user-1'),(8,'american-user-2@dealership.com','$2a$10$siUXMfkzsTE2DllR57ZcwOR11qYAhP3nS0kpnX1KIYnijwYHgDBR2','american-user-2'),(9,'american-user-3@dealership.com','$2a$10$YxgNWXL3qvw4nIC26GAhv.FpDMhq4Z9ZzJ44zHrlyHomFJNLH8/A.','american-user-3');
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

-- Dump completed on 2022-08-01 16:17:36
