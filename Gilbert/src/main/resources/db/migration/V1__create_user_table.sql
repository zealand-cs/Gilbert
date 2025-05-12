CREATE TABLE users
(
    id                   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    display_name         VARCHAR(32)  NOT NULL,
    username             VARCHAR(32)  NOT NULL UNIQUE,
    email                VARCHAR(128) NOT NULL UNIQUE,
    password             VARCHAR(256) NOT NULL,
    profile_picture_id   VARCHAR(64)  NULL,
    terms_agreement_date DATETIME     NULL,
    role                 VARCHAR(32)  NOT NULL DEFAULT 'user',
    created_date         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
)