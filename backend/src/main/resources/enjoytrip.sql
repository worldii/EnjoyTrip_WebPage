drop database enjoytrip;
create database enjoytrip;
use enjoytrip;


CREATE SCHEMA IF NOT EXISTS `enjoytrip` DEFAULT CHARACTER SET utf8mb3 ;
USE `enjoytrip` ;

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

-- -----------------------------------------------------
-- Table `enjoytrip`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`board` (
    `board_id` Bigint NOT NULL AUTO_INCREMENT,
    `user_id` VARCHAR(45) NULL DEFAULT NULL,
    `current_update` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `subject` VARCHAR(100) NOT NULL,
    `content` VARCHAR(3000) NOT NULL,
    `hit` INT NULL DEFAULT '0',
    `type` ENUM('notice', 'community') NULL DEFAULT 'notice',
    PRIMARY KEY (`board_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


CREATE TABLE IF NOT EXISTS `enjoytrip`.`comment` (
    `comment_id` Bigint NOT NULL AUTO_INCREMENT,
    `user_id` VARCHAR(45) NOT NULL,
    `content` VARCHAR(1000) NOT NULL,
    `board_id` INT NOT NULL,
    `current_update` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`comment_id`)
    )
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;

CREATE TABLE IF NOT EXISTS `enjoytrip`.`file_info` (
    `file_info_id` Bigint NOT NULL AUTO_INCREMENT,
    `board_id` INT NOT NULL,
    `file_url` VARCHAR(1000) NOT NULL,
    PRIMARY KEY (`file_info_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `enjoytrip`.`hot_place` (
    `hot_place_id` VARCHAR(45) NOT NULL,
    `hot_place_name` VARCHAR(100) NOT NULL,
    `x` VARCHAR(100) NOT NULL,
    `y` VARCHAR(100) NOT NULL,
    `vote` BIGINT NULL DEFAULT 0,
    `place_url` VARCHAR(100) NULL,
    `road_address_name` VARCHAR(100) NULL,
    `address_name` VARCHAR(100) NULL,
    PRIMARY KEY (`hot_place_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;

-- -----------------------------------------------------
-- Table `enjoytrip`.`hot_place_article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`hot_place_article` (
                                                               `hot_place_article_id` INT NOT NULL AUTO_INCREMENT,
                                                               `hot_place_id` VARCHAR(45) NULL DEFAULT NULL,
    `content` VARCHAR(2000) NULL DEFAULT NULL,
    `createAt` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
    `user_id` VARCHAR(45) NULL DEFAULT NULL,
    `image_url` VARCHAR(1000) NULL DEFAULT NULL,
    `hot_place_name` VARCHAR(45) NULL,
    PRIMARY KEY (`hot_place_article_id`),
    INDEX `user_id_fk_hot_place_idx` (`user_id` ASC) VISIBLE,
    INDEX `hot_place_id_fk_article_idx` (`hot_place_id` ASC) VISIBLE,
    CONSTRAINT `hot_place_id_fk_article`
    FOREIGN KEY (`hot_place_id`)
    REFERENCES `enjoytrip`.`hot_place` (`hot_place_id`),
    CONSTRAINT `user_id_fk_hot_place`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`user` (`user_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;

-- -----------------------------------------------------
-- Table `enjoytrip`.`hot_place_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`hot_place_tag` (
    `hot_place_id` VARCHAR(45) NOT NULL,
    `tag_name` VARCHAR(45) NOT NULL,
    `count` INT NULL DEFAULT NULL,
    PRIMARY KEY (`tag_name`, `hot_place_id`),
    INDEX `hot_place_id_fk_idx` (`hot_place_id` ASC) VISIBLE,
    CONSTRAINT `hot_place_id_fk`
    FOREIGN KEY (`hot_place_id`)
    REFERENCES `enjoytrip`.`hot_place` (`hot_place_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;

/*!40101 SET character_set_client = @saved_cs_client */;


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
