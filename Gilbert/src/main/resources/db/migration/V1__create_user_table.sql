CREATE TABLE users
(
    id          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    display_name        VARCHAR(32)  NOT NULL,
    username        VARCHAR(32)  NOT NULL,
    email       VARCHAR(128) NOT NULL UNIQUE,
    password    VARCHAR(256) NOT NULL,
    terms_agreement_date DATETIME NULL,
    created_date DATETIME NOT NULL,
    role        VARCHAR(32)  NOT NULL DEFAULT 'user'
)