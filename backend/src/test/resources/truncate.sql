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

CREATE TABLE file_info (
    file_info_id BIGINT not null AUTO_INCREMENT PRIMARY KEY,
    file_url VARCHAR(255),
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
