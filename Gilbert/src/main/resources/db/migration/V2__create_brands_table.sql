CREATE TABLE brands
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(48) NOT NULL,
    FULLTEXT (name)
);