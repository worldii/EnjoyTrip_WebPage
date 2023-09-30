DROP TABLE IF EXISTS comment cascade ;
DROP TABLE IF EXISTS file_info cascade;
DROP TABLE IF EXISTS hot_place_article cascade;
DROP TABLE IF EXISTS plan cascade;
DROP TABLE IF EXISTS plan_board cascade;
DROP TABLE IF EXISTS board cascade;
DROP TABLE IF EXISTS `USER` cascade;

CREATE TABLE user (
                      user_id VARCHAR(255) PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      address VARCHAR(255),
                      authority INT default 1,
                      password VARCHAR(255) NOT NULL,
                      email VARCHAR(255) NOT NULL,
                      salt VARCHAR(255)
);

