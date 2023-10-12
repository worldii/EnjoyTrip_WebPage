drop database enjoytrip;
create database enjoytrip;
use enjoytrip;

DROP TABLE IF EXISTS comment cascade ;
DROP TABLE IF EXISTS board_image_info cascade;
DROP TABLE IF EXISTS hot_place_article cascade;
DROP TABLE IF EXISTS plan cascade;
DROP TABLE IF EXISTS plan_board cascade;
DROP TABLE IF EXISTS board cascade;
DROP TABLE IF EXISTS `USER` cascade;
DROP TABLE IF EXISTS hot_place_tag cascade;
DROP TABLE IF EXISTS hot_place_article cascade;
DROP TABLE IF EXISTS hot_place cascade;

CREATE TABLE user (
                      user_id VARCHAR(255) PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      address VARCHAR(255),
                      authority INT default 1,
                      password VARCHAR(255) NOT NULL,
                      email VARCHAR(255) NOT NULL,
                      salt VARCHAR(255)
);

CREATE TABLE board_image_info (
                                 image_info_id BIGINT not null AUTO_INCREMENT PRIMARY KEY,
                                 image_url VARCHAR(255),
                                 board_id BIGINT
);

CREATE TABLE board (
                       board_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       user_id VARCHAR(255) NOT NULL,
                       current_update TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       subject VARCHAR(255) NOT NULL,
                       content VARCHAR(255) NOT NULL,
                       hit INT DEFAULT 0,
                       type VARCHAR(255) default 'COMMUNITY'
);

create table Comment (
                         comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         user_id VARCHAR(255) NOT NULL,
                         board_id BIGINT NOT NULL,
                         content VARCHAR(255) NOT NULL,
                         current_update TIMESTAMP  default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table hot_place (
                           hot_place_id VARCHAR(255) PRIMARY KEY,
                           hot_place_name VARCHAR(255) NOT NULL,
                           x VARCHAR(255) NOT NULL,
                           y VARCHAR(255) NOT NULL,
                           vote BIGINT DEFAULT 0,
                           place_url VARCHAR(255),
                           road_address_name VARCHAR(255),
                           address_name VARCHAR(255)
);

create table hot_place_article (
                                   hot_place_article_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   user_id VARCHAR(255) NOT NULL,
                                   hot_place_id VARCHAR(255) NOT NULL,
                                   current_update TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   hot_place_name VARCHAR(255) NOT NULL,
                                   content VARCHAR(255) NOT NULL,
                                   hit BIGINT DEFAULT 0,
                                   image_url VARCHAR(255)
);

create table hot_place_tag (
                               hot_place_tag_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               hot_place_id VARCHAR(255) NOT NULL,
                               tag_name VARCHAR(255) NOT NULL,
                               count BIGINT DEFAULT 0
);




--
-- Table structure for table `plan_board`
--

DROP TABLE IF EXISTS `plan_board`;
CREATE TABLE `plan_board` (
                              `plan_board_id` int NOT NULL AUTO_INCREMENT,
                              `title` varchar(100) NOT NULL,
                              `user_id` varchar(45) NOT NULL,
                              `start_date` date NOT NULL,
                              `end_date` date NOT NULL,
                              PRIMARY KEY (`plan_board_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


DROP TABLE IF EXISTS `plan`;

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
                        PRIMARY KEY (`plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
