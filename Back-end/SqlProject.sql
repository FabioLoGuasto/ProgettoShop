CREATE DATABASE  IF NOT EXISTS `negozio_scarpe` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `negozio_scarpe`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: negozio_scarpe
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `id_articolo` int NOT NULL AUTO_INCREMENT,
  `codice` varchar(45) DEFAULT NULL,
  `taglia` int DEFAULT '0',
  `negozio_id` int NOT NULL,
  `brand` varchar(45) DEFAULT NULL,
  `categoria` varchar(45) DEFAULT NULL,
  `prezzo` double DEFAULT NULL,
  `sconto` int DEFAULT '0',
  `stagione` varchar(45) DEFAULT NULL,
  `venduto` tinyint DEFAULT '1',
  `fornitore_id` int NOT NULL,
  `transazione_id` int DEFAULT NULL,
  PRIMARY KEY (`id_articolo`),
  KEY `fk_verso_transaction_idx` (`transazione_id`),
  KEY `fk_verso_shop_idx` (`negozio_id`),
  KEY `FK1tdpvvharg6kr4npmf8i9xgqa` (`fornitore_id`),
  CONSTRAINT `FK1tdpvvharg6kr4npmf8i9xgqa` FOREIGN KEY (`fornitore_id`) REFERENCES `supplier` (`id_fornitore`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_verso_shop` FOREIGN KEY (`negozio_id`) REFERENCES `shop` (`id_univoco_negozio`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_verso_transaction` FOREIGN KEY (`transazione_id`) REFERENCES `transaction` (`id_transazione`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3707 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (12,'A002',18,6,'ADIDAS','K',49.99,0,'SS23',0,3,902),(102,'A011',37,1,'LUMBERJACK','W',129.99,0,'SS23',0,2,1),(902,'A012',40,3,'SKECHERS','M',59.99,0,'SS23',0,6,1003),(1052,'A012',43,4,'SKECHERS','M',59.99,0,'SS23',0,6,952),(1202,'A012',43,4,'SKECHERS','M',59.99,0,'SS23',0,6,1),(1402,'A013',19,1,'ADIDAS','K',39.99,0,'SS23',0,3,902),(3706,'A014',40,1,'NIKE','S',99.99,0,'SS23',0,2,NULL);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_seq`
--

DROP TABLE IF EXISTS `article_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_seq`
--

LOCK TABLES `article_seq` WRITE;
/*!40000 ALTER TABLE `article_seq` DISABLE KEYS */;
INSERT INTO `article_seq` VALUES (3801);
/*!40000 ALTER TABLE `article_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fidelity_client`
--

DROP TABLE IF EXISTS `fidelity_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fidelity_client` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `cf` varchar(45) DEFAULT NULL,
  `localita` varchar(45) DEFAULT NULL,
  `provincia` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=503 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fidelity_client`
--

LOCK TABLES `fidelity_client` WRITE;
/*!40000 ALTER TABLE `fidelity_client` DISABLE KEYS */;
INSERT INTO `fidelity_client` VALUES (1,'CF0011','MILANO','MI'),(2,'CF0022','LIMBIATE','MB'),(3,'CF0033','LISSONE','MB'),(4,'CF0044','SEREGNO','MB'),(5,'CF0055','SESTO','MI'),(6,'CF0066','ROMA','RM'),(7,'CF0077','ROMA','RM'),(8,'CF0088','BOLZANO','BZ'),(9,'CF0099','BRESCIA','BS'),(10,'CF1100','CURNO','BG'),(11,'CF1111','NAPOLI','NP'),(12,'CF5555','CATANIA','CT'),(13,'CF1133','PADOVA','PD'),(14,'CF1144','CATANIA','CT'),(15,'CF1155','MODENA','MN'),(16,'CF1144','MODENA','MN');
/*!40000 ALTER TABLE `fidelity_client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fidelity_client_seq`
--

DROP TABLE IF EXISTS `fidelity_client_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fidelity_client_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fidelity_client_seq`
--

LOCK TABLES `fidelity_client_seq` WRITE;
/*!40000 ALTER TABLE `fidelity_client_seq` DISABLE KEYS */;
INSERT INTO `fidelity_client_seq` VALUES (601);
/*!40000 ALTER TABLE `fidelity_client_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop` (
  `id_univoco_negozio` int NOT NULL AUTO_INCREMENT,
  `numero_negozio` int DEFAULT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `localita` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_univoco_negozio`)
) ENGINE=InnoDB AUTO_INCREMENT=1254 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
INSERT INTO `shop` VALUES (1,92,'LISSONE','MONZA E BRIANZA'),(2,108,'SEREGNO','MONZA E BRIANZA'),(3,104,'RHO','MILANO'),(4,233,'CINISELLO BALSAMO','MILANO'),(5,255,'SETTIMO MILANESE','MILANO'),(6,150,'ROMA TIBURTINA','ROMA'),(7,111,'LIMBIATE','MONZA E BRIANZA'),(8,97,'CORMANO','MILANO'),(9,103,'LONATO','BRESCIA'),(1102,185,'FIRENZE GIOTTO','FIRENZE'),(1202,86,'SONDRIO','SO'),(1252,187,'SIENA','SI'),(1253,87,'THIENE','TH');
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop_seq`
--

DROP TABLE IF EXISTS `shop_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_seq`
--

LOCK TABLES `shop_seq` WRITE;
/*!40000 ALTER TABLE `shop_seq` DISABLE KEYS */;
INSERT INTO `shop_seq` VALUES (1351);
/*!40000 ALTER TABLE `shop_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `id_fornitore` int NOT NULL AUTO_INCREMENT,
  `cod_fornitore` int DEFAULT NULL,
  `ragione_sociale` varchar(45) DEFAULT NULL,
  `nazione` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_fornitore`)
) ENGINE=InnoDB AUTO_INCREMENT=853 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (2,1122,'ROX S.R.L.','INDONESIA'),(3,1133,'PROJECT S.PA.','CINA'),(4,1144,'ASTRO S.R.L.','VIETNAM'),(5,1155,'OCTO S.P.A.','FRANCIA'),(6,1166,'CUNO S.P.A.','AAAA'),(7,1177,'ZARD S.R.L.','GIAPPONE');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_seq`
--

DROP TABLE IF EXISTS `supplier_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_seq`
--

LOCK TABLES `supplier_seq` WRITE;
/*!40000 ALTER TABLE `supplier_seq` DISABLE KEYS */;
INSERT INTO `supplier_seq` VALUES (951);
/*!40000 ALTER TABLE `supplier_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id_transazione` int NOT NULL AUTO_INCREMENT,
  `numero_tessera` int DEFAULT NULL,
  `cliente_id` int NOT NULL,
  PRIMARY KEY (`id_transazione`),
  KEY `fk_verso_fidelity_client_idx` (`cliente_id`),
  CONSTRAINT `fk_verso_fidelity_client` FOREIGN KEY (`cliente_id`) REFERENCES `fidelity_client` (`id_cliente`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1453 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,177774,1),(902,232002,2),(952,15613,3),(1003,2589667,4),(1452,15613,3);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_seq`
--

DROP TABLE IF EXISTS `transaction_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_seq`
--

LOCK TABLES `transaction_seq` WRITE;
/*!40000 ALTER TABLE `transaction_seq` DISABLE KEYS */;
INSERT INTO `transaction_seq` VALUES (1551);
/*!40000 ALTER TABLE `transaction_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-19 20:37:59
