-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: papeleria
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `apartado`
--

DROP TABLE IF EXISTS `apartado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apartado` (
  `telefono` varchar(10) NOT NULL,
  `fecha_apartado` date NOT NULL,
  `descripcion` varchar(300) NOT NULL,
  `id_tipo` int NOT NULL,
  `total_pagar` double NOT NULL,
  `cancelado` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`telefono`,`fecha_apartado`),
  KEY `fk_tipo_apartado_idx` (`id_tipo`),
  CONSTRAINT `fk_telefono_apartado` FOREIGN KEY (`telefono`) REFERENCES `cliente` (`telefono`) ON UPDATE CASCADE,
  CONSTRAINT `fk_tipo_apartado` FOREIGN KEY (`id_tipo`) REFERENCES `tipo` (`id_tipo`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `aportaciones_apartado`
--

DROP TABLE IF EXISTS `aportaciones_apartado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aportaciones_apartado` (
  `telefono` varchar(10) NOT NULL,
  `fecha_apartado` date NOT NULL,
  `fecha_venta` timestamp NOT NULL,
  PRIMARY KEY (`telefono`,`fecha_apartado`,`fecha_venta`),
  KEY `fk_aportaciones_apartado_fecha_idx` (`fecha_apartado`),
  KEY `fk_aportaciones_venta_tipo_fecha_idx` (`fecha_venta`),
  CONSTRAINT `fk_aportaciones_apartado_telefono` FOREIGN KEY (`telefono`, `fecha_apartado`) REFERENCES `apartado` (`telefono`, `fecha_apartado`) ON UPDATE CASCADE,
  CONSTRAINT `fk_aportaciones_venta_tipo_fecha` FOREIGN KEY (`fecha_venta`) REFERENCES `venta_tipo` (`fecha_venta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  PRIMARY KEY (`telefono`),
  UNIQUE KEY `telefono_UNIQUE` (`telefono`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gastos`
--

DROP TABLE IF EXISTS `gastos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gastos` (
  `id_gasto` int NOT NULL,
  `fecha_gasto` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cantidad_gasto` double NOT NULL,
  `id_tipo` int NOT NULL,
  `id_proveedor` int NOT NULL,
  PRIMARY KEY (`id_gasto`,`id_proveedor`,`fecha_gasto`),
  KEY `tipo_gasto_idx` (`id_tipo`),
  KEY `proveedor_gasto_idx` (`id_proveedor`),
  CONSTRAINT `proveedor_gasto` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id_proveedor`) ON UPDATE CASCADE,
  CONSTRAINT `tipo_gasto` FOREIGN KEY (`id_tipo`) REFERENCES `tipo` (`id_tipo`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedor` (
  `id_proveedor` int NOT NULL AUTO_INCREMENT,
  `nombre_proveedor` varchar(45) NOT NULL,
  `existe_proveedor` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id_proveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipo`
--

DROP TABLE IF EXISTS `tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo` (
  `id_tipo` int NOT NULL AUTO_INCREMENT,
  `nombre_tipo` varchar(45) NOT NULL,
  `existe_tipo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_tipo`),
  UNIQUE KEY `idTipo_UNIQUE` (`id_tipo`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `venta`
--

DROP TABLE IF EXISTS `venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venta` (
  `id_venta` int NOT NULL,
  `fecha_venta` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `existe_venta` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_venta`,`fecha_venta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `venta_tipo`
--

DROP TABLE IF EXISTS `venta_tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venta_tipo` (
  `id_venta` int NOT NULL,
  `id_tipo` int NOT NULL,
  `cantidad_tipo` double NOT NULL,
  `existe` tinyint(1) NOT NULL DEFAULT '1',
  `fecha_venta` timestamp NOT NULL,
  PRIMARY KEY (`id_venta`,`fecha_venta`,`id_tipo`),
  KEY `id_tipo_venta_idx` (`id_tipo`),
  KEY `fecha_venta_venta_idx` (`fecha_venta`),
  CONSTRAINT `id_venta_venta` FOREIGN KEY (`id_venta`, `fecha_venta`) REFERENCES `venta` (`id_venta`, `fecha_venta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-17 17:56:05
