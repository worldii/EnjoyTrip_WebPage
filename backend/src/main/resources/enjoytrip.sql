CREATE DATABASE  IF NOT EXISTS `enjoytrip` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `enjoytrip`;
-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: enjoytrip
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
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authority` (
                             `authority_id` int NOT NULL COMMENT '1 : 기본 유저',
                             PRIMARY KEY (`authority_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
                         `board_id` int NOT NULL AUTO_INCREMENT,
                         `user_id` varchar(45) DEFAULT NULL,
                         `current_update` datetime DEFAULT CURRENT_TIMESTAMP,
                         `subject` varchar(100) NOT NULL,
                         `content` varchar(3000) NOT NULL,
                         `hit` int DEFAULT '0',
                         `type` enum('notice','community') DEFAULT 'notice',
                         PRIMARY KEY (`board_id`),
                         KEY `user_id` (`user_id`),
                         CONSTRAINT `board_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
                           `comment_id` int NOT NULL AUTO_INCREMENT,
                           `user_id` varchar(45) NOT NULL,
                           `content` varchar(1000) NOT NULL,
                           `board_id` int NOT NULL,
                           `current_update` datetime DEFAULT CURRENT_TIMESTAMP,
                           PRIMARY KEY (`comment_id`),
                           KEY `comment_board_fk_idx` (`board_id`),
                           CONSTRAINT `comment_board_fk` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `file_info`
--

DROP TABLE IF EXISTS `file_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file_info` (
                             `file_info_id` int NOT NULL AUTO_INCREMENT,
                             `board_id` int NOT NULL,
                             `file_url` varchar(1000) NOT NULL,
                             PRIMARY KEY (`file_info_id`),
                             KEY `board_id_idx` (`board_id`),
                             CONSTRAINT `board_id_fk` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hot_place`
--

DROP TABLE IF EXISTS `hot_place`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hot_place` (
                             `hot_place_id` varchar(45) NOT NULL,
                             `hot_place_name` varchar(100) NOT NULL,
                             `x` varchar(100) NOT NULL,
                             `y` varchar(100) NOT NULL,
                             `vote` bigint DEFAULT '0',
                             `place_url` varchar(100) DEFAULT NULL,
                             `road_address_name` varchar(100) DEFAULT NULL,
                             `address_name` varchar(100) DEFAULT NULL,
                             PRIMARY KEY (`hot_place_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hot_place_article`
--

DROP TABLE IF EXISTS `hot_place_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hot_place_article` (
                                     `hot_place_article_id` int NOT NULL AUTO_INCREMENT,
                                     `hot_place_id` varchar(45) DEFAULT NULL,
                                     `content` varchar(2000) DEFAULT NULL,
                                     `createAt` datetime DEFAULT CURRENT_TIMESTAMP,
                                     `user_id` varchar(45) DEFAULT NULL,
                                     `image_url` varchar(1000) DEFAULT NULL,
                                     `hot_place_name` varchar(45) DEFAULT NULL,
                                     PRIMARY KEY (`hot_place_article_id`),
                                     KEY `user_id_fk_hot_place_idx` (`user_id`),
                                     KEY `hot_place_id_fk_article_idx` (`hot_place_id`),
                                     CONSTRAINT `hot_place_id_fk_article` FOREIGN KEY (`hot_place_id`) REFERENCES `hot_place` (`hot_place_id`),
                                     CONSTRAINT `user_id_fk_hot_place` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hot_place_tag`
--

DROP TABLE IF EXISTS `hot_place_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hot_place_tag` (
                                 `hot_place_id` varchar(45) DEFAULT NULL,
                                 `tag_name` varchar(45) NOT NULL,
                                 `count` int DEFAULT NULL,
                                 PRIMARY KEY (`tag_name`),
                                 KEY `hot_place_id_fk_idx` (`hot_place_id`),
                                 CONSTRAINT `hot_place_id_fk` FOREIGN KEY (`hot_place_id`) REFERENCES `hot_place` (`hot_place_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan` (
                        `plan_id` int NOT NULL AUTO_INCREMENT,
                        `plan_board_id` int NOT NULL,
                        `place` varchar(100) NOT NULL,
                        `content` varchar(100) DEFAULT NULL,
                        `order` int NOT NULL,
                        `duration` int DEFAULT '0',
                        `date` date NOT NULL,
                        `start_time` time NOT NULL,
                        `end_time` time NOT NULL,
                        PRIMARY KEY (`plan_id`),
                        KEY `plan_board_id` (`plan_board_id`),
                        CONSTRAINT `plan_ibfk_1` FOREIGN KEY (`plan_board_id`) REFERENCES `plan_board` (`plan_board_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `plan_board`
--

DROP TABLE IF EXISTS `plan_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan_board` (
                              `plan_board_id` int NOT NULL AUTO_INCREMENT,
                              `title` varchar(100) NOT NULL,
                              `user_id` varchar(45) NOT NULL,
                              `start_date` date NOT NULL,
                              `end_date` date NOT NULL,
                              PRIMARY KEY (`plan_board_id`),
                              KEY `user_id` (`user_id`),
                              CONSTRAINT `plan_board_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `user_id` varchar(45) NOT NULL,
                        `name` varchar(10) NOT NULL,
                        `address` varchar(45) NOT NULL,
                        `password` varchar(200) NOT NULL,
                        `email` varchar(45) NOT NULL,
                        `authority` int DEFAULT '1',
                        `salt` varchar(200) DEFAULT NULL,
                        `token` varchar(1000) DEFAULT NULL,
                        PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-25 17:37:31
