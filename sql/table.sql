-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: db_quanghungglasses
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `avatar` varchar(100) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `is_verified_email` bit(1) NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `status` bit(1) NOT NULL,
  `verification_code` varchar(6) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `verification_code_expired_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_q0uja26qgu1atulenwup9rxyr` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'2024-05-01 18:51:00.211000','2024-05-01 18:51:00.211000',NULL,'adminquanghung@gmail.com',_binary '','$2a$10$bc3JCgIrhZjW/..H1QEZXOjf5T/20nEdEmuoglXiBupiQJeHD/d52',_binary '',NULL,NULL),(2,'2024-05-01 22:39:55.511000','2024-05-01 22:39:55.511000',NULL,'trangtran@gmail.com',_binary '','$2a$10$UrpTiJPhtmrb0S4jbLoon.AGyPJbZ/JFQtqFNQlVxJki7XBion8n6',_binary '',NULL,NULL);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_role`
--

DROP TABLE IF EXISTS `account_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_role` (
  `account_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKrs2s3m3039h0xt8d5yhwbuyam` (`role_id`),
  KEY `FK1f8y4iy71kb1arff79s71j0dh` (`account_id`),
  CONSTRAINT `FK1f8y4iy71kb1arff79s71j0dh` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
  CONSTRAINT `FKrs2s3m3039h0xt8d5yhwbuyam` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_role`
--

LOCK TABLES `account_role` WRITE;
/*!40000 ALTER TABLE `account_role` DISABLE KEYS */;
INSERT INTO `account_role` VALUES (1,1),(2,3);
/*!40000 ALTER TABLE `account_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `address` varchar(100) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `city` varchar(20) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `district` varchar(20) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `fullname` varchar(30) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `phone` varchar(10) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `ward` varchar(20) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `customer_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK93c3js0e22ll1xlu21nvrhqgg` (`customer_id`),
  CONSTRAINT `FK93c3js0e22ll1xlu21nvrhqgg` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banner`
--

DROP TABLE IF EXISTS `banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banner` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `image` varchar(100) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `status` bit(1) NOT NULL,
  `staff_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8gi72otto0het65pm26sv1j3q` (`staff_id`),
  CONSTRAINT `FK8gi72otto0het65pm26sv1j3q` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banner`
--

LOCK TABLES `banner` WRITE;
/*!40000 ALTER TABLE `banner` DISABLE KEYS */;
/*!40000 ALTER TABLE `banner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rdxh7tq2xs66r485cc8dkxt77` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,'2024-05-01 18:51:00.656000','2024-05-01 18:51:00.656000','Rayban'),(2,'2024-05-01 18:51:00.672000','2024-05-01 18:51:00.672000','Gucci'),(3,'2024-05-01 18:51:00.686000','2024-05-01 18:51:00.686000','Dior'),(4,'2024-05-01 18:51:00.698000','2024-05-01 18:51:00.698000','Chanel'),(5,'2024-05-01 18:51:00.710000','2024-05-01 18:51:00.710000','Prada'),(6,'2024-05-01 18:51:00.722000','2024-05-01 18:51:00.722000','Burberry'),(7,'2024-05-01 18:51:00.734000','2024-05-01 18:51:00.734000','Versace'),(8,'2024-05-01 18:51:00.746000','2024-05-01 18:51:00.746000','Dolce & Gabbana');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `customer_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdebwvad6pp1ekiqy5jtixqbaj` (`customer_id`),
  CONSTRAINT `FKdebwvad6pp1ekiqy5jtixqbaj` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,'2024-05-01 22:39:55.521000','2024-05-01 22:39:55.521000',2);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_details`
--

DROP TABLE IF EXISTS `cart_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `quantity` int NOT NULL,
  `cart_id` bigint NOT NULL,
  `product_details_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhq1m50l0ke2fkqxxd6ubo3x4b` (`cart_id`),
  KEY `FKtibhs55lvuicxc2r84wqiynt` (`product_details_id`),
  CONSTRAINT `FKhq1m50l0ke2fkqxxd6ubo3x4b` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
  CONSTRAINT `FKtibhs55lvuicxc2r84wqiynt` FOREIGN KEY (`product_details_id`) REFERENCES `product_details` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_details`
--

LOCK TABLES `cart_details` WRITE;
/*!40000 ALTER TABLE `cart_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `description` varchar(100) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `name` varchar(30) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `slug` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_46ccwnsi9409t36lurvtyljak` (`name`),
  UNIQUE KEY `UK_hqknmjh5423vchi4xkyhxlhg2` (`slug`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'2024-05-01 18:51:00.275000','2024-05-01 18:51:00.275000','Kính thời trang','Kính thời trang','kinh-thoi-trang',_binary ''),(2,'2024-05-01 18:51:00.295000','2024-05-01 18:51:00.295000','Gọng kính cận','Gọng kính cận','gong-kinh-can',_binary ''),(3,'2024-05-01 18:51:00.316000','2024-05-01 18:51:00.316000','Tròng kính','Tròng kính','trong-kinh',_binary ''),(4,'2024-05-01 18:51:00.326000','2024-05-01 18:51:00.326000','Phụ kiện','Phụ kiện','phu-kien',_binary '');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `birthday` datetime(6) DEFAULT NULL,
  `fullname` varchar(30) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `gender` varchar(5) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `phone` varchar(10) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `address` varchar(200) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o3uty20c6csmx5y4uk2tc5r4m` (`phone`),
  KEY `FKn9x2k8svpxj3r328iy1rpur83` (`account_id`),
  CONSTRAINT `FKn9x2k8svpxj3r328iy1rpur83` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'2024-05-01 22:38:21.010000','2024-05-01 22:38:21.010000','2001-08-05 07:00:00.000000','Nguyễn Thị Lê','Nữ','0328845817','Xã Bảo Thanh, Tỉnh Phú Thọ',NULL),(2,'2024-05-01 22:39:55.377000','2024-05-01 22:39:55.524000','2000-12-24 07:00:00.000000','Trần Quỳnh Trang','Nữ','0123456789','Bãi Cháy, Quảng Ninh',2);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material`
--

DROP TABLE IF EXISTS `material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_j8lh9456buiw3bl8pg6kbuwln` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material`
--

LOCK TABLES `material` WRITE;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
INSERT INTO `material` VALUES (1,'2024-05-01 18:51:00.343000','2024-05-01 18:51:00.343000','Nhựa cứng'),(2,'2024-05-01 18:51:00.355000','2024-05-01 18:51:00.355000','Kim loại'),(3,'2024-05-01 18:51:00.365000','2024-05-01 18:51:00.365000','Nhựa pha kim loại'),(4,'2024-05-01 18:51:00.375000','2024-05-01 18:51:00.375000','Nhựa dẻo'),(5,'2024-05-01 18:51:00.385000','2024-05-01 18:51:00.385000','Nhựa càng titan'),(6,'2024-05-01 18:51:00.394000','2024-05-01 18:51:00.394000','Nhựa Ultem'),(7,'2024-05-01 18:51:00.404000','2024-05-01 18:51:00.404000','Nhựa Acetate'),(8,'2024-05-01 18:51:00.416000','2024-05-01 18:51:00.416000','Kim loại Titan'),(9,'2024-05-01 18:51:00.426000','2024-05-01 18:51:00.426000','Kim loại Thép không gỉ'),(10,'2024-05-01 18:52:43.004000','2024-05-01 18:52:43.004000','Da'),(11,'2024-05-01 18:52:43.054000','2024-05-01 18:52:43.054000','Polycarbonate');
/*!40000 ALTER TABLE `material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL,
  `price_original` double NOT NULL,
  `quantity` int NOT NULL,
  `order_id` bigint NOT NULL,
  `product_details_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  KEY `FKos61972xo18nahkjujx4ck9ys` (`product_details_id`),
  CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKos61972xo18nahkjujx4ck9ys` FOREIGN KEY (`product_details_id`) REFERENCES `product_details` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (1,12000,15000,1,1,13),(2,3000,5000,1,1,17),(3,300000,300000,1,2,57),(4,715000,715000,1,2,43);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `cancel_date` datetime(6) DEFAULT NULL,
  `cancel_reason` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `completed_date` datetime(6) DEFAULT NULL,
  `confirm_date` datetime(6) DEFAULT NULL,
  `created_date` datetime(6) NOT NULL,
  `delivery_date` datetime(6) DEFAULT NULL,
  `delivery_to_shipper_date` datetime(6) DEFAULT NULL,
  `eyeglass_prescription` varchar(100) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `fullname` varchar(30) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `note` varchar(255) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `order_status` int NOT NULL,
  `payment_date` datetime(6) DEFAULT NULL,
  `payment_method` int NOT NULL,
  `payment_status` bit(1) NOT NULL,
  `phone` varchar(255) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `receive_date` datetime(6) DEFAULT NULL,
  `customer_id` bigint NOT NULL,
  `staff_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK624gtjin3po807j3vix093tlf` (`customer_id`),
  KEY `FK4ery255787xl56k025fyxrqe9` (`staff_id`),
  CONSTRAINT `FK4ery255787xl56k025fyxrqe9` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`),
  CONSTRAINT `FK624gtjin3po807j3vix093tlf` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'Bãi Cháy, Quảng Ninh',NULL,NULL,'2024-05-01 22:40:41.263000','2024-05-01 22:40:41.263000','2024-05-01 22:40:41.278000','2024-05-01 22:40:41.263000','2024-05-01 22:40:41.263000',NULL,'Trần Quỳnh Trang','',5,'2024-05-01 22:40:41.263000',0,_binary '','0123456789','2024-05-01 22:40:41.263000',2,1),(2,'Xã Bảo Thanh, Tỉnh Phú Thọ',NULL,NULL,'2024-05-01 22:42:31.063000','2024-05-01 22:42:31.063000','2024-05-01 22:42:31.074000','2024-05-01 22:42:31.063000','2024-05-01 22:42:31.063000','orders/eyeglass_prescription/009a12e0-a5bc-4343-99dc-37a2a8c13717.png','Nguyễn Thị Lê','',5,'2024-05-01 22:42:31.063000',0,_binary '','0328845817','2024-05-01 22:42:31.063000',1,1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `origin`
--

DROP TABLE IF EXISTS `origin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `origin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9we3bjwe5odxu817807lbsesq` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `origin`
--

LOCK TABLES `origin` WRITE;
/*!40000 ALTER TABLE `origin` DISABLE KEYS */;
INSERT INTO `origin` VALUES (1,'2024-05-01 18:51:00.443000','2024-05-01 18:51:00.443000','Việt Nam'),(2,'2024-05-01 18:51:00.454000','2024-05-01 18:51:00.454000','Trung Quốc'),(3,'2024-05-01 18:51:00.467000','2024-05-01 18:51:00.467000','Hàn Quốc'),(4,'2024-05-01 18:51:00.478000','2024-05-01 18:51:00.478000','Nhật Bản'),(5,'2024-05-01 18:51:00.488000','2024-05-01 18:51:00.488000','Mỹ'),(6,'2024-05-01 18:51:00.499000','2024-05-01 18:51:00.499000','Pháp'),(7,'2024-05-01 18:51:00.510000','2024-05-01 18:51:00.510000','Đức'),(8,'2024-05-01 18:51:00.522000','2024-05-01 18:51:00.522000','Ý');
/*!40000 ALTER TABLE `origin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `description` longtext COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `price` double NOT NULL,
  `slug` varchar(100) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `status` bit(1) NOT NULL,
  `thumbnail` varchar(100) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `time_warranty` int NOT NULL,
  `brand_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  `material_id` bigint NOT NULL,
  `origin_id` bigint NOT NULL,
  `shape_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jmivyxk9rmgysrmsqw15lqr5b` (`name`),
  UNIQUE KEY `UK_88yb4l9100epddqsrdvxerhq9` (`slug`),
  KEY `FKs6cydsualtsrprvlf2bb3lcam` (`brand_id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  KEY `FKw04fq456sc4tk26tnbhvr59o` (`material_id`),
  KEY `FKsk257mxbg5s28xt71e90pv3xm` (`origin_id`),
  KEY `FKpfn0nmgr2iqf97lnj9bg0kl53` (`shape_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKpfn0nmgr2iqf97lnj9bg0kl53` FOREIGN KEY (`shape_id`) REFERENCES `shape` (`id`),
  CONSTRAINT `FKs6cydsualtsrprvlf2bb3lcam` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FKsk257mxbg5s28xt71e90pv3xm` FOREIGN KEY (`origin_id`) REFERENCES `origin` (`id`),
  CONSTRAINT `FKw04fq456sc4tk26tnbhvr59o` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'2024-05-01 20:58:36.616000','2024-05-01 20:58:36.616000','<p style=\"text-align: center;\"><span style=\"font-size: 18pt;\"><strong>K&Iacute;CH THƯỚC GỌNG K&Iacute;NH</strong></span></p>\r\n<table style=\"border-collapse: collapse; width: 100%; border-width: 0px; margin-left: auto; margin-right: auto;\" border=\"1\"><colgroup><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"></colgroup>\r\n<tbody>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/chieu_ngang_1.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://kinhmatlily.com/images/product_size/chieu_ngang.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_rong.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_cao.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/cau_mui.png\" width=\"180\" height=\"180\"></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\">147mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">143mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">56mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">57mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">20mm</td>\r\n</tr>\r\n</tbody>\r\n</table>','KÍNH NHỰA SODA POP 90038',490000,'kinh-nhua-soda-pop-90038',_binary '','products/f82a4fae-464c-4a42-a738-ab6238178dec.jpeg',10,1,2,4,1,2),(2,'2024-05-01 21:01:10.743000','2024-05-01 21:01:10.743000','<h1>HỘP DA FARELLO</h1>\r\n<p>Sang trọng, cứng, bảo vệ k&iacute;nh</p>','HỘP DA FARELLO',75000,'hop-da-farello',_binary '','products/1d967686-d5ef-43fb-99bd-46eadf86cec5.jpeg',0,1,4,1,1,8),(3,'2024-05-01 21:02:40.377000','2024-05-01 21:02:40.377000','<p style=\"text-align: center;\"><span style=\"font-size: 18pt;\"><strong>K&Iacute;CH THƯỚC GỌNG K&Iacute;NH</strong></span></p>\r\n<table style=\"border-collapse: collapse; width: 100%; border-width: 0px; margin-left: auto; margin-right: auto;\" border=\"1\"><colgroup><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"></colgroup>\r\n<tbody>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/chieu_ngang_1.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://kinhmatlily.com/images/product_size/chieu_ngang.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_rong.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_cao.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/cau_mui.png\" width=\"180\" height=\"180\"></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\">147mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">143mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">56mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">57mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">20mm</td>\r\n</tr>\r\n</tbody>\r\n</table>','KÍNH NHỰA 90029',250000,'kinh-nhua-90029',_binary '','products/6416a059-7657-44fe-a9d7-611035a982ec.jpeg',30,2,2,3,2,2),(4,'2024-05-01 21:05:05.486000','2024-05-01 21:05:05.486000','<p style=\"text-align: center;\"><span style=\"font-size: 18pt;\"><strong>K&Iacute;CH THƯỚC GỌNG K&Iacute;NH</strong></span></p>\r\n<table style=\"border-collapse: collapse; width: 100%; border-width: 0px; margin-left: auto; margin-right: auto;\" border=\"1\"><colgroup><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"></colgroup>\r\n<tbody>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/chieu_ngang_1.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://kinhmatlily.com/images/product_size/chieu_ngang.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_rong.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_cao.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/cau_mui.png\" width=\"180\" height=\"180\"></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\">147mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">143mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">56mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">57mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">20mm</td>\r\n</tr>\r\n</tbody>\r\n</table>','GỌNG KIM LOẠI 29120',250000,'gong-kim-loai-29120',_binary '','products/d0350393-276f-4377-a580-5738058d1ff8.jpeg',30,3,2,2,2,2),(5,'2024-05-01 21:05:27.085000','2024-05-01 21:11:46.933000','<h1>SET PHỤ KIỆN ỐC V&Iacute;T/ĐỆM MŨI</h1>','SET PHỤ KIỆN ỐC VÍT/ĐỆM MŨI',150000,'set-phu-kien-oc-vitdjem-mui',_binary '','products/b588ac58-5f83-4eb9-bcf3-fa054b178b16.jpeg',0,3,4,2,2,8),(6,'2024-05-01 21:08:30.002000','2024-05-01 21:08:30.002000','<h1>NƯỚC LAU K&Iacute;NH</h1>','NƯỚC LAU KÍNH',15000,'nuoc-lau-kinh',_binary '','products/e3209455-2e90-43d7-806d-fdd11deb98a2.jpeg',0,4,4,1,3,8),(7,'2024-05-01 21:12:34.344000','2024-05-01 21:12:34.344000','<p style=\"text-align: center;\"><span style=\"font-size: 18pt;\"><strong>K&Iacute;CH THƯỚC GỌNG K&Iacute;NH</strong></span></p>\r\n<table style=\"border-collapse: collapse; width: 100%; border-width: 0px; margin-left: auto; margin-right: auto;\" border=\"1\"><colgroup><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"></colgroup>\r\n<tbody>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/chieu_ngang_1.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://kinhmatlily.com/images/product_size/chieu_ngang.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_rong.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_cao.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/cau_mui.png\" width=\"180\" height=\"180\"></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\">147mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">143mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">56mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">57mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">20mm</td>\r\n</tr>\r\n</tbody>\r\n</table>','KÍNH NHỰA PHA KIM LOẠI 9376',350000,'kinh-nhua-pha-kim-loai-9376',_binary '','products/2835a0b4-df8b-4d5a-8efa-9cae37d8e5d6.png',30,4,2,3,3,3),(8,'2024-05-01 21:13:05.957000','2024-05-01 21:13:05.957000','<h1>KHĂN LAU K&Iacute;NH CHUY&Ecirc;N DỤNG</h1>','KHĂN LAU KÍNH CHUYÊN DỤNG',5000,'khan-lau-kinh-chuyen-dung',_binary '','products/c1e15643-7380-4233-b2cf-8023836dd32c.jpeg',0,5,4,10,4,8),(9,'2024-05-01 21:16:59.806000','2024-05-01 21:16:59.806000','<p style=\"text-align: center;\"><span style=\"font-size: 18pt;\"><strong>K&Iacute;CH THƯỚC GỌNG K&Iacute;NH</strong></span></p>\r\n<table style=\"border-collapse: collapse; width: 100%; border-width: 0px; margin-left: auto; margin-right: auto;\" border=\"1\"><colgroup><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"></colgroup>\r\n<tbody>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/chieu_ngang_1.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://kinhmatlily.com/images/product_size/chieu_ngang.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_rong.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_cao.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/cau_mui.png\" width=\"180\" height=\"180\"></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\">147mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">143mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">56mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">57mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">20mm</td>\r\n</tr>\r\n</tbody>\r\n</table>','KÍNH NHỰA CÀNG TITAN ALVA 3123',1200000,'kinh-nhua-cang-titan-alva-3123',_binary '','products/ed85feb3-8669-486a-8e32-d0916c2b3812.jpeg',90,4,2,5,4,3),(10,'2024-05-01 21:18:17.752000','2024-05-01 21:42:10.947000','<h1><span style=\"font-size: 18pt;\">MẮT K&Iacute;NH ĐỔI M&Agrave;U HOGA 1.56</span></h1>','MẮT KÍNH ĐỔI MÀU HOGA 1.56',690000,'mat-kinh-djoi-mau-hoga-156',_binary '','products/7e73cac0-5500-4dfa-8a42-c968e243f1dc.jpeg',0,6,3,11,5,5),(11,'2024-05-01 21:22:05.208000','2024-05-01 21:42:43.692000','<h3 style=\"text-align: center;\">Tập đo&agrave;n Chemi Lens được th&agrave;nh lập v&agrave;o năm 1985 &amp; Viện nghi&ecirc;n cứu quang học của&nbsp;CHEMILENS&nbsp;được th&agrave;nh lập năm 1995</h3>\r\n<h3 style=\"text-align: center;\">Chemi được c&aacute;c tổ chức y tế v&agrave; c&aacute;c b&aacute;c sĩ nh&atilde;n khoa khuy&ecirc;n d&ugrave;ng bởi chất lượng vượt trội đi c&ugrave;ng gi&aacute; th&agrave;nh hợp l&iacute; ph&ugrave; hợp với người ti&ecirc;u d&ugrave;ng Việt Nam.</h3>\r\n<h3 style=\"text-align: center;\"><strong>TR&Ograve;NG K&Iacute;NH CHEMI ASP CRYSTAL U2 COATED&nbsp;tr&ecirc;n bề mặt tr&ograve;ng k&iacute;nh h&igrave;nh th&agrave;nh một lớp bảo vệ tự nhi&ecirc;n, độ xuy&ecirc;n suốt cao hơn 99%.</strong></h3>\r\n<p style=\"text-align: center;\"><strong><img src=\"https://matkinhtamduc.com/wp-content/uploads/2017/10/noi-dung-trong-kinh-chemi-u2-1-800x332.jpg\"></strong></p>\r\n<p style=\"text-align: center;\"><strong><img src=\"https://matkinhtamduc.com/wp-content/uploads/2018/10/thong-so-ky-thuat-chiet-suat-trong-kinh-1-603x400.jpg\"></strong></p>\r\n<p style=\"text-align: center;\">K&Iacute;NH MẮT LILY xin ph&eacute;p chỉ nhận độ mắt&nbsp;<em class=\"Highlight\">sau&nbsp;</em>để<em class=\"Highlight\">&nbsp;đảm bảo&nbsp;</em>chất lượng sản phẩm mắt k&iacute;nh.</p>\r\n<p style=\"text-align: center;\">- Độ Cận : Kh&ocirc;ng độ - 10 độ</p>\r\n<p style=\"text-align: center;\">- Độ Loạn : Kh&ocirc;ng độ - 2 độ.</p>\r\n<p style=\"text-align: center;\">Với k&iacute;nh đổi m&agrave;u từ kh&ocirc;ng độ - 1,5 độ (Cần cung cấp SỐ TRỤC hoặc GIẤY,SỔ ĐO KH&Aacute;M gần nhất)</p>\r\n<p style=\"text-align: center;\">+ Kh&ocirc;ng nhận Mắt Viễn.</p>\r\n<p style=\"text-align: center;\">+ Gi&aacute; của sản phẩm l&agrave; gi&aacute; 1 cặp mắt k&iacute;nh cận.</p>\r\n<p style=\"text-align: center;\">+ Nhận mắt cả cận cả loạn.&nbsp;</p>','MẮT KÍNH CHEMI HÀN QUỐC CHỐNG ÁNH SÁNG XANH U6',400000,'mat-kinh-chemi-han-quoc-chong-anh-sang-xanh-u6',_binary '','products/c00e58d5-9e74-4754-a987-731e9ee4c996.png',365,7,3,11,7,5),(12,'2024-05-01 21:22:46.381000','2024-05-01 21:22:46.381000','<p style=\"text-align: center;\"><span style=\"font-size: 18pt;\"><strong>K&Iacute;CH THƯỚC GỌNG K&Iacute;NH</strong></span></p>\r\n<table style=\"border-collapse: collapse; width: 100%; border-width: 0px; margin-left: auto; margin-right: auto;\" border=\"1\"><colgroup><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"></colgroup>\r\n<tbody>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/chieu_ngang_1.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://kinhmatlily.com/images/product_size/chieu_ngang.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_rong.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_cao.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/cau_mui.png\" width=\"180\" height=\"180\"></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\">147mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">143mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">56mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">57mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">20mm</td>\r\n</tr>\r\n</tbody>\r\n</table>','KÍNH KÍM LOẠI 91370',350000,'kinh-kim-loai-91370',_binary '','products/ef564164-901e-48b3-ae07-cac9bc6e6895.jpeg',30,5,2,3,5,4),(13,'2024-05-01 21:24:26.454000','2024-05-01 21:43:11.770000','<p>Kodak l&agrave; thương hiệu sản xuất mắt k&iacute;nh đến từ Mỹ, những sản phẩm của h&atilde;ng n&agrave;y được sản xuất theo c&ocirc;ng nghệ ti&ecirc;n tiến v&agrave; hiện đại nhất. Chất lượng tr&ograve;ng k&iacute;nh của Kodak được khẳng định tr&ecirc;n to&agrave;n thế giới bằng việc đạt được ti&ecirc;u chuẩn FDA (ti&ecirc;u chuẩn của Mỹ về thực phẩm v&agrave; dược phẩm), ti&ecirc;u chuẩn CE (ti&ecirc;u chuẩn chất lượng sản phẩm của EU). Nh&agrave; m&aacute;y sản xuất của Kodak được chứng nhận l&agrave; đạt ti&ecirc;u chuẩn chất lượng ISO 9001 2000 (ti&ecirc;u chuẩn thế giới về chất lượng quản l&yacute;).</p>\r\n<p>Điểm đặc biệt của tr&ograve;ng k&iacute;nh Kodak:</p>\r\n<ul>\r\n<li>Loại tr&ograve;ng phẳng một mặt, si&ecirc;u cứng, mỏng v&agrave; nhẹ, chống s&oacute;ng điện từ, tia cực t&iacute;m,chống &aacute;nh s&aacute;ng xanh , UV400 chất liệu trong suốt.</li>\r\n<li>Độ trong suốt l&ecirc;n đến 99,6%, cho ph&eacute;p người đeo c&oacute; tầm nh&igrave;n tốt nhất trong hầu hết điều kiện thời tiết b&ecirc;n ngo&agrave;i.</li>\r\n<li>Tr&ograve;ng được tr&aacute;ng bởi lớp v&aacute;ng dầu Clean&rsquo;N&rsquo;Clear gi&uacute;p giảm b&aacute;m nước v&agrave; b&aacute;m bẩn cao, giảm d&iacute;nh dầu mỡ,v&ugrave;ng quang học rộng cung cấp cho người đeo h&igrave;nh ảnh ch&acirc;n thật hơn.</li>\r\n</ul>','MẮT KÍNH KODAK FSV',520000,'mat-kinh-kodak-fsv',_binary '','products/0338151e-df0f-4fe7-ae2e-200a9d486f61.png',365,8,3,11,8,5),(14,'2024-05-01 21:27:38.415000','2024-05-01 21:27:38.415000','<p style=\"text-align: center;\"><span style=\"font-size: 18pt;\"><strong>K&Iacute;CH THƯỚC GỌNG K&Iacute;NH</strong></span></p>\r\n<table style=\"border-collapse: collapse; width: 100%; border-width: 0px; margin-left: auto; margin-right: auto;\" border=\"1\"><colgroup><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"></colgroup>\r\n<tbody>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/chieu_ngang_1.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://kinhmatlily.com/images/product_size/chieu_ngang.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_rong.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_cao.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/cau_mui.png\" width=\"180\" height=\"180\"></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\">147mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">143mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">56mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">57mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">20mm</td>\r\n</tr>\r\n</tbody>\r\n</table>','KÍNH RÂM LILY KC25',200000,'kinh-ram-lily-kc25',_binary '','products/ad46a1bb-f1e8-4a4a-aef5-efcddf344259.jpeg',20,6,1,1,6,6),(15,'2024-05-01 21:28:47.066000','2024-05-01 21:43:48.678000','<h2 class=\"ptitle\" style=\"text-align: center;\">ĐẶC ĐIỂM NỔI BẬT</h2>\r\n<div id=\"pdes\">\r\n<div class=\"term-description\">\r\n<h3 style=\"text-align: center;\">Tr&ograve;ng K&iacute;nh Cận R&acirc;m Chemi U2 1.60</h3>\r\n<p style=\"text-align: center;\">Tr&ograve;ng K&iacute;nh Cận R&acirc;m Chemi U2 c&oacute; bề mặt tr&ograve;ng k&iacute;nh trơn l&aacute;ng v&agrave; cứng, bền hơn gấp 3 lần so với lớp phủ của tr&ograve;ng k&iacute;nh v&aacute;ng dầu phổ th&ocirc;ng kh&aacute;c, sử dụng l&acirc;u vẫn như mới.</p>\r\n<p style=\"text-align: center;\">-Độ mỏng cao hơn, cắt được độ cận từ 0-8 diop v&agrave; độ loạn từ 0-4 diop.</p>\r\n<p style=\"text-align: center;\">-Lớp phủ Crystal U2 Coated ở cả mặt trong v&agrave; mặt ngo&agrave;i đạt chỉ số UV400.</p>\r\n<p style=\"text-align: center;\">-T&iacute;nh năng ph&ograve;ng chống tia tử ngoại v&agrave; điện từ bức xạ, bảo về to&agrave;n diện mắt của bạn, an to&agrave;n hơn v&agrave; đ&aacute;ng tin cậy hơn.</p>\r\n<p style=\"text-align: center;\">-Tr&ograve;ng k&iacute;nh ch&iacute;nh h&atilde;ng Chemi đạt c&aacute;c ti&ecirc;u chuẩn y tế, ISO 9001:2008, được c&aacute;c chuy&ecirc;n gia đầu ng&agrave;nh khuy&ecirc;n d&ugrave;ng v&agrave; l&agrave; thương hiệu tr&ograve;ng k&iacute;nh b&aacute;n chạy nhất thế giới.</p>\r\n<p style=\"text-align: center;\">-Thời gian đặt h&agrave;ng 7 &ndash; 15 ng&agrave;y ( tuỳ v&agrave;o độ cận của bạn ).</p>\r\n<p style=\"text-align: center;\">-Kh&ocirc;ng l&agrave;m được cho c&aacute;c mẫu gọng k&iacute;nh r&acirc;m c&oacute; độ cong (curve) kh&oacute;.</p>\r\n<p style=\"text-align: center;\"><img src=\"https://www.chapi.vn/img/product/2020/2/7/kinh-ram-nam-phan-cuc-ngay-dem-roupai-13-500x500.jpg\" alt=\"K&iacute;nh r&acirc;m nam ph&acirc;n cực ng&agrave;y đ&ecirc;m ROUPAI\"></p>\r\n<p><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://cdn.kinhmatlily.com/lily01/2022/12/8ef3be9a9b3bb4ea1123b19413c4e669-1671186404000.jpeg\" alt=\"8ef3be9a9b3bb4ea1123b19413c4e669.jpg\" width=\"1024\" height=\"1024\"></p>\r\n</div>\r\n</div>','MẮT KÍNH CHEMI RÂM CẬN CHIẾT SUẤT U2 CX160',715000,'mat-kinh-chemi-ram-can-chiet-suat-u2-cx160',_binary '','products/c3d8ac1c-8d86-4fe9-b803-0f27da05e1c0.jpeg',3365,8,3,11,6,5),(16,'2024-05-01 21:31:25.352000','2024-05-01 21:31:25.352000','<p style=\"text-align: center;\"><span style=\"font-size: 18pt;\"><strong>K&Iacute;CH THƯỚC GỌNG K&Iacute;NH</strong></span></p>\r\n<table style=\"border-collapse: collapse; width: 100%; border-width: 0px; margin-left: auto; margin-right: auto;\" border=\"1\"><colgroup><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"></colgroup>\r\n<tbody>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/chieu_ngang_1.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://kinhmatlily.com/images/product_size/chieu_ngang.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_rong.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_cao.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/cau_mui.png\" width=\"180\" height=\"180\"></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\">147mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">143mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">56mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">57mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">20mm</td>\r\n</tr>\r\n</tbody>\r\n</table>','KÍNH RÂM LILY P2363',350000,'kinh-ram-lily-p2363',_binary '','products/6500deed-ba7e-4d20-a91a-0fb49993e0bd.png',30,7,1,4,7,1),(17,'2024-05-01 21:33:40.222000','2024-05-01 21:33:40.222000','<p style=\"text-align: center;\"><span style=\"font-size: 18pt;\"><strong>K&Iacute;CH THƯỚC GỌNG K&Iacute;NH</strong></span></p>\r\n<table style=\"border-collapse: collapse; width: 100%; border-width: 0px; margin-left: auto; margin-right: auto;\" border=\"1\"><colgroup><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"></colgroup>\r\n<tbody>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/chieu_ngang_1.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://kinhmatlily.com/images/product_size/chieu_ngang.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_rong.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_cao.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/cau_mui.png\" width=\"180\" height=\"180\"></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\">147mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">143mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">56mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">57mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">20mm</td>\r\n</tr>\r\n</tbody>\r\n</table>','KÍNH RÂM P7272',350000,'kinh-ram-p7272',_binary '','products/13a369b3-7818-4ccd-945b-5b30af22c8e4.png',60,8,1,6,8,1),(18,'2024-05-01 21:35:57.878000','2024-05-01 21:35:57.878000','<p style=\"text-align: center;\"><span style=\"font-size: 18pt;\"><strong>K&Iacute;CH THƯỚC GỌNG K&Iacute;NH</strong></span></p>\r\n<table style=\"border-collapse: collapse; width: 100%; border-width: 0px; margin-left: auto; margin-right: auto;\" border=\"1\"><colgroup><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"></colgroup>\r\n<tbody>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/chieu_ngang_1.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://kinhmatlily.com/images/product_size/chieu_ngang.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_rong.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_cao.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/cau_mui.png\" width=\"180\" height=\"180\"></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\">147mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">143mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">56mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">57mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">20mm</td>\r\n</tr>\r\n</tbody>\r\n</table>','KÍNH RÂM P2429',300000,'kinh-ram-p2429',_binary '','products/9456e6b6-4d50-4a4e-961b-4446742bdb06.png',90,6,1,4,2,2),(19,'2024-05-01 21:37:47.585000','2024-05-01 21:37:53.259000','<p style=\"text-align: center;\"><span style=\"font-size: 18pt;\"><strong>K&Iacute;CH THƯỚC GỌNG K&Iacute;NH</strong></span></p>\r\n<table style=\"border-collapse: collapse; width: 100%; border-width: 0px; margin-left: auto; margin-right: auto;\" border=\"1\"><colgroup><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"></colgroup>\r\n<tbody>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/chieu_ngang_1.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://kinhmatlily.com/images/product_size/chieu_ngang.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_rong.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_cao.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/cau_mui.png\" width=\"180\" height=\"180\"></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\">147mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">143mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">56mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">57mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">20mm</td>\r\n</tr>\r\n</tbody>\r\n</table>','KÍNH RÂM P6130',350000,'kinh-ram-p6130',_binary '','products/17f01389-bab3-47ad-b366-b53abaccff1d.png',30,6,1,7,5,3),(20,'2024-05-01 21:39:51.506000','2024-05-01 21:39:51.506000','<p style=\"text-align: center;\"><span style=\"font-size: 18pt;\"><strong>K&Iacute;CH THƯỚC GỌNG K&Iacute;NH</strong></span></p>\r\n<table style=\"border-collapse: collapse; width: 100%; border-width: 0px; margin-left: auto; margin-right: auto;\" border=\"1\"><colgroup><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"><col style=\"width: 19.9599%;\"></colgroup>\r\n<tbody>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/chieu_ngang_1.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img style=\"display: block; margin-left: auto; margin-right: auto;\" src=\"https://kinhmatlily.com/images/product_size/chieu_ngang.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_rong.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/do_cao.png\" width=\"180\" height=\"180\"></td>\r\n<td style=\"text-align: center; border-width: 0px;\"><img src=\"https://kinhmatlily.com/images/product_size/cau_mui.png\" width=\"180\" height=\"180\"></td>\r\n</tr>\r\n<tr>\r\n<td style=\"text-align: center; border-width: 0px;\">147mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">143mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">56mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">57mm</td>\r\n<td style=\"text-align: center; border-width: 0px;\">20mm</td>\r\n</tr>\r\n</tbody>\r\n</table>','KÍNH RÂM 75800',300000,'kinh-ram-75800',_binary '','products/1f30c807-ef0e-43c3-b92e-65b58873eba3.png',60,4,1,2,6,3),(21,'2024-05-01 21:48:45.853000','2024-05-01 21:48:45.853000','<h2 class=\"ptitle\">ĐẶC ĐIỂM NỔI BẬT</h2>\r\n<div id=\"pdes\">\r\n<div class=\"term-description\">\r\n<h3>Tr&ograve;ng K&iacute;nh Cận R&acirc;m Chemi U2 1.67</h3>\r\n<p>Tr&ograve;ng K&iacute;nh Cận R&acirc;m Chemi U2 c&oacute; bề mặt tr&ograve;ng k&iacute;nh trơn l&aacute;ng v&agrave; cứng, bền hơn gấp 3 lần so với lớp phủ của tr&ograve;ng k&iacute;nh v&aacute;ng dầu phổ th&ocirc;ng kh&aacute;c, sử dụng l&acirc;u vẫn như mới.</p>\r\n<p>-Độ mỏng cao hơn, cắt được độ cận từ 0-8 diop v&agrave; độ loạn từ 0-4 diop.</p>\r\n<p>-Lớp phủ Crystal U2 Coated ở cả mặt trong v&agrave; mặt ngo&agrave;i đạt chỉ số UV400.</p>\r\n<p>-T&iacute;nh năng ph&ograve;ng chống tia tử ngoại v&agrave; điện từ bức xạ, bảo về to&agrave;n diện mắt của bạn, an to&agrave;n hơn v&agrave; đ&aacute;ng tin cậy hơn.</p>\r\n<p>-Tr&ograve;ng k&iacute;nh ch&iacute;nh h&atilde;ng Chemi đạt c&aacute;c ti&ecirc;u chuẩn y tế, ISO 9001:2008, được c&aacute;c chuy&ecirc;n gia đầu ng&agrave;nh khuy&ecirc;n d&ugrave;ng v&agrave; l&agrave; thương hiệu tr&ograve;ng k&iacute;nh b&aacute;n chạy nhất thế giới.</p>\r\n<p>-Thời gian đặt h&agrave;ng 7 &ndash; 15 ng&agrave;y ( tuỳ v&agrave;o độ cận của bạn ).</p>\r\n<p>-Kh&ocirc;ng l&agrave;m được cho c&aacute;c mẫu gọng k&iacute;nh r&acirc;m c&oacute; độ cong (curve) kh&oacute;.</p>\r\n<p><img src=\"https://cdn.kinhmatlily.com/lily01/2022/12/8ef3be9a9b3bb4ea1123b19413c4e669-1671186404000.jpeg\" alt=\"8ef3be9a9b3bb4ea1123b19413c4e669.jpg\" width=\"1024\" height=\"1024\"></p>\r\n</div>\r\n</div>','MẮT KÍNH CHEMI RÂM CẬN CHIẾT SUẤT U2 CX167',1195000,'mat-kinh-chemi-ram-can-chiet-suat-u2-cx167',_binary '','products/b92abb92-e7c1-4356-8072-8450245c061e.jpeg',0,4,3,11,3,5);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_details`
--

DROP TABLE IF EXISTS `product_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `color` varchar(20) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `quantity` int NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrhahp4f26x99lqf0kybcs79rb` (`product_id`),
  CONSTRAINT `FKrhahp4f26x99lqf0kybcs79rb` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_details`
--

LOCK TABLES `product_details` WRITE;
/*!40000 ALTER TABLE `product_details` DISABLE KEYS */;
INSERT INTO `product_details` VALUES (1,'2024-05-01 20:58:36.649000','2024-05-01 22:17:41.956000','Ghi',10,1),(2,'2024-05-01 20:58:36.653000','2024-05-01 22:17:41.956000','Đen xi mờ',11,1),(3,'2024-05-01 20:58:36.655000','2024-05-01 22:17:41.956000','Nâu',12,1),(4,'2024-05-01 21:01:10.745000','2024-05-01 22:17:41.956000','Xám',13,2),(5,'2024-05-01 21:02:40.380000','2024-05-01 21:02:40.380000','Đen',0,3),(6,'2024-05-01 21:02:40.382000','2024-05-01 21:02:40.382000','Ghi',0,3),(7,'2024-05-01 21:02:40.383000','2024-05-01 21:02:40.383000','Hồng',0,3),(8,'2024-05-01 21:05:05.488000','2024-05-01 22:17:41.957000','Trắng',14,4),(9,'2024-05-01 21:05:05.491000','2024-05-01 22:17:41.957000','Đen',15,4),(10,'2024-05-01 21:05:05.493000','2024-05-01 22:17:41.957000','Hồng',16,4),(11,'2024-05-01 21:05:27.089000','2024-05-01 22:17:41.957000','SPK Ốc Vít',17,5),(12,'2024-05-01 21:05:27.091000','2024-05-01 22:17:41.957000','SPK Đệm Mũi',18,5),(13,'2024-05-01 21:08:30.005000','2024-05-01 22:40:41.310000','Xanh',18,6),(14,'2024-05-01 21:12:34.348000','2024-05-01 22:17:41.958000','Ghi',20,7),(15,'2024-05-01 21:12:34.350000','2024-05-01 22:17:41.958000','Trắng',21,7),(16,'2024-05-01 21:12:34.353000','2024-05-01 22:17:41.958000','Đen trắng',22,7),(17,'2024-05-01 21:13:05.958000','2024-05-01 22:40:41.310000','Xanh dương',39,8),(18,'2024-05-01 21:13:05.960000','2024-05-01 22:21:31.199000','Vàng',50,8),(19,'2024-05-01 21:13:05.962000','2024-05-01 21:13:05.962000','Cam',0,8),(20,'2024-05-01 21:13:05.964000','2024-05-01 21:13:05.964000','Xanh lá cây',0,8),(21,'2024-05-01 21:13:05.965000','2024-05-01 21:13:05.965000','Hồng',0,8),(22,'2024-05-01 21:16:59.809000','2024-05-01 22:21:31.199000','Trắng',50,9),(23,'2024-05-01 21:16:59.810000','2024-05-01 22:21:31.199000','Ghi trắng',20,9),(24,'2024-05-01 21:16:59.811000','2024-05-01 22:21:31.199000','Đen ghi',30,9),(25,'2024-05-01 21:18:17.752000','2024-05-01 22:21:31.199000','Xanh nước biển',15,10),(26,'2024-05-01 21:18:17.752000','2024-05-01 22:21:31.199000','Xanh lá',15,10),(27,'2024-05-01 21:18:17.752000','2024-05-01 22:21:31.199000','Tím',10,10),(28,'2024-05-01 21:22:05.210000','2024-05-01 22:23:44.824000','u6-156',10,11),(29,'2024-05-01 21:22:05.211000','2024-05-01 22:23:44.824000','u6-160',19,11),(30,'2024-05-01 21:22:05.213000','2024-05-01 22:23:44.824000','u6-167',40,11),(31,'2024-05-01 21:22:46.384000','2024-05-01 22:23:44.824000','Đen',20,12),(32,'2024-05-01 21:22:46.386000','2024-05-01 22:23:44.824000','Vàng',8,12),(33,'2024-05-01 21:22:46.388000','2024-05-01 21:22:46.388000','Đen vàng',0,12),(34,'2024-05-01 21:22:46.389000','2024-05-01 22:23:44.824000','Hồng',9,12),(35,'2024-05-01 21:24:26.455000','2024-05-01 22:26:26.736000','1.56',10,13),(36,'2024-05-01 21:24:26.456000','2024-05-01 22:26:26.736000','1.60',19,13),(37,'2024-05-01 21:27:38.415000','2024-05-01 22:26:26.736000','Ghi',18,14),(38,'2024-05-01 21:27:38.415000','2024-05-01 22:26:26.736000','Đen',17,14),(39,'2024-05-01 21:27:38.426000','2024-05-01 22:26:26.736000','Đen nâu',18,14),(40,'2024-05-01 21:27:38.428000','2024-05-01 22:26:26.736000','Đen mắt cam',15,14),(41,'2024-05-01 21:28:47.066000','2024-05-01 22:26:26.736000','Nâu',30,15),(42,'2024-05-01 21:28:47.066000','2024-05-01 21:28:47.066000','Xanh',0,15),(43,'2024-05-01 21:28:47.082000','2024-05-01 22:42:31.076000','Hồng',19,15),(44,'2024-05-01 21:31:25.367000','2024-05-01 22:27:49.940000','Trắng',10,16),(45,'2024-05-01 21:31:25.367000','2024-05-01 22:27:49.940000','Đen',10,16),(46,'2024-05-01 21:33:40.224000','2024-05-01 22:27:49.940000','Trắng',18,17),(47,'2024-05-01 21:33:40.226000','2024-05-01 22:27:49.940000','Đen',14,17),(48,'2024-05-01 21:35:57.878000','2024-05-01 22:29:50.075000','Kem',20,18),(49,'2024-05-01 21:35:57.878000','2024-05-01 21:35:57.878000','Nâu',0,18),(50,'2024-05-01 21:35:57.878000','2024-05-01 22:29:50.075000','Đen',10,18),(51,'2024-05-01 21:37:47.585000','2024-05-01 22:29:50.075000','Đen nâu',15,19),(52,'2024-05-01 21:37:47.585000','2024-05-01 22:29:50.075000','Nâu đục',20,19),(53,'2024-05-01 21:37:47.585000','2024-05-01 21:37:47.585000','Đen',0,19),(54,'2024-05-01 21:37:47.585000','2024-05-01 22:29:50.075000','Ghi',25,19),(55,'2024-05-01 21:39:51.510000','2024-05-01 22:31:25.441000','Đen vàng',18,20),(56,'2024-05-01 21:39:51.511000','2024-05-01 21:39:51.511000','Đen xanh dương',0,20),(57,'2024-05-01 21:39:51.513000','2024-05-01 22:42:31.076000','Trắng mắt nâu',19,20),(58,'2024-05-01 21:48:45.853000','2024-05-01 22:31:25.441000','Nâu G50',12,21),(59,'2024-05-01 21:48:45.853000','2024-05-01 22:31:25.441000','Nâu G30',14,21),(60,'2024-05-01 21:48:45.853000','2024-05-01 21:48:45.853000','Nâu 85F',0,21),(61,'2024-05-01 21:48:45.864000','2024-05-01 22:31:25.441000','Xanh 85F',16,21);
/*!40000 ALTER TABLE `product_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS `product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_image` (
  `product_id` bigint NOT NULL,
  `image` varchar(100) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  KEY `FK6oo0cvcdtb6qmwsga468uuukk` (`product_id`),
  CONSTRAINT `FK6oo0cvcdtb6qmwsga468uuukk` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_image`
--

LOCK TABLES `product_image` WRITE;
/*!40000 ALTER TABLE `product_image` DISABLE KEYS */;
INSERT INTO `product_image` VALUES (1,'products/4c961d70-2198-4083-889e-0c30f5871c34.jpeg'),(1,'products/c4f67214-c86c-4bab-86fb-e4e9c4892891.jpeg'),(1,'products/d29e84e8-db32-4e41-9909-4a56f6dafb4f.jpeg'),(2,'products/f24c2100-b887-4055-9bd3-9bc8c9e17e2c.jpeg'),(2,'products/446864b1-1432-449b-8287-e52180c4d5a3.jpg'),(3,'products/4ec69153-4167-4abd-ae15-9660db6f42f8.jpeg'),(3,'products/f241c894-4ddc-4c73-a8de-fc449d563c0a.jpeg'),(3,'products/1bbf5185-19a5-4bf8-ae18-08b8b6c643e7.jpeg'),(4,'products/b8fb3ac1-c8bc-47cc-a8f6-3bc4f8b97d9b.jpeg'),(4,'products/98e80c8f-87bc-4b2d-ad7a-1cb7a40eee92.jpeg'),(4,'products/09ae071d-8a9a-46f5-b3f4-87279b6cf64a.jpeg'),(6,'products/763aec8a-5698-4811-b6e0-7000c68975a4.jpeg'),(5,'products/378e5988-c97b-42e0-83b1-0ef5611ef9b7.jpeg'),(5,'products/4bed2d84-4817-4459-bc49-97a427efbad5.jpeg'),(5,'products/d981abdc-339c-46b9-b668-670285961aa0.jpeg'),(7,'products/c59472e7-3664-425b-a736-d3f6dc26d46a.png'),(7,'products/9276893a-2251-4f1c-8ca4-4844a927bd02.png'),(7,'products/8c52d429-6d24-4c59-896a-1e322c12e935.png'),(8,'products/ff8ad3bc-e1c4-479d-8449-d9adf287a5e5.jpeg'),(8,'products/0fd145fd-68ad-4fbd-a3ef-7b33a2d1d3db.jpeg'),(9,'products/3e26ff7a-c643-4bce-8f25-bca8a5ab6971.jpeg'),(9,'products/9f169fb8-238a-462b-b08a-ee499d7bb56b.jpeg'),(9,'products/1ba7c43a-b0e9-4311-9353-6a2de5b15a83.jpeg'),(12,'products/1239d405-72fa-4a76-a512-440cc0fa3a43.jpeg'),(12,'products/c799a881-5611-4858-abf3-ddc9484d7972.jpeg'),(12,'products/eddf1d8d-a5d1-422d-a2ea-f66bbb749306.png'),(12,'products/90e742a7-266f-4c71-88d1-b339ec261033.png'),(14,'products/f86821ab-1655-4e31-87bc-28cda3d73b36.jpeg'),(14,'products/85be27ee-7be9-49e2-9549-c59afdec7960.jpeg'),(14,'products/d5f9d526-8cdc-4b43-8c52-73885da23a42.jpeg'),(14,'products/a7c4dfe9-20a9-49c3-9040-852c4629e31d.jpeg'),(16,'products/00df9d59-7dc7-4dc4-a954-3ab0d5f089ee.png'),(16,'products/f74cf797-f701-48ad-8b6b-3c9299a833bc.png'),(17,'products/b65c66c8-09ab-4969-8a22-32c17571e05b.png'),(17,'products/82d0c4d7-98d1-4fdf-82fe-e8822ddb926e.png'),(18,'products/cf6431c9-917e-4b9c-bfb5-135b6e6dee6d.png'),(18,'products/58fb355b-ae14-4b62-83c7-0ef48d611eab.png'),(18,'products/9a82b266-91a3-416b-9923-99531d9bd03b.png'),(19,'products/e0ed9038-4c70-4e75-966b-85d71de0862c.png'),(19,'products/70c3da51-2f2a-467f-a10e-4528b390faef.png'),(19,'products/1ba33ec0-ca00-45a2-a0c0-9d04628faa8b.png'),(19,'products/3e097ecb-2e48-4351-9b65-1aa3f82e5ccc.png'),(20,'products/038c446a-4844-4ddc-b22a-22fa0cc697ea.png'),(20,'products/028f88cc-54d6-4fad-9eec-49a4924a29c3.png'),(20,'products/23da0304-4c22-4106-9e78-4ec61be33e62.png'),(10,'products/56d6a316-4d41-4815-ad27-17f1ad4b7c39.jpeg'),(10,'products/92096f84-0850-4b4c-8b28-058e50681fe6.jpeg'),(10,'products/532f45a0-5936-4a2b-9ac4-ecdf55b4c205.jpeg'),(11,'products/e483b5e0-8c6e-498e-a870-e2348cacf640.png'),(11,'products/669067b4-b7c4-40e3-8cfe-b56d6e18a7c1.png'),(11,'products/7023d5ff-0dfc-4efb-b908-3dfeeb05383b.png'),(13,'products/567c4a3f-f5eb-41fb-b5d3-b5cc29c7ae29.png'),(13,'products/02ed6b72-9b0b-4ebf-a097-299c3ebe37d8.png'),(15,'products/38b8cf69-5fd1-4a45-9e72-92b1e7a7a368.jpeg'),(15,'products/6b29e11d-2137-4a73-8f99-fa7f46a7f274.jpeg'),(15,'products/21f9438d-b5df-410e-94a5-22ba28eed911.jpeg'),(21,'products/349f3c43-d238-4978-86f4-f314017890cf.jpeg'),(21,'products/df06a967-4509-460d-b967-e1144d3a2dd7.jpeg'),(21,'products/0f95bc4f-b93e-4eaa-bf12-242505698c3b.jpeg'),(21,'products/af3ab3df-17be-43d1-8985-63fca99aa59d.jpeg');
/*!40000 ALTER TABLE `product_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt`
--

DROP TABLE IF EXISTS `receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `status` bit(1) NOT NULL,
  `staff_id` bigint NOT NULL,
  `supplier_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd3m9vjhrr13nbb4ycw6o2u3lx` (`staff_id`),
  KEY `FK76be0gur5257gn4ocs942s9ml` (`supplier_id`),
  CONSTRAINT `FK76be0gur5257gn4ocs942s9ml` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`),
  CONSTRAINT `FKd3m9vjhrr13nbb4ycw6o2u3lx` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt`
--

LOCK TABLES `receipt` WRITE;
/*!40000 ALTER TABLE `receipt` DISABLE KEYS */;
INSERT INTO `receipt` VALUES (1,'2024-01-01 22:17:41.878000','2024-01-01 22:17:41.878000',_binary '',1,6),(2,'2024-03-01 22:21:31.162000','2024-03-01 22:21:31.162000',_binary '',1,5),(3,'2024-03-01 22:23:44.798000','2024-03-01 22:23:44.798000',_binary '',1,4),(4,'2024-04-01 22:26:26.696000','2024-04-01 22:26:26.696000',_binary '',1,3),(5,'2024-04-01 22:27:49.927000','2024-04-01 22:27:49.927000',_binary '',1,1),(6,'2024-05-01 22:29:50.055000','2024-05-01 22:29:50.055000',_binary '',1,1),(7,'2024-05-01 22:31:25.418000','2024-05-01 22:31:25.418000',_binary '',1,2);
/*!40000 ALTER TABLE `receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt_details`
--

DROP TABLE IF EXISTS `receipt_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL,
  `quantity` int NOT NULL,
  `product_details_id` bigint NOT NULL,
  `receipt_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKolael09bv0lm95ajv0gfwf34t` (`product_details_id`),
  KEY `FKpkdffw2hifmlrufrfhj5oavl0` (`receipt_id`),
  CONSTRAINT `FKolael09bv0lm95ajv0gfwf34t` FOREIGN KEY (`product_details_id`) REFERENCES `product_details` (`id`),
  CONSTRAINT `FKpkdffw2hifmlrufrfhj5oavl0` FOREIGN KEY (`receipt_id`) REFERENCES `receipt` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt_details`
--

LOCK TABLES `receipt_details` WRITE;
/*!40000 ALTER TABLE `receipt_details` DISABLE KEYS */;
INSERT INTO `receipt_details` VALUES (1,100000,10,1,1),(2,100000,11,2,1),(3,100000,12,3,1),(4,50000,13,4,1),(5,150000,14,8,1),(6,150000,15,9,1),(7,150000,16,10,1),(8,5000,17,11,1),(9,5000,18,12,1),(10,10000,19,13,1),(11,100000,20,14,1),(12,100000,21,15,1),(13,100000,22,16,1),(14,1000,40,17,2),(15,1000,50,18,2),(16,300000,50,22,2),(17,300000,20,23,2),(18,300000,30,24,2),(19,200000,15,25,2),(20,200000,15,26,2),(21,200000,10,27,2),(22,200000,10,28,3),(23,200000,19,29,3),(24,200000,40,30,3),(25,150000,20,31,3),(26,150000,8,32,3),(27,150000,9,34,3),(28,10000,10,35,4),(29,120000,19,36,4),(30,200000,18,37,4),(31,200000,17,38,4),(32,200000,18,39,4),(33,200000,15,40,4),(34,400000,30,41,4),(35,400000,20,43,4),(36,140000,10,44,5),(37,140000,10,45,5),(38,150000,18,46,5),(39,150000,14,47,5),(40,200000,20,48,6),(41,200000,10,50,6),(42,180000,15,51,6),(43,180000,20,52,6),(44,180000,25,54,6),(45,150000,12,58,7),(46,150000,14,59,7),(47,150000,16,61,7),(48,200000,18,55,7),(49,200000,20,57,7);
/*!40000 ALTER TABLE `receipt_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `description` varchar(200) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `name` varchar(20) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'2024-05-01 18:50:59.937000','2024-05-01 18:50:59.937000','ROLE_ADMIN','ROLE_ADMIN'),(2,'2024-05-01 18:51:00.006000','2024-05-01 18:51:00.006000','ROLE_STAFF','ROLE_STAFF'),(3,'2024-05-01 18:51:00.019000','2024-05-01 18:51:00.019000','ROLE_USER','ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale`
--

DROP TABLE IF EXISTS `sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `end_date` datetime(6) NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `start_date` datetime(6) NOT NULL,
  `staff_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrnh309k9vhquwp9o2wk5gjajs` (`staff_id`),
  CONSTRAINT `FKrnh309k9vhquwp9o2wk5gjajs` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale`
--

LOCK TABLES `sale` WRITE;
/*!40000 ALTER TABLE `sale` DISABLE KEYS */;
INSERT INTO `sale` VALUES (1,'2024-05-01 21:59:03.160000','2024-05-01 21:59:03.160000','2024-05-05 23:59:59.000000','Giảm giá 30/04 - 01/05','2024-04-29 00:00:00.000000',1),(2,'2024-05-01 22:13:09.324000','2024-05-01 22:13:09.324000','2024-05-31 23:59:59.000000','Chào hè 2024','2024-05-01 00:00:00.000000',1);
/*!40000 ALTER TABLE `sale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale_details`
--

DROP TABLE IF EXISTS `sale_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `discount` float NOT NULL,
  `product_id` bigint NOT NULL,
  `sale_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqf951dcqoxo848ljot0457os2` (`product_id`),
  KEY `FKnqvhai2up4q50gyf1fcbtmt9r` (`sale_id`),
  CONSTRAINT `FKnqvhai2up4q50gyf1fcbtmt9r` FOREIGN KEY (`sale_id`) REFERENCES `sale` (`id`),
  CONSTRAINT `FKqf951dcqoxo848ljot0457os2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale_details`
--

LOCK TABLES `sale_details` WRITE;
/*!40000 ALTER TABLE `sale_details` DISABLE KEYS */;
INSERT INTO `sale_details` VALUES (1,20,1,1),(2,10,2,1),(3,30,3,1),(4,40,4,1),(5,10,5,2),(6,20,6,2),(7,30,7,2),(8,40,8,2),(9,50,9,2),(10,60,10,2),(11,15,11,2),(12,25,12,2);
/*!40000 ALTER TABLE `sale_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shape`
--

DROP TABLE IF EXISTS `shape`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shape` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k8ioe803183hxgwvdumoypp9v` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shape`
--

LOCK TABLES `shape` WRITE;
/*!40000 ALTER TABLE `shape` DISABLE KEYS */;
INSERT INTO `shape` VALUES (1,'2024-05-01 18:51:00.540000','2024-05-01 18:51:00.540000','Mắt mèo'),(2,'2024-05-01 18:51:00.553000','2024-05-01 18:51:00.553000','Tròn Oval'),(3,'2024-05-01 18:51:00.563000','2024-05-01 18:51:00.563000','Vuông chữ nhật'),(4,'2024-05-01 18:51:00.574000','2024-05-01 18:51:00.574000','Vuông tròn'),(5,'2024-05-01 18:51:00.585000','2024-05-01 18:51:00.585000','Tròn tròn'),(6,'2024-05-01 18:51:00.594000','2024-05-01 18:51:00.594000','Vuông vuông'),(7,'2024-05-01 18:51:00.605000','2024-05-01 18:51:00.605000','Tròn cạnh'),(8,'2024-05-01 18:51:00.617000','2024-05-01 18:51:00.617000','Đặc biệt'),(9,'2024-05-01 18:51:00.630000','2024-05-01 18:51:00.630000','Đa giác');
/*!40000 ALTER TABLE `shape` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `birthday` datetime(6) DEFAULT NULL,
  `fullname` varchar(30) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `gender` varchar(5) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `phone` varchar(10) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_oqg5g7ejg0vk2ew0thf2asi4` (`phone`),
  KEY `FKs9jl798sgmtrl79dm4svocvaw` (`account_id`),
  CONSTRAINT `FKs9jl798sgmtrl79dm4svocvaw` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'2024-05-01 18:51:00.228000','2024-05-01 18:51:00.228000','2001-10-14 18:51:00.044000','Chủ cửa hàng','Nam','0096871026',_binary '',1);
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `address` varchar(200) COLLATE utf8mb4_vietnamese_ci DEFAULT NULL,
  `name` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `phone` varchar(10) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_c3fclhmodftxk4d0judiafwi3` (`name`),
  UNIQUE KEY `UK_odw8hcb1lettg4mqax263yyb5` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'2024-05-01 18:51:00.769000','2024-05-01 18:51:00.769000','Số nhà 154 ngõ Văn Chương, Đống Đa, Hà Nội','G – Shop','0943061993'),(2,'2024-05-01 18:51:00.790000','2024-05-01 18:51:00.790000','Số 75A ngõ 61 Lê Văn Lương, Hà Nội','Anna Eyewear','0966886634'),(3,'2024-05-01 18:51:00.806000','2024-05-01 18:51:00.806000','Số 58 Lương Văn Can, Hà Nội','Hiệu Kính Thành Luân','0382897422'),(4,'2024-05-01 18:51:00.820000','2024-05-01 18:51:00.820000','151B Lê Duẩn, Hoàn Kiếm, Hà Nội','Công Ty TNHH Kính Mắt Thiên Vũ','0983675413'),(5,'2024-05-01 18:51:00.833000','2024-05-01 18:51:00.833000','Số 8 ngõ 381/55/4 Nguyễn Khang, Cầu Giấy, Hà Nội','Vanila Shop','0912101011'),(6,'2024-05-01 18:51:00.846000','2024-05-01 18:51:00.846000','Số 17D ngõ 141/236 Giáp Nhị, Thịnh Liệt, Hoàng Mai, Hà Nội','Công Ty TNHH Kính Mắt Thành Đô','0975151118');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warranty`
--

DROP TABLE IF EXISTS `warranty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warranty` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `status` bit(1) NOT NULL,
  `customer_id` bigint NOT NULL,
  `staff_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeu8bknpqjux3k1ccqmx1avrle` (`customer_id`),
  KEY `FK7k26dvg52tfxo96ipfy8mq90v` (`staff_id`),
  CONSTRAINT `FK7k26dvg52tfxo96ipfy8mq90v` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`),
  CONSTRAINT `FKeu8bknpqjux3k1ccqmx1avrle` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warranty`
--

LOCK TABLES `warranty` WRITE;
/*!40000 ALTER TABLE `warranty` DISABLE KEYS */;
/*!40000 ALTER TABLE `warranty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warranty_details`
--

DROP TABLE IF EXISTS `warranty_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warranty_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cost` double NOT NULL,
  `quantity` int NOT NULL,
  `warranty_type` int NOT NULL,
  `order_id` bigint NOT NULL,
  `product_details_id` bigint NOT NULL,
  `warranty_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9anrwwdrgw0gwr0wemvqb88iq` (`order_id`),
  KEY `FKsr428bxrkatd6g613668cxc69` (`product_details_id`),
  KEY `FKr4b3myw2mqxkstqs1hhv9cfmd` (`warranty_id`),
  CONSTRAINT `FK9anrwwdrgw0gwr0wemvqb88iq` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKr4b3myw2mqxkstqs1hhv9cfmd` FOREIGN KEY (`warranty_id`) REFERENCES `warranty` (`id`),
  CONSTRAINT `FKsr428bxrkatd6g613668cxc69` FOREIGN KEY (`product_details_id`) REFERENCES `product_details` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warranty_details`
--

LOCK TABLES `warranty_details` WRITE;
/*!40000 ALTER TABLE `warranty_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `warranty_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-01 22:52:06
